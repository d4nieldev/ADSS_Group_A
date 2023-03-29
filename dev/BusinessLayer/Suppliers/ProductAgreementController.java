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

    //TODO: FINISH PRODUCT AGREEMENT CONTROLLER
    public static ProductAgreementController getInstance() {
        if (instance == null)
            instance = new ProductAgreementController();
        return instance;
    }

    public Collection<ProductAgreement> getProductAgreements(int productId) {
        return productIdToSupplierProducts.get(productId).values();
    }

    public void addProductAgreement(int supplierId, int productId, ProductAgreement productAgreement) {
        productIdToSupplierProducts.computeIfAbsent(productId, k -> new HashMap<>()).put(supplierId, productAgreement);
    }
}
