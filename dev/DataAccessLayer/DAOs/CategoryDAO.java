package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.CategoryDTO;

public class CategoryDAO extends DAO<CategoryDTO> {

    public CategoryDAO(){
        super("Categories");
    }

    @Override
    public CategoryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String categoryParentId = rs.getString("parent");
        CategoryDTO parenCategoryDTO;
        if(categoryParentId == null){
            parenCategoryDTO = null;
        }else{
            parenCategoryDTO = getById(Integer.parseInt(categoryParentId));
        }
        return new CategoryDTO(id, name, parenCategoryDTO);
    }

    public CategoryDTO getById(int categoryId) throws SQLException {
        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM Categories WHERE id= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, categoryId);
        ResultSet catId = statement.executeQuery();
        statement.close();
        con.close();
        return makeDTO(catId);
    }
    
}
