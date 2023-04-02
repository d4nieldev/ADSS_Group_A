package BusinessLayer.Suppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ProductController {
    private static ProductController instance = null;
    private Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;
    private Map<Integer, List<ProductAgreement>> supplierIdToProductAgreements;
    private Map<Integer, Product> products;

    private ProductController() {
        productIdToSupplierProducts = new HashMap<>();
        products = new HashMap<>();
    }

    public static ProductController getInstance() {
        if (instance == null)
            instance = new ProductController();
        return instance;
    }

    public Collection<ProductAgreement> getProductAgreementsOfProduct(int productId) {
        return productIdToSupplierProducts.get(productId).values();
    }

    /**
     * Adds a new product agreement to the map of product agreements.
     * 
     * @param supplierId
     * @param productId
     * @param productAgreement
     */
    public void addProductAgreement(int supplierId, int productId, ProductAgreement productAgreement) {
        productIdToSupplierProducts.computeIfAbsent(productId, k -> new HashMap<>()).put(supplierId, productAgreement);
        supplierIdToProductAgreements.computeIfAbsent(supplierId, k -> new ArrayList<>()).add(productAgreement);
    }

    public List<ProductAgreement> getProductAgreementsOfSupplier(int supplierId) throws SuppliersException {
        if (!supplierIdToProductAgreements.containsKey(supplierId))
            throw new SuppliersException("No supplier with id " + supplierId + " exists");
        return supplierIdToProductAgreements.get(supplierId);
    }

    public void updateProductAgreement(int supplierId, int productShopId, int stockAmount,
            TreeMap<Integer, Double> amountToPrice) {
        ProductAgreement productAgreement = productIdToSupplierProducts.get(productShopId).get(supplierId);
        productAgreement.setStockAmount(stockAmount);
        productAgreement.setAmountToPrice(amountToPrice);
    }

    /**
     * Removes all suppliers products agreements from the system.
     * 
     * @param supplierId the supplier id
     */
    public void deleteAllSupplierAgreements(int supplierId) {
        for (Map.Entry<Integer, Map<Integer, ProductAgreement>> ProdToSupToAgree : productIdToSupplierProducts
                .entrySet()) {
            for (Map.Entry<Integer, ProductAgreement> SupToAgree : ProdToSupToAgree.getValue().entrySet()) {
                if (SupToAgree.getKey().equals(supplierId)) {
                    ProdToSupToAgree.getValue().remove(supplierId);
                }
            }
        }
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public Product getProductById(int productId) {
        return products.get(productId);
    }
}
