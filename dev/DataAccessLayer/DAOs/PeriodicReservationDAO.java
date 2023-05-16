package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;

public class PeriodicReservationDAO extends DAO<PeriodicReservationDTO> {

    private static PeriodicReservationDAO instance = null;
    private PeriodicReservationItemDAO periodicReservationItemDAO;

    public static PeriodicReservationDAO getInstance() {
        if (instance == null)
            instance = new PeriodicReservationDAO();
        return instance;
    }

    private PeriodicReservationDAO() {
        super("PeriodicReservation");
        this.periodicReservationItemDAO = PeriodicReservationItemDAO.getInstance();
    }

    @Override
    public void insert(PeriodicReservationDTO dataObject) throws SQLException {
        super.insert(dataObject);
        // insert all periodic reservation items
        for (PeriodicReservationItemDTO item : dataObject.getAllItems()) {
            periodicReservationItemDAO.insert(item);
        }
    }

    @Override
    public void update(PeriodicReservationDTO dataObject) throws SQLException {
        super.update(dataObject);
        // update all periodic reservation items
        for (PeriodicReservationItemDTO item : dataObject.getAllItems()) {
            periodicReservationItemDAO.update(item);
        }
    }

    @Override
    public PeriodicReservationDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int branchId = (int) row.get("branchId");

        // TODO: i changed to Integer.
        DayOfWeek dayOfOrder = intToStatus((Integer) row.get("dayOfOrder"));
        List<PeriodicReservationItemDTO> allItems = periodicReservationItemDAO.getBySupplierAndBrunchId(supplierId,
                branchId);

        return new PeriodicReservationDTO(supplierId, branchId, dayOfOrder, allItems);
    }

    public PeriodicReservationDTO getById(int supplierId, int branchID) throws SQLException {
        String query = "SELECT * FROM PeriodicReservation WHERE supplierId= ? AND branchId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId, branchID);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

    private DayOfWeek stringToStatus(String status) {
        switch (status) {
            case "Sunday":
                return DayOfWeek.SUNDAY;
            case "Monday":
                return DayOfWeek.MONDAY;
            case "Tuesday":
                return DayOfWeek.TUESDAY;
            case "Wednesday":
                return DayOfWeek.WEDNESDAY;
            case "Thursday":
                return DayOfWeek.THURSDAY;
            case "Friday":
                return DayOfWeek.FRIDAY;
            case "Saturday":
                return DayOfWeek.SATURDAY;
        }
        return null;
    }

    private DayOfWeek intToStatus(Integer status) {
        return DayOfWeek.of(status);
    }

    public Map<Integer, PeriodicReservationDTO> getSupplierPeriodicReservations(int supplierId) throws SQLException {

        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);

        List<PeriodicReservationDTO> dtos = makeDTOs(rows);
        Map<Integer, PeriodicReservationDTO> supplierPeriodicReservations = new HashMap<>();
        for (PeriodicReservationDTO dto : dtos) {
            supplierPeriodicReservations.put(dto.getBranchId(), dto);
        }

        return supplierPeriodicReservations;
    }

    /***
     * return all branch periodic reservation
     * 
     * @param branchId
     * @return
     * @throws SQLException
     */
    public List<PeriodicReservationDTO> getByBranchId(int branchId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE branchId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, branchId);
        List<PeriodicReservationDTO> allBranchReservations = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            PeriodicReservationDTO periodicReservationDTO = makeDTO(row);
            allBranchReservations.add(periodicReservationDTO);
        }

        return allBranchReservations;
    }
}
