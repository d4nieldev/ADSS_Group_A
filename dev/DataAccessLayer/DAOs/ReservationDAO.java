package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BusinessLayer.Suppliers.enums.Status;
import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ReservationDTO;

public class ReservationDAO extends DAO<ReservationDTO> {

    public ReservationDAO() {
        this.tableName = "Reservations";
        this.columnNames = new ArrayList<String>(List.of("id", "supplierId", "rDate", "status", "destinationBranch"));
    }

    public boolean insert(ReservationDTO dataObject) throws SQLException {
        Connection con = Repository.getInstance().connect();
        PreparedStatement statement = con.prepareStatement(
                "INSERT INTO " + this.tableName + "(" + columnsString(columnNames) + ") Values (?, ?, ?, ?, ?)");
        statement.setInt(1, dataObject.getId());
        statement.setInt(2, dataObject.getId());
        statement.setDate(3, dataObject.getDate());
        statement.setString(4, dataObject.getStatus().toString());
        statement.setString(5, dataObject.getDestinationBranch());
        statement.executeUpdate();
        statement.close();
        return true;
    }

    public boolean update(ReservationDTO newDataObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public ReservationDTO makeDTO(ResultSet RS) throws SQLException {
        ReservationDTO res = null;
        res = new ReservationDTO(RS.getInt(1), RS.getInt(2), RS.getDate(3), enumHandler(RS.getString(4)),
                RS.getString(5));
        return res;
    }

    private Status enumHandler(String statusEnum) throws SQLException {
        switch (statusEnum) {
            case "NOTREADY":
                return Status.NOTREADY;
            case "READY":
                return Status.READY;
            case "CLOSED":
                return Status.CLOSED;
            case "ABORTED":
                return Status.ABORTED;
            default:
                throw new SQLException("Reservation Error: Unknown enum");
        }
    }

}