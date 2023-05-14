package BusinessLayer.Suppliers;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.InveontorySuppliers.PeriodicReservation;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.ReceiptItem;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.enums.Status;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.PeriodicReservationDAO;
import DataAccessLayer.DAOs.ReceiptItemDAO;
import DataAccessLayer.DAOs.ReservationDAO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;
import DataAccessLayer.DTOs.ReceiptItemDTO;
import DataAccessLayer.DTOs.ReservationDTO;

public class ReservationController {
    private static ReservationController instance = null;
    private boolean initialized;
    // maps between the main reservation and the sub-reservations it was splited
    // into.
    private Map<Integer, List<Reservation>> idToSupplierReservations;
    // maps between the supplier id and the reservations that were made to that
    // supplier.
    private Map<Integer, List<Reservation>> supplierIdToReservations;
    // list of reservations with 'Ready' status.
    private List<Integer> readyReservations;
    // the next id for a reservation in the system.
    private Map<Integer, Map<Integer, PeriodicReservation>> supplierToBranchToPeriodicReservations;
    private int lastId;
    private ProductController pc = ProductController.getInstance();
    private SupplierController sc = SupplierController.getInstance();

    private ReceiptItemDAO receiptItemDAO;
    private ReservationDAO reservationDAO;
    private PeriodicReservationDAO periodicReservationDAO;

    private Thread periodicReservationsCareTaker;

