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

    public List<ReceiptItemDTO> getReceiptOfReservation(int reservationId) throws SQLException {
        Connection con = Repository.getInstance().connect();
        PreparedStatement statement = con
                .prepareStatement("SELECT * FROM " + tableName + " WHERE reservationId = ?;");
        statement.setInt(1, reservationId);
        ResultSet rs = statement.executeQuery();

        List<ReceiptItemDTO> receipt = new ArrayList<>();
        while (rs.next())
            receipt.add(makeDTO(rs));

        statement.close();
        con.close();

        return receipt;
    }

}
