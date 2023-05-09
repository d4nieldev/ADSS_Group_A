package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class SelfPickUpSupplierDAO extends DAO<SelfPickUpSupplierDTO> {
    SupplierDAO supplierDAO;

    public SelfPickUpSupplierDAO() {
        super("SelfPickUpSuppliers");
        supplierDAO = SupplierDAO.getInstance();
    }

    @Override
    public void insert(SelfPickUpSupplierDTO dataObject) throws SQLException {
        supplierDAO.insert(dataObject.getSuper());
        super.insert(dataObject);
    }

    @Override
    public void update(SelfPickUpSupplierDTO newDataObject) throws SQLException {
        supplierDAO.update(newDataObject.getSuper());
        super.update(newDataObject);
    }

    @Override
    public void delete(SelfPickUpSupplierDTO dataObject) throws SQLException {
        supplierDAO.delete(dataObject.getSuper());
        super.delete(dataObject);
    }

    @Override
    public SelfPickUpSupplierDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Cannot make DTO from nothing!");

        int id = rs.getInt("supplierId");
        String address = rs.getString("address");
        int maxPreperationDays = rs.getInt("maxPreperationDays");

        SupplierDTO supplierDTO = supplierDAO.getById(id);
        return new SelfPickUpSupplierDTO(supplierDTO, address, maxPreperationDays);
    }

}
