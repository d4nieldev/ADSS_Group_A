package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.SuppliersFieldsDTO;

public class SuppliersFieldsDAO extends DAO<SuppliersFieldsDTO> {
    private static SuppliersFieldsDAO instance = null;

    protected SuppliersFieldsDAO() {
        super("SuppliersFields");
    }

    public static SuppliersFieldsDAO getInstance() {
        if (instance == null) {
            instance = new SuppliersFieldsDAO();
        }
        return instance;
    }

    @Override
    public SuppliersFieldsDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        String fieldName = (String) row.get("fieldName");

        return new SuppliersFieldsDTO(supplierId, fieldName);
    }

    public List<SuppliersFieldsDTO> getFieldsOfSupplier(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        List<SuppliersFieldsDTO> fields = makeDTOs(rows);
        return fields;
    }

}