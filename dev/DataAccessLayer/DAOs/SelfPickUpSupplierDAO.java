package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class SelfPickUpSupplierDAO extends DAO<SelfPickUpSupplierDTO> {
    SupplierDAO supplierDAO;

    private static SelfPickUpSupplierDAO instance = null;

    public static SelfPickUpSupplierDAO getInstance(){
        if(instance == null)
            instance = new SelfPickUpSupplierDAO();
        return instance;
    }

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

    public boolean deleteById(int supplierId) throws SQLException {
        SelfPickUpSupplierDTO dto = getById(supplierId);
        if(dto != null){
            delete(dto);
            return true;
        }
        return false;
    }

    @Override
    public SelfPickUpSupplierDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int id = rs.getInt("supplierId");
        String address = rs.getString("address");
        int maxPreperationDays = rs.getInt("maxPreperationDays");

        SupplierDTO supplierDTO = supplierDAO.getById(id);
        return new SelfPickUpSupplierDTO(supplierDTO, address, maxPreperationDays);
    }

    public SelfPickUpSupplierDTO getById(int supplierId) throws SQLException {
        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM SelfPickupSuppliers WHERE supplierId= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, supplierId);
        ResultSet supRS = statement.executeQuery();
        //statement.close();
        //con.close();
        SelfPickUpSupplierDTO res = makeDTO(supRS);
        supRS.close();
        return res;
    }



}
