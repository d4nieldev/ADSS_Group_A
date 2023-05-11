package BusinessLayer.Suppliers;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.ReceiptItem;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.ReceiptItemDAO;
import DataAccessLayer.DTOs.ReceiptItemDTO;

public class ReservationController {
    private static ReservationController instance = null;
    // maps between the main reservation and the sub-reservations it was splited
    // into.
    private Map<Integer, List<Reservation>> idToSupplierReservations;
    // maps between the supplier id and the reservations that were made to that
    // supplier.
    private Map<Integer, List<Reservation>> supplierIdToReservations;
    // list of reservations with 'Ready' status.
    private List<Integer> readyReservations;
    // the next id for a reservation in the system.
    private int lastId;
    private ProductController pc = ProductController.getInstance();
    private SupplierController sc = SupplierController.getInstance();

    private ReservationController() {
        idToSupplierReservations = new HashMap<>();
        supplierIdToReservations = new HashMap<>();
        readyReservations = new ArrayList<>();
        lastId = 0;
    }

    public static ReservationController getInstance() {
        if (instance == null)
            instance = new ReservationController();
        return instance;
    }

    public void makeManualReservation(Map<Integer, Map<Integer, Integer>> supplierToproductToAmount,
            Integer destinationBranch) throws SuppliersException, SQLException {
        int reservationId = getNextIdAndIncrement();
        List<Reservation> finalOrder = new ArrayList<>();

        for (int supplierId : supplierToproductToAmount.keySet()) {
            Map<Integer, Integer> productToAmount = supplierToproductToAmount.get(supplierId);
            Contact contact = sc.getRandomContactOf(supplierId);
            Reservation r = new Reservation(reservationId, supplierId, new ArrayList<>(), contact, destinationBranch);

            for (int productId : productToAmount.keySet()) {
                int amount = productToAmount.get(productId);
                ProductAgreement agreement = pc.getAgreement(productId, supplierId);
                if (agreement.getStockAmount() < amount)
                    throw new SuppliersException(
                            "Supplier " + supplierId + " provides only " + agreement.getStockAmount()
                                    + " units of product " + productId + " but requested " + amount + ".");

                double pricePerUnitBeforeDiscount = agreement.getBasePrice();
                double pricePerUnitAfterDiscount = agreement.getPrice(amount);
                ReceiptItemDTO receiptItemDTO = new ReceiptItemDTO(reservationId, productId, amount,
                        pricePerUnitBeforeDiscount,
                        pricePerUnitAfterDiscount);
                ReceiptItemDAO.getInstance().insert(receiptItemDTO);
                ReceiptItem item = new ReceiptItem(receiptItemDTO);
                r.addReceiptItem(item);
            }

            // calculate final discount
            List<ReceiptItem> items = r.getReceipt();
            sc.calculateSupplierDiscount(supplierId, items);
            finalOrder.add(r);
        }

        // if everything went well, add the reservation to the system
        addPartialReservations(finalOrder);

    }

    private void addPartialReservations(List<Reservation> reservations) {
        for (Reservation r : reservations)
            addPartialReservation(r);
    }

