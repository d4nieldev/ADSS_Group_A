package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.SuppliersFieldsDTO;

public class SuppliersFieldsDAO extends DAO<SuppliersFieldsDTO> {

    protected SuppliersFieldsDAO() {
        super("SuppliersFields");
    }

    @Override
    public SuppliersFieldsDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        String fieldName = rs.getString("fieldName");

        return new SuppliersFieldsDTO(supplierId, fieldName);
    }

    public List<String> getFieldsOfSupplier(int supplierId) throws SQLException {
        Connection con = Repository.getInstance().connect();

        PreparedStatement statement = con.prepareStatement("SELECT * FROM " + tableName + " WHERE supplierId = ?;");

        statement.setInt(1, supplierId);

        ResultSet rs = statement.executeQuery();

        List<String> fields = new ArrayList<>();
        while (rs.next())
            fields.add(rs.getString("fieldName"));

        return fields;
    }

}
