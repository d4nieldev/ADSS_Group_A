package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

public class ProductAgreementDAO extends DAO<ProductAgreementDTO> {
    private static ProductAgreementDAO instance = null;
    private ProductsDAO productDAO;
    private AgreementAmountToDiscountDAO agreementAmountToDiscountDAO;
    private Repository repo;

    private ProductAgreementDAO() {
        super("ProductAgreement");
        productDAO = ProductsDAO.getInstance();
        agreementAmountToDiscountDAO = AgreementAmountToDiscountDAO.getInstance();
        repo = Repository.getInstance();
    }

    public static ProductAgreementDAO getInstance() {
        if (instance == null)
            instance = new ProductAgreementDAO();
        return instance;
    }

    @Override
    public ProductAgreementDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

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

        List<ProductAgreementDTO> output = new ArrayList<>();
        while (rs.next())
            output.add(makeDTO(rs));

        rs.close();

        return output;
    }

    public Collection<ProductAgreementDTO> getAgreementsOfSupplier(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        ResultSet rs = repo.executeQuery(query, supplierId);

        List<ProductAgreementDTO> output = new ArrayList<>();
        while (rs.next())
            output.add(makeDTO(rs));

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
