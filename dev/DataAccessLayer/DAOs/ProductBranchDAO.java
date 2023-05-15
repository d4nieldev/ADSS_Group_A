package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class ProductBranchDAO extends DAO<ProductBranchDTO> {
    private static ProductBranchDAO instance = null;
    private ProductsDAO productDAO;
    private Repository repo;

    private ProductBranchDAO() {
        super("ProductBranch");
        productDAO = ProductsDAO.getInstance();
        this.repo = Repository.getInstance();
    }

    public static ProductBranchDAO getInstance() {
        if (instance == null)
            instance = new ProductBranchDAO();
        return instance;
    }

    @Override
    public ProductBranchDTO makeDTO(Map<String, Object> row) throws SQLException {
        int productId = (int) row.get("productId");
        int branchId = (int) row.get("branchId");
        double price = (double) row.get("price");
        int minQuantity = (int) row.get("minQuantity");
        int idealQuantity = (int) row.get("idealQuantity");
        SpecificProductDAO specificProductDAO = SpecificProductDAO.getInstance();
        HashMap<Integer, SpecificProductDTO> specificProductHashMap = specificProductDAO.getByGeneralId(productId,
                branchId);
        DiscountDTO discountDTO = getByProductAndBranchId(productId, branchId).getDiscountDTO();

        return new ProductBranchDTO(productDAO.getById(productId), discountDTO, branchId, price, minQuantity,
                idealQuantity, specificProductHashMap);
    }

    public ProductBranchDTO getByProductAndBranchId(int productId, int branchId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId= ? AND branchId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId, branchId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

    public ProductBranchDTO getByProductAndBranch(int productId, int branchId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE productId = ? AND branchId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId, branchId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

}
