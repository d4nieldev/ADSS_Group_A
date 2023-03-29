package BusinessLayer.Suppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.Suppliers.enums.Status;
import BusinessLayer.Suppliers.exceptions.NoMatchingSupplierException;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationController {
    private Map<Integer, List<Reservation>> idToSupplierReservations;
    private int lastId;

    public void splitReservation(Map<Integer, Integer> productToAmount) throws SuppliersException {
        Map<Integer, List<ReceiptItem>> supplierToProducts = new HashMap<Integer, List<ReceiptItem>>();

        for (Map.Entry<Integer, Integer> entry : productToAmount.entrySet()) {
            int productId = entry.getKey();
            int amount = entry.getValue();

            // find the most attractive agreement for this product
            ProductAgreement minProductAgreement = findBestProductAgreement(productId, amount);
            if (minProductAgreement == null)
                throw new NoMatchingSupplierException(
                        "No matching supplier found for product " + productId + " and amount " + amount);

            // create a receipt item for this product
            ReceiptItem item = new ReceiptItem(productId, minProductAgreement.getPrice(amount),
                    minProductAgreement.getPrice(0));

            // add the receipt item to the list of receipt items for this supplier
            supplierToProducts
                    .computeIfAbsent(minProductAgreement.getSupplierId(), k -> new ArrayList<ReceiptItem>())
                    .add(item);
        }

        // reservation is possible - create partial reservations
        int reservationId = getNextIdAndIncrement();
        for (Map.Entry<Integer, List<ReceiptItem>> entry : supplierToProducts.entrySet()) {
            int supplierId = entry.getKey();
            List<ReceiptItem> receipt = entry.getValue();
            Reservation reservation = new Reservation(reservationId, supplierId, new Date(),
                    Status.NOTREADY, receipt);
            addPartialReservation(reservationId, reservation);
        }
    }

    private ProductAgreement findBestProductAgreement(int productId, int amount) {
        Collection<ProductAgreement> productAgreements = ProductAgreementController.getInstance()
                .getProductAgreements(productId);

        ProductAgreement minProductAgreement = null;
        for (ProductAgreement agreement : productAgreements)
            if (agreement.getStockAmount() >= amount
                    && agreement.getPrice(amount) < minProductAgreement.getPrice(amount))
                minProductAgreement = agreement;

        return minProductAgreement;
    }

    private void addPartialReservation(int reservationId, Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservationId, k -> new ArrayList<Reservation>()).add(reservation);
    }

    private int getNextIdAndIncrement() {
        return lastId++;
    }
}
