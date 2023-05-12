package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDiscountDTO;
import DataAccessLayer.Repository;

public class ProductBranchDiscountsDAO extends DAO<ProductBranchDiscountDTO> {
    private DiscountDAO discountDAO;
    private Repository repo;
    private static ProductBranchDiscountsDAO instance = null;

    private ProductBranchDiscountsDAO() {
        super("ProductBranchDiscounts");
        this.discountDAO = DiscountDAO.getInstance();
        Repository.getInstance();
    }
    public static ProductBranchDiscountsDAO getInstance() {
        if (instance == null)
            instance = new ProductBranchDiscountsDAO();
        return instance;
    }

    @Override
    public ProductBranchDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int productId = rs.getInt("productId");
        int branchId = rs.getInt("branchId");
        int discountId = rs.getInt("discountId");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new ProductBranchDiscountDTO(productId, branchId, discount);
    }

    public DiscountDTO getById(int discountId) throws SQLException {
        return  DiscountDAO.getInstance().getById(discountId);
    }
}
