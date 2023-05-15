package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.SelfPickUpSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class SelfPickUpSupplierDAO extends DAO<SelfPickUpSupplierDTO> {
    SupplierDAO supplierDAO;

    private static SelfPickUpSupplierDAO instance = null;

    public static SelfPickUpSupplierDAO getInstance() {
        if (instance == null)
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
        if (dto != null) {
            delete(dto);
            return true;
        }
        return false;
    }

    @Override
    public SelfPickUpSupplierDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("supplierId");
        String address = (String) row.get("address");
        int maxPreperationDays = (int) row.get("maxPreperationDays");

        SupplierDTO supplierDTO = supplierDAO.getById(id);
        return new SelfPickUpSupplierDTO(supplierDTO, address, maxPreperationDays);
    }

    public SelfPickUpSupplierDTO getById(int supplierId) throws SQLException {
        String query = "SELECT * FROM SelfPickupSuppliers WHERE supplierId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

}
