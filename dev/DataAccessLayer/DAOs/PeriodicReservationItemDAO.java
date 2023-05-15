package DataAccessLayer.DAOs;

import DataAccessLayer.DTOs.PeriodicReservationItemDTO;
import DataAccessLayer.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public PeriodicReservationItemDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int supplierId = rs.getInt("supplierId");
        int branchId = rs.getInt("branchId");
        int productId = rs.getInt("productId");
        int amount = rs.getInt("amount");

        return new PeriodicReservationItemDTO(supplierId, branchId, productId, amount);
    }

    public PeriodicReservationItemDTO getById(int supplierId, int branchId, int productId) throws SQLException {
        String query = "SELECT * FROM PeriodicReservationItem WHERE supplierId= ? AND branchId= ? AND productId= ?;";
        ResultSet rs = repo.executeQuery(query, supplierId, branchId, productId);
        PeriodicReservationItemDTO dto = makeDTO(rs);
        rs.close();
        return dto;
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
        ResultSet rs = repo.executeQuery(query, supplierId, branchId);
        List<PeriodicReservationItemDTO> periodicReservationItemDTOS = makeDTOs(rs);
        rs.close();
        return periodicReservationItemDTOS;
    }

}
