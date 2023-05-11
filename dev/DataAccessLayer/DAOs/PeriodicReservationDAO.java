package DataAccessLayer.DAOs;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;
import DataAccessLayer.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodicReservationDAO extends DAO<PeriodicReservationDTO> {

    private static PeriodicReservationDAO instance = null;
    private Repository repo;

    public static PeriodicReservationDAO getInstance(){
        if(instance == null)
            instance = new PeriodicReservationDAO();
        return instance;
    }

    private PeriodicReservationDAO() {
        super("PeriodicReservation");
        this.repo = Repository.getInstance();
    }
    
    @Override
    public PeriodicReservationDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        int branchId = rs.getInt("branchId");
        ProductStatus.Day dayOfOrder = stringToStatus(rs.getString("dayOfOrder"));


        return new PeriodicReservationDTO(supplierId,branchId,dayOfOrder);
    }

    public PeriodicReservationDTO getById(int supplierId, int branchID) throws SQLException {
        String query = "SELECT * FROM PeriodicReservation WHERE supplierId= ? AND branchId= ?;";
        ResultSet rs = repo.executeQuery(query, supplierId,branchID);
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

}
