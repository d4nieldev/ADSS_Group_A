package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ProductBranchDiscountDTO;
import DataAccessLayer.Repository;

public class ProductBranchDiscountsDAO extends DAO<ProductBranchDiscountDTO> {
    private DiscountDAO discountDAO;
    private Repository repo;
    private static ProductBranchDiscountsDAO instance = null;

    private ProductBranchDiscountsDAO() {
        super("ProductBranchDiscounts");
        this.discountDAO = DiscountDAO.getInstance();
        this.repo = Repository.getInstance();

    }

    public static ProductBranchDiscountsDAO getInstance() {
        if (instance == null)
            instance = new ProductBranchDiscountsDAO();
        return instance;
    }

    @Override
    public ProductBranchDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int productId = rs.getInt("productId");
        int branchId = rs.getInt("branchId");
        int discountId = rs.getInt("discountId");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new ProductBranchDiscountDTO(productId, branchId, discount);
    }

    public DiscountDTO getById(int discountId) throws SQLException {
        return DiscountDAO.getInstance().getById(discountId);
    }

    public DiscountDTO getByIdAndBranch(int productId, int branchId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT discountId FROM " + tableName + " WHERE productId = ? AND branchId = ?;",
                productId, branchId);
        ProductBranchDiscountDTO dto = makeDTO(rs);
        DiscountDTO discountDTO = null;
        if(dto != null) {
             discountDTO = DiscountDAO.getInstance().getById(dto.getDiscount().getId());
        }
        rs.close();
        return discountDTO;
    }
}
