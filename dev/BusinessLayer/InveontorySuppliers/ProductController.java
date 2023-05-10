package BusinessLayer.InveontorySuppliers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.Suppliers.ProductAgreement;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ProductController {
    private static ProductController instance = null;
    private Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;
    private Map<Integer, List<ProductAgreement>> supplierIdToProductAgreements;
    private Map<Integer, Product> products;

    private ProductController() {
        productIdToSupplierProducts = new HashMap<>();
        supplierIdToProductAgreements = new HashMap<>();
        products = new HashMap<>();
    }

    public static ProductController getInstance() {
        if (instance == null)
            instance = new ProductController();
        return instance;
    }

    public Collection<ProductAgreement> getProductAgreementsOfProduct(int productId) throws SuppliersException {
        if (!productIdToSupplierProducts.containsKey(productId))
            throw new SuppliersException("Product does not exist");
        return productIdToSupplierProducts.get(productId).values();
    }

    public ProductAgreement getAgreement(int productId, int supplierId) throws SuppliersException {
        if (!productIdToSupplierProducts.containsKey(productId))
            throw new SuppliersException("No supplier with id " + supplierId + " exists");
        if (!productIdToSupplierProducts.get(productId).containsKey(supplierId))
            throw new SuppliersException(
                    "Supplier with id " + supplierId + " does not supply product with id " + productId);
        return productIdToSupplierProducts.get(productId).get(supplierId);
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
            TreeMap<Integer, Double> amountToDiscount) {
        ProductAgreement productAgreement = productIdToSupplierProducts.get(productShopId).get(supplierId);
        productAgreement.setStockAmount(stockAmount);
        productAgreement.setAmountToDiscount(amountToDiscount);
    }

    /**
     * Removes all suppliers products agreements from the system.
     * 
     * @param supplierId the supplier id
     */
    public void deleteAllSupplierAgreements(int supplierId) {
        for (Integer productId : productIdToSupplierProducts.keySet()) {
            for (Integer supId : productIdToSupplierProducts.get(productId).keySet()) {
                if (supId == supplierId) {
                    productIdToSupplierProducts.get(productId).remove(supId);
                }
            }
        }
        if (supplierIdToProductAgreements.containsKey(supplierId)) {
            supplierIdToProductAgreements.get(supplierId).clear();
        }
    }

    public void addProduct(Product product) throws SuppliersException {
        if (products.containsKey(product.getId()))
            throw new SuppliersException("A product with id " + product.getId() + " already exists");

        products.put(product.getId(), product);
    }

    public Product getProductById(int productId) {
        return products.get(productId);
    }

    public void clearData() {
        productIdToSupplierProducts.clear();
        supplierIdToProductAgreements.clear();
        products.clear();
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public boolean exist(int id) {
        return products.containsKey(id);
    }
}
