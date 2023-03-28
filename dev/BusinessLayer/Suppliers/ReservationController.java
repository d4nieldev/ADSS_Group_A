package BusinessLayer.Suppliers;

import java.util.ArrayList;
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

    public String makeReservation(Map<Integer, Integer> productToAmount) {
        try {
            Map<Integer, List<ReceiptItem>> supplierToProducts = new HashMap<Integer, List<ReceiptItem>>();

            for (Map.Entry<Integer, Integer> entry : productToAmount.entrySet()) {
                int productId = entry.getKey();
                int amount = entry.getValue();

                // get the list of agreements from different suppliers for this product
                Map<Integer, ProductAgreement> productAgreements = ProductAgreementController.getInstance()
                        .getAllProductAgreements(productId);

                // find the most attractive agreement for this product
                ProductAgreement minProductAgreement = null;
                double minPrice = Double.MAX_VALUE;
                for (Map.Entry<Integer, ProductAgreement> productAgreementEntry : productAgreements.entrySet()) {
                    ProductAgreement agreement = productAgreementEntry.getValue();
                    if (agreement.getStockAmount() >= amount && agreement.getPrice(amount) < minPrice) {
                        minPrice = agreement.getPrice(amount);
                        minProductAgreement = agreement;
                    }
                }

                // if no agreement was found, throw an exception
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

            return "success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    private void addPartialReservation(int reservationId, Reservation reservation) {
        idToSupplierReservations.computeIfAbsent(reservationId, k -> new ArrayList<Reservation>()).add(reservation);
    }

    public int getNextIdAndIncrement() {
        return lastId++;
    }
}
