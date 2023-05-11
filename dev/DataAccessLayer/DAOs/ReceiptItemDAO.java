package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public ReceiptItemDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int reservationId = rs.getInt("reservationId");
        int productId = rs.getInt("productId");
        int amount = rs.getInt("amount");
        double pricePerUnitBeforeDiscount = rs.getDouble("pricePerUnitBeforeDiscount");
        double pricePerUnitAfterDiscount = rs.getDouble("pricePerUnitAfterDiscount");

        return new ReceiptItemDTO(reservationId, productId, amount, pricePerUnitBeforeDiscount,
                pricePerUnitAfterDiscount);
    }

}
