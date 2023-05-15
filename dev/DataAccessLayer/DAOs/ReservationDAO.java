package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import BusinessLayer.enums.Status;
import DataAccessLayer.DTOs.ContactDTO;
import DataAccessLayer.DTOs.ReceiptItemDTO;
import DataAccessLayer.DTOs.ReservationDTO;

public class ReservationDAO extends DAO<ReservationDTO> {
    private static ReservationDAO instance = null;
    private ReceiptItemDAO receiptItemDAO;
    private ContactDAO contactDAO;

    protected ReservationDAO() {
        super("Reservations");
        receiptItemDAO = ReceiptItemDAO.getInstance();
        contactDAO = ContactDAO.getInstance();
    }

    public static ReservationDAO getInstance() {
        if (instance == null)
            instance = new ReservationDAO();
        return instance;
    }

    @Override
    public ReservationDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("id");
        int supplierId = (int) row.get("supplierId");
        LocalDate date = LocalDate.parse((String) row.get("rDate"));
        Status status = stringToStatus((String) row.get("status"));
        int destinationBranchId = (int) row.get("destinationBranch");
        String contactPhone = (String) row.get("contactPhone");

        ContactDTO contact = contactDAO.getBySupplierAndPhone(supplierId, contactPhone);
        List<ReceiptItemDTO> receipt = receiptItemDAO.getReceiptOfReservation(id);

        return new ReservationDTO(id, supplierId, date, status, destinationBranchId, contact, receipt);
    }

    public List<ReservationDTO> getFullReservation(int reservationId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, reservationId);
        List<ReservationDTO> res = makeDTOs(rows);
        return res;
    }

    public List<ReservationDTO> getSupplierReservations(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);
        List<ReservationDTO> res = makeDTOs(rows);
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
        String query = "SELECT * FROM Reservations WHERE id=(SELECT MAX(id) FROM Reservations)";
        List<Map<String, Object>> rows = repo.executeQuery(query);
        if (rows.size() > 0)
            return makeDTO(rows.get(0)).getId();
        return -1;
    }

}