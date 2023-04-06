package BusinessLayer.Suppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import BusinessLayer.Suppliers.exceptions.SuppliersException;

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

    /**
     * Calculates the cheapest division between suppliers if possible and saves in
     * the system
     * 
     * @param productToAmount the reservation in product id (in store) to amount
     *                        format
     * @throws SuppliersException if the reservation could not be complete for lack
     *                            of supply
     */
    public void makeReservation(Map<Integer, Integer> productToAmount) throws SuppliersException {
        Map<Integer, List<ReceiptItem>> supplierToProducts = splitReservation(productToAmount);

        // calculate final discounts and create partial reservations
        int reservationId = getNextIdAndIncrement();
        for (Integer supplierId : supplierToProducts.keySet()) {
            List<ReceiptItem> items = supplierToProducts.get(supplierId);
            SupplierController.getInstance().calculateSupplierDiscount(supplierId, items);
            Contact contact = SupplierController.getInstance().getRandomContactOf(supplierId);
            Reservation reservation = new Reservation(reservationId, supplierId, items, contact);
            addPartialReservation(reservation);
        }
    }

    /**
     * Returns the cheapest division between suppliers if possible
     * 
     * @param productToAmount the reservation in product id (in store) to amount
     *                        format
     * @throws SuppliersException if the reservation could not be complete for lack
     *                            of supply
     */
    private Map<Integer, List<ReceiptItem>> splitReservation(Map<Integer, Integer> productToAmount)
            throws SuppliersException {
        Map<Integer, List<ReceiptItem>> supplierToProducts = new HashMap<Integer, List<ReceiptItem>>();

        for (Integer productId : productToAmount.keySet()) {
            int amount = productToAmount.get(productId);

            // find the most attractive suppliers for this product
            Map<Integer, ReceiptItem> supplierToReceiptItem = splitProduct(productId, amount);

            // add the receipt items to the list of receipt items for each supplier
            for (Integer supplierId : supplierToReceiptItem.keySet()) {
                ReceiptItem item = supplierToReceiptItem.get(supplierId);
                supplierToProducts.computeIfAbsent(supplierId, k -> new ArrayList<ReceiptItem>()).add(item);
            }
        }

        return supplierToProducts;
    }

    /**
     * Returns the cheapest division between suppliers if possible
     * 
     * @param productId the product to split
     * @param amount    the amount to be splitted
     * @throws SuppliersException if the reservation could not be complete for lack
     *                            of supply
     */
    private Map<Integer, ReceiptItem> splitProduct(int productId, int amount) throws SuppliersException {
        Collection<ProductAgreement> productAgreements = ProductController.getInstance()
                .getProductAgreementsOfProduct(productId);
        Map<Integer, ReceiptItem> output = new HashMap<>();

        // first check if the product can be ordered from one supplier
        ProductAgreement minAgreement = null;
        for (ProductAgreement agreement : productAgreements)
            if (agreement.getStockAmount() >= amount
                    && (minAgreement == null
                            || agreement.getPrice(amount) < minAgreement.getPrice(amount)))
                minAgreement = agreement;
        if (minAgreement != null) {
            output.put(minAgreement.getSupplierId(), new ReceiptItem(amount, minAgreement));
            return output;
        }

        // can't be ordered from one supplier - split the amount among suppliers
        while (amount > 0) {
            minAgreement = null;
            int maxAmount = -1;
            Collection<ProductAgreement> relevantPAs = productAgreements.stream()
                    .filter(e -> output.get(e.getSupplierId()) == null).collect(Collectors.toList());
            for (ProductAgreement agreement : relevantPAs) {
                maxAmount = Math.min(amount, agreement.getStockAmount());
                if (minAgreement == null
                        || agreement.getPrice(maxAmount) < minAgreement.getPrice(maxAmount))
                    minAgreement = agreement;
            }
            if (minAgreement == null)
                throw new SuppliersException("Cannot order product with id " + productId + " and amount " + amount);
            output.put(minAgreement.getSupplierId(), new ReceiptItem(maxAmount, minAgreement));
            amount -= maxAmount;
        }
        return output;
    }

    private void addPartialReservation(Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservation.getId(), k -> new ArrayList<>()).add(reservation);
        supplierIdToReservations.computeIfAbsent(reservation.getSupplierId(), k -> new ArrayList<>()).add(reservation);
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

    public List<ReceiptItem> getReservationReceipt(int reservationId) throws SuppliersException {
        if (!idToSupplierReservations.containsKey(reservationId))
            throw new SuppliersException("No reservation with id " + reservationId + " found.");

        List<ReceiptItem> output = new ArrayList<>();
        List<Reservation> reservations = idToSupplierReservations.get(reservationId);
        for (Reservation reservation : reservations)
            output.addAll(reservation.getReceipt());
        return output;
    }

    public List<Reservation> getSupplierReservations(int supplierId) {
        if (!supplierIdToReservations.containsKey(supplierId))
            return new ArrayList<>();
        return supplierIdToReservations.get(supplierId);
    }

    public Map<Integer, List<String>> getReadySupplierToAddresses() {
        Map<Integer, List<String>> output = new HashMap<>();
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
