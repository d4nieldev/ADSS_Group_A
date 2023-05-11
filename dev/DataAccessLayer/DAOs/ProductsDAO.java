package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductDTO;

public class ProductsDAO extends DAO<ProductDTO> {
    private static ProductsDAO instance = null;
    private Repository repo;

    protected ProductsDAO() {
        super("Products");
        repo = Repository.getInstance();
    }

    public static ProductsDAO getInstance() {
        if (instance == null) {
            instance = new ProductsDAO();
        }
        return instance;
    }

    @Override
    public ProductDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String manufacturer = rs.getString("manufacturer");
        int categoryId = rs.getInt("categoryId");

        CategoryDTO category = CategoryDAO.getInstance().getById(categoryId);

        return new ProductDTO(id, name, manufacturer, category);
    }

    public ProductDTO getById(int id) throws SQLException {
        String query = "SELECT * FROM Products WHERE id= ?;";
        ResultSet rs = repo.executeQuery(query, id);
        ProductDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

}
