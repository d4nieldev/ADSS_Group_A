package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.OnOrderSuppliersDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class OnOrderSuppliersDAO extends DAO<OnOrderSuppliersDTO> {
    SupplierDAO supplierDAO;

    private static OnOrderSuppliersDAO instance = null;

    public static OnOrderSuppliersDAO getInstance(){
        if(instance == null)
            instance = new OnOrderSuppliersDAO();
        return instance;
    }

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

    public void deleteById(int supplierId) throws SQLException {
        OnOrderSuppliersDTO dto = getById(supplierId);
        delete(dto);
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

    public OnOrderSuppliersDTO getById(int supplierId) throws SQLException {
        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM OnOrderSuppliers WHERE supplierId= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, supplierId);
        ResultSet supRS = statement.executeQuery();
        statement.close();
        con.close();
        return makeDTO(supRS);
    }

}
