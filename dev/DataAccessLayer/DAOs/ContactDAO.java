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
    private Repository repo;

    private ContactDAO() {
        super("Contacts");
        repo = Repository.getInstance();
    }

    public static ContactDAO getInstance() {
        if (instance == null)
            instance = new ContactDAO();
        return instance;
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
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE supplierId = ?;", supplierId);

        List<ContactDTO> contacts = new ArrayList<>();
        while (rs.next())
            contacts.add(makeDTO(rs));

        rs.close();

        return contacts;
    }

    public ContactDTO getBySupplierAndPhone(int supplierId, String phone) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE supplierId = ? AND phone = ?;",
                supplierId, phone);

        if (!rs.next())
            throw new SQLException("No such contact found with id " + supplierId + " and phone " + phone);
        ContactDTO dto = makeDTO(rs);

        rs.close();

        return dto;
    }

}
