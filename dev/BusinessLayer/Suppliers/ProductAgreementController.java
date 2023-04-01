package BusinessLayer.Suppliers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductAgreementController {
    private static ProductAgreementController instance = null;
    private Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;

    private ProductAgreementController() {
        productIdToSupplierProducts = new HashMap<>();
    }

    public static ProductAgreementController getInstance() {
        if (instance == null)
            instance = new ProductAgreementController();
        return instance;
    }

    public Collection<ProductAgreement> getProductAgreements(int productId) {
        return productIdToSupplierProducts.get(productId).values();
    }

    /**
     * Adds a new product agreement to the map of product agreements.
     * @param supplierId 
     * @param productId
     * @param productAgreement
     */
    public void addProductAgreement(int supplierId, int productId, ProductAgreement productAgreement) {
        productIdToSupplierProducts.computeIfAbsent(productId, k -> new HashMap<>()).put(supplierId, productAgreement);
    }

    /**
     * Removes all suppliers products agreements from the system.
     * @param supplierId the supplier id
     */
    public void deleteAllSupplierAgreements(int supplierId) {
        for (Map.Entry<Integer, Map<Integer, ProductAgreement>> ProdToSupToAgree : productIdToSupplierProducts.entrySet()){
            for (Map.Entry<Integer, ProductAgreement> SupToAgree : ProdToSupToAgree.getValue().entrySet()){
                if(SupToAgree.getKey().equals(supplierId)){
                    ProdToSupToAgree.getValue().remove(supplierId);
                }
            }  
        }
    }
}
