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

    public static ContactDAO getInstance(){
        if(instance == null)
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
        
        return contacts;
    }

}
