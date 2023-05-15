package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.ReceiptItemDTO;

public class ReceiptItemDAO extends DAO<ReceiptItemDTO> {
    private static ReceiptItemDAO instance = null;

    private ReceiptItemDAO() {
        super("ReceiptItem");
    }

    public static ReceiptItemDAO getInstance() {
        if (instance == null) {
            instance = new ReceiptItemDAO();
        }
        return instance;
    }

    @Override
    public ReceiptItemDTO makeDTO(Map<String, Object> row) throws SQLException {
        int reservationId = (int) row.get("reservationId");
        int productId = (int) row.get("productId");
        int amount = (int) row.get("amount");
        double pricePerUnitBeforeDiscount = (double) row.get("pricePerUnitBeforeDiscount");
        double pricePerUnitAfterDiscount = (double) row.get("pricePerUnitAfterDiscount");

        return new ReceiptItemDTO(reservationId, productId, amount, pricePerUnitBeforeDiscount,
                pricePerUnitAfterDiscount);
    }

    public List<ReceiptItemDTO> getReceiptOfReservation(int reservationId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reservationId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, reservationId);
        List<ReceiptItemDTO> receipt = makeDTOs(rows);
        return receipt;
    }

}
