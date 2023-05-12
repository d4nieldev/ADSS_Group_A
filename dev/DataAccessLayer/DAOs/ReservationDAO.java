package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import BusinessLayer.enums.Status;
import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.ReceiptItemDTO;
import DataAccessLayer.DTOs.ReservationDTO;

public class ReservationDAO extends DAO<ReservationDTO> {
    private static ReservationDAO instance = null;
    private ReceiptItemDAO receiptItemDAO;
    private ContactDAO contactDAO;
    private Repository repo;

    protected ReservationDAO() {
        super("Reservations");
        receiptItemDAO = ReceiptItemDAO.getInstance();
        contactDAO = ContactDAO.getInstance();
        repo = Repository.getInstance();
    }

    public static ReservationDAO getInstance() {
        if (instance == null)
            instance = new ReservationDAO();
        return instance;
    }

    @Override
    public ReservationDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int id = rs.getInt("id");
        int supplierId = rs.getInt("supplierId");
        LocalDate date = LocalDate.parse(rs.getString("rDate"));
        Status status = stringToStatus(rs.getString("status"));
        int destinationBranchId = rs.getInt("destinationBranch");
        String contactPhone = rs.getString("contactPhone");

        ContactDTO contact = contactDAO.getBySupplierAndPhone(supplierId, contactPhone);
        List<ReceiptItemDTO> receipt = receiptItemDAO.getReceiptOfReservation(id);

        return new ReservationDTO(id, supplierId, date, status, destinationBranchId, contact, receipt);
    }

    public List<ReservationDTO> getFullReservation(int reservationId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE id = ?;", reservationId);
        List<ReservationDTO> res = new ArrayList<>();
        while (rs.next())
            res.add(makeDTO(rs));
        rs.close();
        return res;
    }

    public List<ReservationDTO> getSupplierReservations(int supplierId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE supplierId = ?;", supplierId);
        List<ReservationDTO> res = new ArrayList<>();
        while (rs.next())
            res.add(makeDTO(rs));
        rs.close();
        return res;
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

    public int getLastId() throws SQLException {
        String query = "SELECT Max(id) FROM Reservations;";
        ResultSet rs = repo.executeQuery(query);
        ReservationDTO dto = makeDTO(rs);
        if (dto == null) {
            return -1;
        }
        rs.close();
        return dto.getId();
    }

}
