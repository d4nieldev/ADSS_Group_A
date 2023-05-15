package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import org.junit.runner.Result;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.AgreementAmountToDiscountDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

public class ProductAgreementDAO extends DAO<ProductAgreementDTO> {
    private static ProductAgreementDAO instance = null;
    private ProductsDAO productDAO;
    private AgreementAmountToDiscountDAO agreementAmountToDiscountDAO;
    private Repository repo;
    private DiscountDAO discountDAO;

    private ProductAgreementDAO() {
        super("ProductAgreement");
        productDAO = ProductsDAO.getInstance();
        agreementAmountToDiscountDAO = AgreementAmountToDiscountDAO.getInstance();
        repo = Repository.getInstance();
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
    public ProductAgreementDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int supplierId = rs.getInt("supplierId");
        int productId = rs.getInt("productId");
        int stockAmount = rs.getInt("stockAmount");
        double minQuantity = rs.getDouble("basePrice");
        int productSupplierId = rs.getInt("productSupplierId");
        TreeMap<Integer, DiscountDTO> amountToDiscount = agreementAmountToDiscountDAO
                .getAgreementAmountToDiscount(productSupplierId, productId);

        return new ProductAgreementDTO(supplierId, productDAO.getById(productId), stockAmount, minQuantity,
                productSupplierId, amountToDiscount);
    }

    public Collection<ProductAgreementDTO> getAgreementsOfProduct(int productId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId = ?;";
        ResultSet rs = repo.executeQuery(query, productId);

        List<ProductAgreementDTO> output = makeDTOs(rs);

        rs.close();

        return output;
    }

    public Collection<ProductAgreementDTO> getAgreementsOfSupplier(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        ResultSet rs = repo.executeQuery(query, supplierId);

        List<ProductAgreementDTO> output = makeDTOs(rs);

        rs.close();

        return output;
    }

    public ProductAgreementDTO getAgreementByProductAndSupplier(int productId, int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId = ? AND supplierId = ?;";
        ResultSet rs = repo.executeQuery(query, productId, supplierId);
        ProductAgreementDTO paDTO = makeDTO(rs);
        rs.close();
        return paDTO;
    }

}
