package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.SuppliersFieldsDTO;

public class SuppliersFieldsDAO extends DAO<SuppliersFieldsDTO> {
    private static SuppliersFieldsDAO instance = null;
    private Repository repo;

    protected SuppliersFieldsDAO() {
        super("SuppliersFields");
        repo = Repository.getInstance();
    }

    public static SuppliersFieldsDAO getInstance() {
        if (instance == null) {
            instance = new SuppliersFieldsDAO();
        }
        return instance;
    }

    @Override
    public SuppliersFieldsDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        String fieldName = rs.getString("fieldName");

        return new SuppliersFieldsDTO(supplierId, fieldName);
    }

    public List<SuppliersFieldsDTO> getFieldsOfSupplier(int supplierId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE supplierId = ?;", supplierId);
        List<SuppliersFieldsDTO> fields = new ArrayList<>();
        while (rs.next()) {
            SuppliersFieldsDTO field = makeDTO(rs);
            fields.add(field);
        }
        rs.close();
        return fields;
    }

}
