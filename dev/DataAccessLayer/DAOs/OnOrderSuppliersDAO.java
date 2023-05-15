package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.OnOrderSuppliersDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class OnOrderSuppliersDAO extends DAO<OnOrderSuppliersDTO> {
    private static OnOrderSuppliersDAO instance = null;
    private SupplierDAO supplierDAO;

    public static OnOrderSuppliersDAO getInstance() {
        if (instance == null)
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

    public boolean deleteById(int supplierId) throws SQLException {
        OnOrderSuppliersDTO dto = getById(supplierId);
        if (dto != null) {
            delete(dto);
            return true;
        }
        return false;
    }

    @Override
    public OnOrderSuppliersDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int maxSupplyDays = (int) row.get("maxSupplyDays");

        SupplierDTO supplierDTO = supplierDAO.getById(supplierId);
        return new OnOrderSuppliersDTO(supplierDTO, maxSupplyDays);
    }

    public OnOrderSuppliersDTO getById(int supplierId) throws SQLException {
        String query = "SELECT * FROM OnOrderSuppliers WHERE supplierId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

}
