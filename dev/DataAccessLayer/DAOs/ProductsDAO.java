package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductDTO;

public class ProductsDAO extends DAO<ProductDTO> {

    protected ProductsDAO() {
        super("Products");
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
        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM Products WHERE id= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        return makeDTO(rs);
    }

}
