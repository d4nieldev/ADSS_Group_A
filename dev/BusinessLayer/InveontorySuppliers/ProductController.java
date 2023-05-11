package BusinessLayer.InveontorySuppliers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import BusinessLayer.Suppliers.ProductAgreement;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.ProductAgreementDAO;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;
import DataAccessLayer.DTOs.ProductDTO;

public class ProductController {
    private static ProductController instance = null;
    private ProductAgreementDAO productAgreementDAO;
    private Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;
    private Map<Integer, List<ProductAgreement>> supplierIdToProductAgreements;
    private Map<Integer, Product> products;

    private ProductController() {
        productIdToSupplierProducts = new HashMap<>();
        supplierIdToProductAgreements = new HashMap<>();
        products = new HashMap<>();
        productAgreementDAO = ProductAgreementDAO.getInstance();
    }

    public static ProductController getInstance() {
        if (instance == null)
            instance = new ProductController();
        return instance;
    }

    public ProductAgreement getAgreement(int productId, int supplierId) throws SQLException {
        if (productIdToSupplierProducts.containsKey(productId)) {
            if (productIdToSupplierProducts.get(productId).containsKey(supplierId))
                return productIdToSupplierProducts.get(productId).get(supplierId);
        }
        ProductAgreementDTO dto = ProductAgreementDAO.getInstance().getAgreementByProductAndSupplier(productId,
                supplierId);
        ProductAgreement agreement = new ProductAgreement(dto);
        addProductAgreement(supplierId, productId, agreement);
        return agreement;
    }

    public Collection<ProductAgreement> getProductAgreementsOfProduct(int productId)
            throws SuppliersException, SQLException {
        Collection<ProductAgreementDTO> dtos = ProductAgreementDAO.getInstance().getAgreementsOfProduct(productId);
        List<ProductAgreement> output = new ArrayList<>();
        for (ProductAgreementDTO dto : dtos) {
            ProductAgreement agreement = getAgreement(productId, dto.getSupplierId());
            output.add(agreement);
        }
        return output;
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

    public List<ProductAgreement> getProductAgreementsOfSupplier(int supplierId)
            throws SuppliersException, SQLException {
        Collection<ProductAgreementDTO> dtos = ProductAgreementDAO.getInstance().getAgreementsOfSupplier(supplierId);
        List<ProductAgreement> output = new ArrayList<>();
        for (ProductAgreementDTO dto : dtos) {
            ProductAgreement agreement = getAgreement(dto.getProductDTO().getId(), supplierId);
            output.add(agreement);
        }
        return output;
    }

    public void updateProductAgreement(int supplierId, int productShopId, int stockAmount, double basePrice,
            TreeMap<Integer, Discount> amountToDiscount) throws SQLException {
        ProductAgreement oldAgreement = productIdToSupplierProducts.get(productShopId).get(supplierId);
        TreeMap<Integer, DiscountDTO> amountToDiscountDTO = new TreeMap<>();
        for (Integer amount : amountToDiscount.keySet())
            amountToDiscountDTO.put(amount, amountToDiscount.get(amount).getDto());
        ProductAgreementDTO dto = new ProductAgreementDTO(supplierId, oldAgreement.getDto().getProductDTO(),
                stockAmount, basePrice, oldAgreement.getProductSupplierId(), amountToDiscountDTO);
        productAgreementDAO.update(dto);
        ProductAgreement newAgreement = new ProductAgreement(dto);
        addProductAgreement(supplierId, productShopId, newAgreement);
    }

    /**
     * Removes all suppliers products agreements from the system.
     * 
     * @param supplierId the supplier id
     */
    // TODO: delete this
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

    public void addProduct(Product product) throws SuppliersException, SQLException {
        if (products.containsKey(product.getId()))
            throw new SuppliersException("A product with id " + product.getId() + " already exists");
        ProductDTO dto = product.getDTO();
        ProductsDAO.getInstance().insert(dto);
        products.put(product.getId(), product);
    }

    public Product getProductById(int productId) throws SQLException {
        if (products.containsKey(productId))
            return products.get(productId);
        else {
            // load from tables and save
            ProductDTO productDTO = ProductsDAO.getInstance().getById(productId);
            Product product = new Product(productDTO);
            products.put(productId, product);
            return product;
        }
    }

    public void clearData() {
        productIdToSupplierProducts.clear();
        supplierIdToProductAgreements.clear();
        products.clear();
    }

    public boolean exist(int id) throws SQLException {
        return getProductById(id) != null;
    }
}