    private ReservationController() {
        idToSupplierReservations = new HashMap<>();
        supplierIdToReservations = new HashMap<>();
        readyReservations = new ArrayList<>();
        supplierToBranchToPeriodicReservations = new HashMap<>();

        periodicReservationsCareTaker = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    orderPeriodicalReservations(LocalDate.now().getDayOfWeek());
                    Thread.sleep(86400000); // sleep for a day
                }
            } catch (InterruptedException ignored) {
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        receiptItemDAO = ReceiptItemDAO.getInstance();
        reservationDAO = ReservationDAO.getInstance();
        periodicReservationDAO = PeriodicReservationDAO.getInstance();

        this.initialized = false;
    }

    public void init() throws SQLException {
        if (!initialized) {
            loadReservationLastId();
            loadPeriodicReservations();
            periodicReservationsCareTaker.start();
            initialized = true;
        }
    }

    private void loadPeriodicReservations() throws SQLException {
        List<PeriodicReservationDTO> dtos = periodicReservationDAO.selectAll();
        for (PeriodicReservationDTO dto : dtos) {
            PeriodicReservation pr = new PeriodicReservation(dto);
            int supplierId = dto.getSupplierId();
            int branchId = dto.getBranchId();
            supplierToBranchToPeriodicReservations.computeIfAbsent(supplierId, k -> new HashMap<>()).put(branchId, pr);
        }
    }

    private void orderPeriodicalReservations(DayOfWeek day) throws SQLException {
        for (Map<Integer, PeriodicReservation> supplierReservations : supplierToBranchToPeriodicReservations.values()) {
            for (PeriodicReservation pr : supplierReservations.values()) {
                if (day == pr.getDay()) {
                    Map<Integer, Map<Integer, Integer>> supplierToProductToAmount = new HashMap<>();
                    int supplierId = pr.getSupplierId();
                    Map<Integer, Integer> productToAmount = pr.getProductsToAmounts();
                    supplierToProductToAmount.put(supplierId, productToAmount);
                    makeManualReservation(supplierToProductToAmount, pr.getBranchId());
                }
            }
        }
    }

    private boolean periodicReservationExists(int supplierId, int branchId) {
        return supplierToBranchToPeriodicReservations.containsKey(supplierId)
                && supplierToBranchToPeriodicReservations.get(supplierId).containsKey(branchId);
    }

    public void addPeriodicReservation(int branchId, int supplierId, DayOfWeek day,
            Map<Integer, Integer> productToAmount) throws SQLException {
        List<PeriodicReservationItemDTO> items = new ArrayList<>();
        for (int productId : productToAmount.keySet()) {
            int amount = productToAmount.get(productId);
            PeriodicReservationItemDTO itemDTO = new PeriodicReservationItemDTO(supplierId, branchId, productId,
                    amount);
            items.add(itemDTO);
        }
        PeriodicReservationDTO rDTO = new PeriodicReservationDTO(supplierId, branchId, day, items);
        PeriodicReservation r = new PeriodicReservation(rDTO);
        periodicReservationDAO.insert(r.getDTO());
        supplierToBranchToPeriodicReservations.computeIfAbsent(supplierId, k -> new HashMap<>()).put(branchId, r);
    }

    public void updatePeriodicReservation(int branchId, int supplierId, DayOfWeek day,
            Map<Integer, Integer> productToAmount) throws SQLException {
        if (!periodicReservationExists(supplierId, branchId))
            throw new SuppliersException(
                    "No periodic reservation found for supplier " + supplierId + " and branch " + branchId);

        PeriodicReservation r = supplierToBranchToPeriodicReservations.get(supplierId).get(branchId);
        r.setDay(day);
        r.setProductsToAmounts(productToAmount);
        periodicReservationDAO.update(r.getDTO());
    }

    /**
     * Sets the next id to be the last id used in the database.
     * 
     * @throws SQLException if there is an error in the database.
     */
    private void loadReservationLastId() throws SQLException {
        lastId = reservationDAO.getLastId() + 1;
    }

    public static ReservationController getInstance() {
        if (instance == null)
            instance = new ReservationController();
        return instance;
    }

    public ReservationDTO createReservationDTO(int reservationId, int supplierId, int destinationBranch,
            Contact contact, List<ReceiptItemDTO> receipt) {
        return new ReservationDTO(reservationId, supplierId, LocalDate.now(), Status.NOTREADY, destinationBranch,
                contact.getContactDTO(), receipt);
    }

    public void makeManualReservation(Map<Integer, Map<Integer, Integer>> supplierToproductToAmount,
            Integer destinationBranch) throws SuppliersException, SQLException {
        int reservationId = getNextIdAndIncrement();
        List<Reservation> finalOrder = new ArrayList<>();

        for (int supplierId : supplierToproductToAmount.keySet()) {
            Map<Integer, Integer> productToAmount = supplierToproductToAmount.get(supplierId);
            Contact contact = sc.getRandomContactOf(supplierId);
            ReservationDTO rDTO = createReservationDTO(reservationId, supplierId, destinationBranch, contact,
                    new ArrayList<>());
            Reservation r = new Reservation(rDTO, contact, new ArrayList<>());

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
                        pricePerUnitBeforeDiscount, pricePerUnitAfterDiscount);
                Product product = pc.getProductById(productId);
                ReceiptItem item = new ReceiptItem(receiptItemDTO, product);
                r.addReceiptItem(item);
            }
            // calculate final discount
            List<ReceiptItem> items = r.getReceipt();
            sc.calculateSupplierDiscount(supplierId, items);
            finalOrder.add(r);
        }

        // if everything went well, add the reservation to the system
        orderPartialReservations(finalOrder);
    }

    private void orderPartialReservations(List<Reservation> reservations) throws SQLException {
        for (Reservation r : reservations)
            orderPartialReservation(r);
    }

    private void orderPartialReservation(Reservation reservation) throws SQLException {
        BranchController.getInstance().receiveReservation(reservation);
        reservationDAO.insert(reservation.getDto());
        for (ReceiptItemDTO dto : reservation.getDto().getReceipt())
            receiptItemDAO.insert(dto);
        savePartialReservation(reservation);
    }

    private void savePartialReservation(Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservation.getId(), k -> new ArrayList<>()).add(reservation);
        supplierIdToReservations.computeIfAbsent(reservation.getSupplierId(), k -> new ArrayList<>()).add(reservation);
    }

    public void makeDeficiencyReservation(Map<Integer, Integer> productToAmount, Integer destinationBranch)
            throws SuppliersException, SQLException {
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
        orderPartialReservations(finalOrder);
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
            Integer destinationBranch, int reservationId) throws SuppliersException, SQLException {
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
                    ReservationDTO rDTO = createReservationDTO(reservationId, supplierId, destinationBranch, contact,
                            new ArrayList<>());
                    r = new Reservation(rDTO, contact, new ArrayList<>());
                    supplierToReservation.put(supplierId, r);
                }

                // for each product and supplier, find the maximum amount that can be purchased
                int maxAmount = Math.min(amount, agreement.getStockAmount());
                double pricePerUnitBeforeDiscount = agreement.getBasePrice();
                double pricePerUnitAfterDiscount = agreement.getPrice(maxAmount);
                ReceiptItemDTO receiptItemDTO = new ReceiptItemDTO(reservationId, productId, maxAmount,
                        pricePerUnitBeforeDiscount, pricePerUnitAfterDiscount);
                Product product = ProductController.getInstance().getProductById(productId);
                r.addReceiptItem(new ReceiptItem(receiptItemDTO, product));
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
            throws SuppliersException, SQLException {
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

    public void cancelReservation(int reservationId) throws SuppliersException, SQLException {
        for (Reservation r : idToSupplierReservations.get(reservationId)) {
            r.getDto().setStatus(Status.ABORTED);
            reservationDAO.update(r.getDto());
            r.cancel();
        }
    }

    public void makeReservationReady(int reservationId) throws SuppliersException, SQLException {
        for (Reservation r : idToSupplierReservations.get(reservationId)) {
            r.getDto().setStatus(Status.READY);
            reservationDAO.update(r.getDto());
            r.makeReady();
            readyReservations.add(r.getSupplierId());
        }
    }

    public void closeReservation(int reservationId) throws SuppliersException, SQLException {
        for (Reservation r : idToSupplierReservations.get(reservationId)) {
            r.getDto().setStatus(Status.CLOSED);
            reservationDAO.update(r.getDto());
            r.close();
        }
    }

    private Reservation createReservationFromDTO(ReservationDTO rDTO) throws SQLException {
        List<ReceiptItem> receipt = new ArrayList<>();
        for (ReceiptItemDTO itemDTO : rDTO.getReceipt()) {
            Product product = ProductController.getInstance().getProductById(itemDTO.getProductId());
            receipt.add(new ReceiptItem(itemDTO, product));
        }
        int supplierId = rDTO.getSupplierId();
        String contactPhone = rDTO.getContact().getPhone();
        Contact contact = SupplierController.getInstance().getContactOfSupplier(supplierId, contactPhone);
        return new Reservation(rDTO, contact, receipt);
    }

    public List<Reservation> getReservationReceipt(int reservationId) throws SuppliersException, SQLException {
        // load all data
        List<ReservationDTO> fullreservationDTOs = reservationDAO.getFullReservation(reservationId);
        for (ReservationDTO rDTO : fullreservationDTOs) {
            Reservation r = createReservationFromDTO(rDTO);
            savePartialReservation(r);
        }

        return idToSupplierReservations.get(reservationId);
    }

    public List<Reservation> getSupplierReservations(int supplierId) throws SQLException {
        List<ReservationDTO> reservations = reservationDAO.getSupplierReservations(supplierId);
        for (ReservationDTO rDTO : reservations) {
            Reservation r = createReservationFromDTO(rDTO);
            savePartialReservation(r);
        }
        return supplierIdToReservations.get(supplierId);
    }

    // TODO: delete this?
    // public Map<Integer, List<Integer>> getReadySupplierToBranches() {
    // Map<Integer, List<Integer>> output = new HashMap<>();
    // for (Integer reservationId : readyReservations) {
    // List<Reservation> subReservations =
    // idToSupplierReservations.get(reservationId);
    // for (Reservation r : subReservations)
    // output.computeIfAbsent(reservationId, k -> new
    // ArrayList<>()).add(r.getDestination());
    // }
    // return output;
    // }

    public void clearData() {
        idToSupplierReservations.clear();
        supplierIdToReservations.clear();
        readyReservations.clear();
        lastId = 0;
    }
}
