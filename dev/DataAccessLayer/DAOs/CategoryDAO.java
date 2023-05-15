package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import DataAccessLayer.DTOs.CategoryDTO;

public class CategoryDAO extends DAO<CategoryDTO> {
    private static CategoryDAO instance = null;

    protected CategoryDAO() {
        super("Categories");
    }

    public static CategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAO();
        return instance;
    }

    @Override
    public CategoryDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("id");
        String name = (String) row.get("name");
        Object parent = row.get("parent");
        Integer categoryParentId;
        if (Objects.isNull(parent)) {
            categoryParentId = null;
        } else {
            categoryParentId = (Integer) parent;
        }
        CategoryDTO parenCategoryDTO;
        if (categoryParentId == null) {
            parenCategoryDTO = null;
        } else {
            parenCategoryDTO = getById(categoryParentId);
        }
        return new CategoryDTO(id, name, parenCategoryDTO);
    }

    public CategoryDTO getById(int categoryId) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery("SELECT * FROM Categories WHERE id= ?;", categoryId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

}
