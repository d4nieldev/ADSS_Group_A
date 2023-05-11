package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.CategoryDTO;

public class CategoryDAO extends DAO<CategoryDTO> {
    private static CategoryDAO instance = null;
    private Repository repo;

    protected CategoryDAO() {
        super("Categories");
        repo = Repository.getInstance();
    }

    public static CategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAO();
        return instance;
    }

    @Override
    public CategoryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String categoryParentId = rs.getString("parent");
        CategoryDTO parenCategoryDTO;
        if (categoryParentId == null) {
            parenCategoryDTO = null;
        } else {
            parenCategoryDTO = getById(Integer.parseInt(categoryParentId));
        }
        return new CategoryDTO(id, name, parenCategoryDTO);
    }

    public CategoryDTO getById(int categoryId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM Categories WHERE id= ?;", categoryId);
        CategoryDTO categoryDTO = makeDTO(rs);

        rs.close();

        return categoryDTO;
    }

}
