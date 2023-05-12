package DataAccessLayer.DAOs;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;
import DataAccessLayer.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicReservationDAO extends DAO<PeriodicReservationDTO> {

    private static PeriodicReservationDAO instance = null;
    private PeriodicReservationItemDAO periodicReservationItemDAO;
    private Repository repo;

    public static PeriodicReservationDAO getInstance() {
        if (instance == null)
            instance = new PeriodicReservationDAO();
        return instance;
    }

    private PeriodicReservationDAO() {
        super("PeriodicReservation");
        this.repo = Repository.getInstance();
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
    public PeriodicReservationDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        int branchId = rs.getInt("branchId");
        ProductStatus.Day dayOfOrder = stringToStatus(rs.getString("dayOfOrder"));
        List<PeriodicReservationItemDTO> allItems = periodicReservationItemDAO.getBySupplierAndBrunchId(supplierId,
                branchId);

        return new PeriodicReservationDTO(supplierId, branchId, dayOfOrder, allItems);
    }

    public PeriodicReservationDTO getById(int supplierId, int branchID) throws SQLException {
        String query = "SELECT * FROM PeriodicReservation WHERE supplierId= ? AND branchId= ?;";
        ResultSet rs = repo.executeQuery(query, supplierId, branchID);
        PeriodicReservationDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

    private ProductStatus.Day stringToStatus(String status) {
        switch (status) {
            case "Sunday":
                return ProductStatus.Day.Sunday;
            case "Monday":
                return ProductStatus.Day.Monday;
            case "Tuesday":
                return ProductStatus.Day.Tuesday;
            case "Wednesday":
                return ProductStatus.Day.Wednesday;
            case "Thursday":
                return ProductStatus.Day.Thursday;
            case "Friday":
                return ProductStatus.Day.Friday;
            case "Saturday":
                return ProductStatus.Day.Saturday;
        }
        return null;
    }

    public Map<Integer, PeriodicReservationDTO> getSupplierPeriodicReservations(int supplierId) throws SQLException {

        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        ResultSet rs = repo.executeQuery(query, supplierId);

        Map<Integer, PeriodicReservationDTO> supplierPeriodicReservations = new HashMap<>();
        while (rs.next()) {
            PeriodicReservationDTO PRDTO = makeDTO(rs);
            supplierPeriodicReservations.put(PRDTO.getBranchId(), PRDTO);
        }

        rs.close();

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
        ResultSet rs = repo.executeQuery(query, branchId);
        List<PeriodicReservationDTO> allBranchReservations = new ArrayList<>();
        while (rs.next()) {
            PeriodicReservationDTO periodicReservationDTO = makeDTO(rs);
            allBranchReservations.add(periodicReservationDTO);
        }

        rs.close();
        return allBranchReservations;
    }
}
