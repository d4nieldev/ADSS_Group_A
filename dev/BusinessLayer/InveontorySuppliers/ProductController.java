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

    private Map<Integer, Map<Integer, ProductAgreement>> productIdToSupplierProducts;
    private Map<Integer, List<ProductAgreement>> supplierIdToProductAgreements;
    private Map<Integer, Product> products;

    private ProductAgreementDAO productAgreementDAO;
    private ProductsDAO productsDAO;

    private ProductController() {
        productIdToSupplierProducts = new HashMap<>();
        supplierIdToProductAgreements = new HashMap<>();
        products = new HashMap<>();

        productAgreementDAO = ProductAgreementDAO.getInstance();
        productsDAO = ProductsDAO.getInstance();
    }

    public static ProductController getInstance() {
        if (instance == null)
            instance = new ProductController();
        return instance;
    }

    private void loadAgreement(int productId, int supplierId) throws SQLException {
        ProductAgreementDTO dto = productAgreementDAO.getAgreementByProductAndSupplier(productId, supplierId);
        addProductAgreementFromDTO(dto);
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

    private void addProductAgreementFromDTO(ProductAgreementDTO dto) {
        int supplierId = dto.getSupplierId();
        int productId = dto.getProductDTO().getId();

        if (!productIdToSupplierProducts.containsKey(productId)
                || !productIdToSupplierProducts.get(productId).containsKey(supplierId)) {
            ProductAgreement agreement = new ProductAgreement(dto);
            addProductAgreement(supplierId, productId, agreement);
        }
    }

    public ProductAgreement getAgreement(int productId, int supplierId) throws SuppliersException {
        try {
            loadAgreement(productId, supplierId);
        } catch (SQLException e) {
            throw new SuppliersException(e.getMessage());
        }
        return productIdToSupplierProducts.get(productId).get(supplierId);
    }

    public Collection<ProductAgreement> getProductAgreementsOfProduct(int productId)
            throws SuppliersException, SQLException {
        Collection<ProductAgreementDTO> dtos = ProductAgreementDAO.getInstance().getAgreementsOfProduct(productId);
        List<ProductAgreement> output = new ArrayList<>();
        for (ProductAgreementDTO dto : dtos) {
            int supplierId = dto.getSupplierId();
            ProductAgreement agreement = getAgreement(productId, supplierId);
            output.add(agreement);
        }
        return output;
    }

    public List<ProductAgreement> getProductAgreementsOfSupplier(int supplierId)
            throws SuppliersException, SQLException {
        Collection<ProductAgreementDTO> dtos = ProductAgreementDAO.getInstance().getAgreementsOfSupplier(supplierId);
        List<ProductAgreement> output = new ArrayList<>();
        for (ProductAgreementDTO dto : dtos) {
            int productId = dto.getProductDTO().getId();
            ProductAgreement agreement = getAgreement(productId, supplierId);
            output.add(agreement);
        }
        return output;
    }

    public void updateProductAgreement(int supplierId, int productShopId, int stockAmount, double basePrice,
            TreeMap<Integer, Discount> amountToDiscount) throws SQLException {

        ProductAgreement oldAgreement = getAgreement(productShopId, supplierId);
        TreeMap<Integer, DiscountDTO> amountToDiscountDTO = new TreeMap<>();
        for (Integer amount : amountToDiscount.keySet()) {
            DiscountDTO discountDTO = amountToDiscount.get(amount).getDto();
            amountToDiscountDTO.put(amount, discountDTO);
        }

        ProductDTO productDTO = oldAgreement.getDto().getProductDTO();
        int productSupplierId = oldAgreement.getProductSupplierId();
        ProductAgreementDTO newAgreementDTO = new ProductAgreementDTO(supplierId, productDTO, stockAmount, basePrice,
                productSupplierId, amountToDiscountDTO);
        productAgreementDAO.update(newAgreementDTO);
        ProductAgreement newAgreement = new ProductAgreement(newAgreementDTO);

        addProductAgreement(supplierId, productShopId, newAgreement);
    }

    /**
     * Removes all suppliers products agreements from the system RAM.
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

    public void addProduct(ProductDTO productDTO) throws SuppliersException, SQLException {
        if (products.containsKey(productDTO.getId()))
            throw new SuppliersException("A product with id " + productDTO.getId() + " already exists");

        productsDAO.insert(productDTO);
        Product product = new Product(productDTO);
        products.put(productDTO.getId(), product);
    }

    public Product getProductById(int productId) throws SQLException {
        if (products.containsKey(productId))
            return products.get(productId);
        else {
            // load from tables, save and return
            ProductDTO productDTO = productsDAO.getById(productId);
            Product product = new Product(productDTO);
            products.put(productId, product);
            return product;
        }
    }

    /**
     * clears the data from the RAM
     */
    public void clearData() {
        productIdToSupplierProducts.clear();
        supplierIdToProductAgreements.clear();
        products.clear();
    }

    public boolean productExists(int id) throws SQLException {
        return getProductById(id) != null;
    }

    public boolean productAgreementExists(int productId, int supplierId) throws SQLException {
        return getAgreement(productId, supplierId) != null;
    }

}
