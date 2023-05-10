package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class SupplierDAO extends DAO<SupplierDTO> {
    private SuppliersFieldsDAO suppliersFieldsDAO;
    private ContactDAO contactDAO;
    private static SupplierDAO instance = null;

    // public abstract List<FieldDTO> getFieldsBySupplierId(int supplierId);
    // public abstract void addFieldToSupplier(int supplierId, int fieldId);
    // public abstract void removeFieldFromSupplier(int supplierId, int fieldId);
    private SupplierDAO() {
        super("Suppliers");
        suppliersFieldsDAO = new SuppliersFieldsDAO();
        contactDAO = ContactDAO.getInstance();
    }

    public static SupplierDAO getInstance() {
        if (instance == null)
            instance = new SupplierDAO();
        return instance;
    }

    @Override
    public SupplierDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String bankAccount = rs.getString("bankAccount");
        String paymentCondition = rs.getString("paymentCondition");
        List<String> fields = suppliersFieldsDAO.getFieldsOfSupplier(id);
        List<ContactDTO> contacts = contactDAO.getSupplierContacts(id);

        return new SupplierDTO(id, name, bankAccount, paymentCondition, fields, contacts);
    }

    public SupplierDTO getById(int supplierId) throws SQLException {
        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM Suppliers WHERE supplierId= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, supplierId);
        ResultSet supRS = statement.executeQuery();
        return makeDTO(supRS);
    }

}
