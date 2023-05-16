package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDiscountDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class ProductBranchDiscountsDAO extends DAO<ProductBranchDiscountDTO> {
    private DiscountDAO discountDAO;
    private static ProductBranchDiscountsDAO instance = null;

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

    public DiscountDTO getByIdAndBranch(int productId, int branchId) throws SQLException {
        String query = "SELECT discountId FROM " + tableName + " WHERE productId = ? AND branchId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId, branchId);
        ProductBranchDiscountDTO dto = null;
        if (rows.size() > 0)
            dto = makeDTO(rows.get(0));

        DiscountDTO discountDTO = null;
        if (dto != null) {
            discountDTO = DiscountDAO.getInstance().getById(dto.getDiscount().getId());
        }

        return discountDTO;
    }

    public List<ProductBranchDiscountDTO> selectAllById(int id) throws SQLException {
        String query = "SELECT * FROM ProductBranchDiscounts Where generalId= ?;";
        return makeDTOs(repo.executeQuery(query, id));
    }
}