    private void addPartialReservation(Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservation.getId(), k -> new ArrayList<>()).add(reservation);
        supplierIdToReservations.computeIfAbsent(reservation.getSupplierId(), k -> new ArrayList<>()).add(reservation);
    }

    public void makeDeficiencyReservation(Map<Integer, Integer> productToAmount, Integer destinationBranch)
            throws SuppliersException {
        int reservationId = getNextIdAndIncrement();
        Map<Integer, Reservation> supToReservation = maxReservationPerSupplier(productToAmount, destinationBranch,
                reservationId);

        List<Reservation> finalOrder = new ArrayList<>();
        while (productToAmount.size() > 0 && supToReservation.size() > 0) {
            // find and choose the best reservation
            Reservation r = getMostAttractiveReservationAndRemove(supToReservation);
            finalOrder.add(r);

            subtractReservationFromOrder(productToAmount, r);

            for (Reservation other : supToReservation.values())
                other.floorReservation(productToAmount);
        }

        if (productToAmount.size() > 0)
            throw new SuppliersException("The reservation could not be made due to lack of stock.");

        // if everything went well, add the reservation to the system
        addPartialReservations(finalOrder);
    }

    private void subtractReservationFromOrder(Map<Integer, Integer> productToAmount, Reservation r) {
        for (ReceiptItem item : r.getReceipt()) {
            int productId = item.getProduct().getId();
            int amountOrdered = item.getAmount();
            int prevAmount = productToAmount.get(productId);
            int leftAmount = prevAmount - amountOrdered;
            if (leftAmount == 0)
                productToAmount.remove(productId);
            else
                productToAmount.put(productId, leftAmount);
        }
    }

    private Map<Integer, Reservation> maxReservationPerSupplier(Map<Integer, Integer> productToAmount,
            Integer destinationBranch, int reservationId) throws SuppliersException {
        Map<Integer, Reservation> supplierToReservation = new HashMap<>();

        for (int productId : productToAmount.keySet()) {
            int amount = productToAmount.get(productId);
            Collection<ProductAgreement> agreements = pc.getProductAgreementsOfProduct(productId);

            for (ProductAgreement agreement : agreements) {
                int supplierId = agreement.getSupplierId();
                // for each supplier, create a new reservation
                Reservation r = supplierToReservation.get(supplierId);
                if (r == null) {
                    Contact contact = sc.getRandomContactOf(supplierId);
                    r = new Reservation(reservationId, supplierId, new ArrayList<>(), contact, destinationBranch);
                    supplierToReservation.put(supplierId, r);
                }

                // for each product and supplier, find the maximum amount that can be purchased
                int maxAmount = Math.min(amount, agreement.getStockAmount());
                double pricePerUnitBeforeDiscount = agreement.getBasePrice();
                double pricePerUnitAfterDiscount = agreement.getPrice(maxAmount);
                ReceiptItemDTO receiptItemDTO = new ReceiptItemDTO(reservationId, productId, maxAmount,
                        pricePerUnitBeforeDiscount, pricePerUnitAfterDiscount);
                r.addReceiptItem(new ReceiptItem(receiptItemDTO));
            }
        }

        // update final discount of suppliers
        for (int supplierId : supplierToReservation.keySet()) {
            List<ReceiptItem> items = supplierToReservation.get(supplierId).getReceipt();
            sc.calculateSupplierDiscount(supplierId, items);
        }

        return supplierToReservation;
    }

    private Reservation getMostAttractiveReservationAndRemove(Map<Integer, Reservation> supToReservation)
            throws SuppliersException {
        // min time
        // min num of suppliers
        // min total price

        // compare first by mimimal time, then by minimum num of reservations, then by
        // minimum price
        Comparator<Integer> supComp = (sup1, sup2) -> {
            Supplier s1 = SupplierController.getInstance().getSupplierById(sup1);
            Supplier s2 = SupplierController.getInstance().getSupplierById(sup2);
            // s1 before s2 <=> s1 supplies before sw <=> s1.time - s2.time < 0
            int output = (int) ChronoUnit.DAYS.between(s1.getClosestDeliveryDate(), s2.getClosestDeliveryDate());

            Reservation r1 = supToReservation.get(sup1);
            Reservation r2 = supToReservation.get(sup2);

            if (output == 0) {

                int amount1 = r1.getTotalAmount();
                int amount2 = r2.getTotalAmount();
                // s1 before s2 <=> s1 offers more that s2 <=> s2.amount - s1.amount < 0
                output = amount2 - amount1;
            }

            if (output == 0) {
                // s1 before s2 <=> s1 is cheaper than s2 <=> s1.price - s2.price < 0
                double diff = r1.getPriceAfterDiscount() - r2.getPriceAfterDiscount();
                output = diff > 0 ? 1 : (diff < 0 ? -1 : 0);
            }

            return output;
        };

        int supplierId = supToReservation.keySet().stream().max(supComp).get();
        return supToReservation.remove(supplierId);
    }

    private int getNextIdAndIncrement() {
        return lastId++;
    }

    public void cancelReservation(int reservationId) throws SuppliersException {
        for (Reservation r : idToSupplierReservations.get(reservationId))
            r.cancel();
    }

    public void makeReservationReady(int reservationId) throws SuppliersException {
        for (Reservation r : idToSupplierReservations.get(reservationId)) {
            r.makeReady();
            readyReservations.add(r.getSupplierId());
        }
    }

    public void closeReservation(int reservationId) throws SuppliersException {
        for (Reservation r : idToSupplierReservations.get(reservationId))
            r.close();
    }

    public List<Reservation> getReservationReceipt(int reservationId) throws SuppliersException {
        if (!idToSupplierReservations.containsKey(reservationId))
            throw new SuppliersException("No reservation with id " + reservationId + " found.");

        return idToSupplierReservations.get(reservationId);
    }

    public List<Reservation> getSupplierReservations(int supplierId) {
        if (!supplierIdToReservations.containsKey(supplierId))
            return new ArrayList<>();
        return supplierIdToReservations.get(supplierId);
    }

    public Map<Integer, List<Integer>> getReadySupplierToBranches() {
        Map<Integer, List<Integer>> output = new HashMap<>();
        for (Integer reservationId : readyReservations) {
            List<Reservation> subReservations = idToSupplierReservations.get(reservationId);
            for (Reservation r : subReservations)
                output.computeIfAbsent(reservationId, k -> new ArrayList<>()).add(r.getDestination());
        }
        return output;
    }

    public void clearData() {
        idToSupplierReservations.clear();
        supplierIdToReservations.clear();
        readyReservations.clear();
        lastId = 0;
    }
}
