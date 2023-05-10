package DataAccessLayer.DAOs;

import BusinessLayer.Inventory.ProductBranch;
import DataAccessLayer.DTOs.BranchDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class SpecificProductDAO extends DAO<SpecificProductDTO> {
    
    ProductBranchDAO productBrnachDAO;
    BranchDAO BranchDAO;


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

