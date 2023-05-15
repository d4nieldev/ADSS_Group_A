package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.Map;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDiscountDTO;

public class ProductBranchDiscountsDAO extends DAO<ProductBranchDiscountDTO> {
    private static ProductBranchDiscountsDAO instance = null;
    private DiscountDAO discountDAO;

    private ProductBranchDiscountsDAO() {
        super("ProductBranchDiscounts");
        this.discountDAO = DiscountDAO.getInstance();
    }

    public static ProductBranchDiscountsDAO getInstance() {
        if (instance == null)
            instance = new ProductBranchDiscountsDAO();
        return instance;
    }

    @Override
    public ProductBranchDiscountDTO makeDTO(Map<String, Object> row) throws SQLException {
        int productId = (int) row.get("productId");
        int branchId = (int) row.get("branchId");
        int discountId = (int) row.get("discountId");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new ProductBranchDiscountDTO(productId, branchId, discount);
    }

    public DiscountDTO getById(int discountId) throws SQLException {
        return DiscountDAO.getInstance().getById(discountId);
    }
}
