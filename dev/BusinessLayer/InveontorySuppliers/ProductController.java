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
import DataAccessLayer.DAOs.CategoryDAO;
import DataAccessLayer.DAOs.ProductAgreementDAO;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.CategoryDTO;
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

    private void addProductAgreementFromDTO(ProductAgreementDTO dto) throws SuppliersException {
        if (dto == null) {
            throw new SuppliersException("ProductAgreementDTO in null and cannot add product agreement from DTO");
        }
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
        // check if supplier exists
        SupplierController.getInstance().getSupplierById(supplierId);

        // get his agreement
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
    public void deleteAllSupplierAgreements(int supplierId) throws SQLException {
        for (Integer productId : productIdToSupplierProducts.keySet()) {
            for (Integer supId : productIdToSupplierProducts.get(productId).keySet()) {
                if (supId == supplierId) {
                    ProductAgreementDTO dto = productIdToSupplierProducts.get(productId).get(supId).getDto();
                    productAgreementDAO.delete(dto);
                    productIdToSupplierProducts.get(productId).remove(supId);
                }
            }
        }
        if (supplierIdToProductAgreements.containsKey(supplierId)) {
            supplierIdToProductAgreements.get(supplierId).clear();
        }

    }

    public void addProduct(int id, String name, String manufacturer, int categoryId)
            throws SuppliersException, SQLException {
        if (products.containsKey(id))
            throw new SuppliersException("A product with id " + id + " already exists");

        CategoryDTO categoryDTO = CategoryDAO.getInstance().getById(categoryId);
        ProductDTO productDTO = new ProductDTO(id, name, manufacturer, categoryDTO);
        productsDAO.insert(productDTO);
        Product product = new Product(productDTO);
        products.put(productDTO.getId(), product);
    }

    public void addProduct(ProductDTO productDTO) throws SQLException {
        int id = productDTO.getId();
        String name = productDTO.getName();
        String manufacturer = productDTO.getManufacturer();
        int categoryId = productDTO.getCategory().getId();
        addProduct(id, name, manufacturer, categoryId);
    }

    // public void addNewProduct(ProductDTO productDTO) throws SQLException {
    // if (products.containsKey(productDTO.getId()))
    // throw new SuppliersException("this Product is already exist . product id is:
    // " + productDTO.getName());
    // productsDAO.insert(productDTO);
    // Product product = new Product(productDTO);
    // products.put(product.getId(), product);
    // }

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

    public List<Product> getAllProducts() throws SQLException {
        if(products.size() == 0){
            loadProducts();
        }
        List<Product> res = new ArrayList<>();
        for(Product product : products.values()){
            res.add(product);
        }
        return res;
    }

    private void loadProducts() throws SQLException {
        List<ProductDTO> productsDTOs = ProductsDAO.getInstance().getaAllProducts();
    for(ProductDTO productDTO : productsDTOs){
        Product product = new Product(productDTO);
        products.put(product.getId(),product);
    }
    }

}
