package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.FixedDaysSupplierDTO;
import DataAccessLayer.DTOs.SupplierDTO;

public class FixedDaysSupplierDAO extends DAO<FixedDaysSupplierDTO> {
    SupplierDAO supplierDAO;

    private static FixedDaysSupplierDAO instance = null;

    public static FixedDaysSupplierDAO getInstance() {
        if (instance == null)
            instance = new FixedDaysSupplierDAO();
        return instance;
    }

    protected FixedDaysSupplierDAO() {
        super("FixedDaysSuppliers");
        supplierDAO = SupplierDAO.getInstance();
    }

    @Override
    public void insert(FixedDaysSupplierDTO dataObject) throws SQLException {
        // insert super only if does not exist already
        if (supplierDAO.getById(dataObject.getSuper().getId()) == null)
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

    public boolean deleteById(int supplierId) throws SQLException {
        List<FixedDaysSupplierDTO> dtos = getById(supplierId);
        // TODO: i delete only if i got something.
        if (dtos != null) {
            for (FixedDaysSupplierDTO dto : dtos) {
                delete(dto);
            }
            return true;
        }
        return false;

    }

    @Override
    public FixedDaysSupplierDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int dayOfSupply = (int) row.get("dayOfSupply");

        SupplierDTO supplierDTO = supplierDAO.getById(supplierId);
        return new FixedDaysSupplierDTO(supplierDTO, dayOfSupply);
    }

    public List<FixedDaysSupplierDTO> getById(int supplierId) throws SQLException {
        String query = "SELECT * FROM FixedDaysSuppliers WHERE supplierId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        List<FixedDaysSupplierDTO> fixedDaysSupplierDTOs = makeDTOs(rows);

        if (fixedDaysSupplierDTOs.size() == 0)
            return null;

        return fixedDaysSupplierDTOs;
    }

}
