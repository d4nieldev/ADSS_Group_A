package BusinessLayer.Suppliers;

import java.util.Map;

public class ProductAgreementController {
    private static Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;

    //TODO: FINISH PRODUCT AGREEMENT CONTROLLER
    public static ProductAgreementController getInstance() {
        return null;
    }

    public Map<Integer, ProductAgreement> getAllProductAgreements(int productId) {
        return productIdToSupplierProducts.get(productId);
    }

    public void addProductAgreement(int supplierId, int productId, ProductAgreement productAgreement) {
    }
}
