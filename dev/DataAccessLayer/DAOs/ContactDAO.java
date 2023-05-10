package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class ContactDAO extends DAO<ContactDTO> {
    SupplierDAO supplierDAO;

    protected ContactDAO() {
        super("Contacts");
        supplierDAO = SupplierDAO.getInstance();
    }

    @Override
    public ContactDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        String phone = rs.getString("phone");
        String name = rs.getString("name");

        SupplierDTO supplierDTO = supplierDAO.getById(supplierId);

        return new ContactDTO(supplierDTO, phone, name);
    }

}
