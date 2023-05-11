package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import BusinessLayer.enums.Status;
import DataAccessLayer.DTOs.ReservationDTO;

public class ReservationDAO extends DAO<ReservationDTO> {

    private SupplierDAO supplierDAO;
    private BranchDAO branchDAO;

    protected ReservationDAO() {
        super("Reservations");
        this.supplierDAO = SupplierDAO.getInstance();
        this.branchDAO = BranchDAO.getInstance();
    }

    @Override
    public ReservationDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        int supplierId = rs.getInt("supplierId");
        LocalDate date = LocalDate.parse(rs.getString("rDate"));
        Status status = stringToStatus(rs.getString("status"));
        int destinationBranch = rs.getInt("destinationBranch");

        return new ReservationDTO(id, supplierDAO.getById(supplierId), date, status,
                branchDAO.getById(destinationBranch));
    }

    private Status stringToStatus(String status) {
        switch (status) {
            case "NOTREADY":
                return Status.NOTREADY;
            case "READY":
                return Status.READY;
            case "CLOSED":
                return Status.CLOSED;
            case "ABORTED":
                return Status.ABORTED;
        }
        return null;
    }

}
