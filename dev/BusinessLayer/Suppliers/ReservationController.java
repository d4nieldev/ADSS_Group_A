package BusinessLayer.Suppliers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationController {
    private Map<Integer, List<Reservation>> idToSupplierReservations;

    public String makeReservation(Map<Integer, Integer> productToAmount) {
        try {
            Map<Integer, List<Integer>> supplierToProducts = new HashMap<Integer, List<Integer>>();

            for (Map.Entry<Integer, Integer> entry : productToAmount.entrySet()) {
                int productId = entry.getKey();
                int amount = entry.getValue();

                Map<Integer, ProductAgreement> productAgreements = ProductAgreementController.getInstance()
                        .getAllProductAgreements(productId);

                Integer curSupplierId = null;
                double minPrice = Double.MAX_VALUE;
                for (Map.Entry<Integer, ProductAgreement> productAgreementEntry : productAgreements.entrySet()) {
                    if (productAgreementEntry.getValue().getPrice(amount) < minPrice) {
                        minPrice = productAgreementEntry.getValue().getPrice(amount);
                        curSupplierId = productAgreementEntry.getKey();
                    }
                }
                if (curSupplierId == null) {
                    // TODO: throw exception
                    return "Product not found";
                }

                supplierToProducts.computeIfAbsent(curSupplierId, k -> new ArrayList<Integer>()).add(productId);
            }

            // TODO create reservations per supplier
            // TODO return something meaningful
            return "WTF?????????????";
        } catch (Exception e) { // MY EXCEPTION
            return e.getMessage();
        }
    }
}
