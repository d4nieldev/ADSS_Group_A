package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.OnOrderSuppliersDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class OnOrderSuppliersDAO extends DAO<OnOrderSuppliersDTO> {
    SupplierDAO supplierDAO;

    protected OnOrderSuppliersDAO() {
        super("OnOrderSuppliers");
        supplierDAO = SupplierDAO.getInstance();
    }

    @Override
    public void insert(OnOrderSuppliersDTO dataObject) throws SQLException {
        supplierDAO.insert(dataObject.getSuper());
        super.insert(dataObject);
    }

    @Override
    public void update(OnOrderSuppliersDTO newDataObject) throws SQLException {
        supplierDAO.update(newDataObject.getSuper());
        super.update(newDataObject);
    }

    @Override
    public void delete(OnOrderSuppliersDTO dataObject) throws SQLException {
        supplierDAO.delete(dataObject.getSuper());
        super.delete(dataObject);
    }

    @Override
    public OnOrderSuppliersDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        int maxSupplyDays = rs.getInt("maxSupplyDays");

        SupplierDTO supplierDTO = supplierDAO.getById(supplierId);
        return new OnOrderSuppliersDTO(supplierDTO, maxSupplyDays);
    }

}
