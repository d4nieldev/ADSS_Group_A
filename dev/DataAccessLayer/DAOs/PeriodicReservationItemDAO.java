package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;

public class PeriodicReservationItemDAO extends DAO<PeriodicReservationItemDTO> {
    private static PeriodicReservationItemDAO instance = null;
    private Repository repo;

    public static PeriodicReservationItemDAO getInstance() {
        if (instance == null)
            instance = new PeriodicReservationItemDAO();
        return instance;
    }

    private PeriodicReservationItemDAO() {
        super("PeriodicReservationItem");
        this.repo = Repository.getInstance();
    }

    @Override
    public PeriodicReservationItemDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int branchId = (int) row.get("branchId");
        int productId = (int) row.get("productId");
        int amount = (int) row.get("amount");

        return new PeriodicReservationItemDTO(supplierId, branchId, productId, amount);
    }

    public PeriodicReservationItemDTO getById(int supplierId, int branchId, int productId) throws SQLException {
        String query = "SELECT * FROM PeriodicReservationItem WHERE supplierId= ? AND branchId= ? AND productId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId, branchId, productId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

    /***
     * return a list of periodicReservationItemDTO for a specific
     * PeriodicReservation
     * 
     * @param supplierId
     * @param branchId
     * @return
     * @throws SQLException
     */
    public List<PeriodicReservationItemDTO> getBySupplierAndBrunchId(int supplierId, int branchId) throws SQLException {
        String query = "SELECT * FROM PeriodicReservationItem WHERE supplierId= ? AND branchId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId, branchId);
        List<PeriodicReservationItemDTO> periodicReservationItemDTOS = makeDTOs(rows);
        return periodicReservationItemDTOS;
    }

}
