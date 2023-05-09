package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.FixedDaysSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class FixedDaysSupplierDAO extends DAO<FixedDaysSupplierDTO> {
    SupplierDAO supplierDAO;

    protected FixedDaysSupplierDAO() {
        super("FixedDaysSuppliers");
        supplierDAO = SupplierDAO.getInstance();
    }

    @Override
    public void insert(FixedDaysSupplierDTO dataObject) throws SQLException {
        supplierDAO.insert(dataObject.getSuper());
        super.insert(dataObject);
    }

    @Override
    public void update(FixedDaysSupplierDTO newDataObject) throws SQLException {
        supplierDAO.update(newDataObject.getSuper());
        super.update(newDataObject);
    }

    @Override
    public void delete(FixedDaysSupplierDTO dataObject) throws SQLException {
        supplierDAO.delete(dataObject.getSuper());
        super.delete(dataObject);
    }

    @Override
    public FixedDaysSupplierDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        int dayOfSupply = rs.getInt("dayOfSupply");

        SupplierDTO supplierDTO = supplierDAO.getById(supplierId);
        return new FixedDaysSupplierDTO(supplierDTO, dayOfSupply);
    }

}
