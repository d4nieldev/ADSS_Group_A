package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ContactDTO;

public class ContactDAO extends DAO<ContactDTO> {

    private static ContactDAO instance = null;

    public static ContactDAO getInstance() {
        if (instance == null)
            instance = new ContactDAO();
        return instance;
    }

    private ContactDAO() {
        super("Contacts");
    }

    @Override
    public ContactDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        String phone = rs.getString("phone");
        String name = rs.getString("name");

        return new ContactDTO(supplierId, phone, name);
    }

    public List<ContactDTO> getSupplierContacts(int supplierId) throws SQLException {
        Connection con = Repository.getInstance().connect();
        PreparedStatement statement = con.prepareStatement("SELECT * FROM " + tableName + " WHERE supplierId = ?;");
        statement.setInt(1, supplierId);
        ResultSet rs = statement.executeQuery();
        List<ContactDTO> contacts = new ArrayList<>();
        while (rs.next())
            contacts.add(makeDTO(rs));
        statement.close();
        con.close();
        return contacts;
    }

    public ContactDTO getBySupplierAndPhone(int supplierId, String phone) throws SQLException {
        Connection con = Repository.getInstance().connect();
        PreparedStatement statement = con
                .prepareStatement("SELECT * FROM " + tableName + " WHERE supplierId = ? AND phone = ?;");
        statement.setInt(1, supplierId);
        statement.setString(2, phone);
        ResultSet rs = statement.executeQuery();

        if (!rs.next())
            throw new SQLException("No such contact found with id " + supplierId + " and phone " + phone);
        ContactDTO dto = makeDTO(rs);

        statement.close();
        con.close();
        return dto;
    }

}
