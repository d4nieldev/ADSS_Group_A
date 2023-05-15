package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.ContactDTO;

public class ContactDAO extends DAO<ContactDTO> {
    private static ContactDAO instance = null;

    private ContactDAO() {
        super("Contacts");
    }

    public static ContactDAO getInstance() {
        if (instance == null)
            instance = new ContactDAO();
        return instance;
    }

    @Override
    public ContactDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        String phone = (String) row.get("phone");
        String name = (String) row.get("name");

        return new ContactDTO(supplierId, phone, name);
    }

    public List<ContactDTO> getSupplierContacts(int supplierId) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery("SELECT * FROM " + tableName + " WHERE supplierId = ?;",
                supplierId);

        List<ContactDTO> contacts = makeDTOs(rows);

        return contacts;
    }

    public ContactDTO getBySupplierAndPhone(int supplierId, String phone) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery(
                "SELECT * FROM " + tableName + " WHERE supplierId = ? AND phone = ?;",
                supplierId, phone);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

}
