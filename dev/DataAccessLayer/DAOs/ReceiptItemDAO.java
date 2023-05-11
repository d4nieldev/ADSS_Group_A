package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ReceiptItemDTO;

public class ReceiptItemDAO extends DAO<ReceiptItemDTO> {
    private static ReceiptItemDAO instance = null;
    private Repository repo;

    private ReceiptItemDAO() {
        super("ReceiptItem");
        repo = Repository.getInstance();
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

    public List<ReceiptItemDTO> getReceiptOfReservation(int reservationId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE reservationId = ?;", reservationId);

        List<ReceiptItemDTO> receipt = new ArrayList<>();
        while (rs.next())
            receipt.add(makeDTO(rs));

        rs.close();

        return receipt;
    }

}
