package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductAgreementDTO;

public class ProductAgreementDAO extends DAO<ProductAgreementDTO> {

    private ProductsDAO productDAO;
    private AgreementAmountToDiscountDAO agreementAmountToDiscountDAO;

    public ProductAgreementDAO() {
        super("ProductAgreement");
        productDAO = ProductsDAO.getInstance();
        agreementAmountToDiscountDAO = AgreementAmountToDiscountDAO.getInstance();
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

}
