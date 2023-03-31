package BusinessLayer.Suppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.Suppliers.enums.Status;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationController {
    private Map<Integer, List<Reservation>> idToSupplierReservations;
    private List<Integer> readyReservations;
    private int lastId;

    public void makeReservation(Map<Integer, Integer> productToAmount) throws SuppliersException {
        Map<Integer, List<ReceiptItem>> supplierToProducts = splitReservation(productToAmount);

        // reservation is possible - create partial reservations
        int reservationId = getNextIdAndIncrement();
        for (Map.Entry<Integer, List<ReceiptItem>> entry : supplierToProducts.entrySet()) {
            int supplierId = entry.getKey();
            List<ReceiptItem> receipt = entry.getValue();
            Reservation reservation = new Reservation(reservationId, supplierId, receipt);
            addPartialReservation(reservationId, reservation);
        }
    }

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

    private Map<Integer, ReceiptItem> splitProduct(int productId, int amount) throws SuppliersException {
        Collection<ProductAgreement> productAgreements = ProductAgreementController.getInstance()
                .getProductAgreements(productId);
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
            for (ProductAgreement agreement : productAgreements) {
                maxAmount = Math.min(amount, agreement.getStockAmount());
                if ((minAgreement == null
                        || agreement.getPrice(maxAmount) < minAgreement.getPrice(maxAmount)))
                    minAgreement = agreement;
            }
            if (minAgreement == null)
                throw new SuppliersException("Cannot order product with id " + productId + " and amount " + amount);
            output.put(minAgreement.getSupplierId(), new ReceiptItem(maxAmount, minAgreement));
            amount -= maxAmount;
        }
        return output;
    }

    private void addPartialReservation(int reservationId, Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservationId, k -> new ArrayList<Reservation>()).add(reservation);
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
}
