package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import DataAccessLayer.DTOs.AgreementAmountToDiscountDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

public class ProductAgreementDAO extends DAO<ProductAgreementDTO> {
    private static ProductAgreementDAO instance = null;
    private ProductsDAO productDAO;
    private AgreementAmountToDiscountDAO agreementAmountToDiscountDAO;
    private DiscountDAO discountDAO;

    private ProductAgreementDAO() {
        super("ProductAgreement");
        productDAO = ProductsDAO.getInstance();
        agreementAmountToDiscountDAO = AgreementAmountToDiscountDAO.getInstance();
        discountDAO = DiscountDAO.getInstance();
    }

    @Override
    public void insert(ProductAgreementDTO dataObject) throws SQLException {
        super.insert(dataObject);
        TreeMap<Integer, DiscountDTO> amountToDiscount = dataObject.getAmountToDiscount();
        for (int amount : amountToDiscount.keySet()) {
            DiscountDTO discountDTO = amountToDiscount.get(amount);
            discountDAO.insert(discountDTO);
            int supplierId = dataObject.getSupplierId();
            int productId = dataObject.getProductDTO().getId();
            AgreementAmountToDiscountDTO dto = new AgreementAmountToDiscountDTO(supplierId, productId, amount,
                    discountDTO);
            agreementAmountToDiscountDAO.insert(dto);
        }
    }

    @Override
    public void update(ProductAgreementDTO newDataObject) throws SQLException {
        super.update(newDataObject);
        TreeMap<Integer, DiscountDTO> amountToDiscount = newDataObject.getAmountToDiscount();
        for (int amount : amountToDiscount.keySet()) {
            DiscountDTO discountDTO = amountToDiscount.get(amount);
            discountDAO.update(discountDTO);
            int supplierId = newDataObject.getSupplierId();
            int productId = newDataObject.getProductDTO().getId();
            AgreementAmountToDiscountDTO dto = new AgreementAmountToDiscountDTO(supplierId, productId, amount,
                    discountDTO);
            agreementAmountToDiscountDAO.insert(dto);
        }
    }

    public static ProductAgreementDAO getInstance() {
        if (instance == null)
            instance = new ProductAgreementDAO();
        return instance;
    }

    @Override
    public ProductAgreementDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int productId = (int) row.get("productId");
        int stockAmount = (int) row.get("stockAmount");
        double minQuantity = (double) row.get("basePrice");
        int productSupplierId = (int) row.get("productSupplierId");
        TreeMap<Integer, DiscountDTO> amountToDiscount = agreementAmountToDiscountDAO
                .getAgreementAmountToDiscount(supplierId, productId);

        return new ProductAgreementDTO(supplierId, productDAO.getById(productId), stockAmount, minQuantity,
                productSupplierId, amountToDiscount);
    }

    public Collection<ProductAgreementDTO> getAgreementsOfProduct(int productId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId);

        List<ProductAgreementDTO> output = makeDTOs(rows);

        return output;
    }

    public Collection<ProductAgreementDTO> getAgreementsOfSupplier(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        List<ProductAgreementDTO> output = makeDTOs(rows);
        return output;
    }

    public ProductAgreementDTO getAgreementByProductAndSupplier(int productId, int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId = ? AND supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId, supplierId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

}
