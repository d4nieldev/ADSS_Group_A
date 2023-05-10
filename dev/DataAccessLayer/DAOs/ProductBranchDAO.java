package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.ProductBranchDTO;

public class ProductBranchDAO extends DAO<ProductBranchDTO> {

    private ProductDAO productDAO;

    public ProductBranchDAO(){
        super("ProductBranch");
        productDAO = ProductDAO.getInstance();
    }

    @Override
    public ProductBranchDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int productId = rs.getInt("productId");
        int branchId = rs.getInt("branchId");
        double price = rs.getDouble("price");
        int minQuantity = rs.getInt("minQuantity");
        
        return new ProductBranchDTO(ProductDAO.getById(productId), branchId , price, minQuantity);
    }
    
}
