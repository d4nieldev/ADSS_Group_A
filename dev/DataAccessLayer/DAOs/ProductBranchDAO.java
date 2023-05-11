package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.SpecificProduct;
import DataAccessLayer.DTOs.BranchDTO;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;
import DataAccessLayer.Repository;

import static DataAccessLayer.DAOs.ReportDAO.repo;

public class ProductBranchDAO extends DAO<ProductBranchDTO> {

    private ProductsDAO productDAO;
    private Repository repo;
    private static ProductBranchDAO instance = null;

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
    public ProductBranchDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int productId = rs.getInt("productId");
        int branchId = rs.getInt("branchId");
        double price = rs.getDouble("price");
        int minQuantity = rs.getInt("minQuantity");
        int idealQuantity = rs.getInt("idealQuantity");
        SpecificProductDAO specificProductDAO = SpecificProductDAO.getInstance();
        HashMap<Integer, SpecificProductDTO> specificProductHashMap = specificProductDAO.getByGeneralId(productId,branchId);
        DiscountDTO discountDTO = getByProductAndBranchId(productId,branchId).getDiscountDTO();


        return new ProductBranchDTO(productDAO.getById(productId), discountDTO,branchId, price, minQuantity,idealQuantity,specificProductHashMap);
    }

    public ProductBranchDTO getByProductAndBranchId(int productId,int branchId) throws SQLException {
        String query = "SELECT * FROM SpecificProducts WHERE productId= ? AND branchId= ?;";
        ResultSet rs = repo.executeQuery(query, productId,branchId);
        ProductBranchDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }



}
