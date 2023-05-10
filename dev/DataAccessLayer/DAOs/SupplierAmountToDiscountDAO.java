package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.SupplierAmountToDiscountDTO;

public class SupplierAmountToDiscountDAO extends DAO<SupplierAmountToDiscountDTO> {
    private static SupplierAmountToDiscountDAO instance = null;
    private DiscountDAO discountDAO;

    protected SupplierAmountToDiscountDAO() {
        super("SupplierAmountToDiscount");
        discountDAO = DiscountDAO.getInstance();
    }

    public static SupplierAmountToDiscountDAO getInstance() {
        if (instance == null) {
            instance = new SupplierAmountToDiscountDAO();
        }
        return instance;
    }

    @Override
    public SupplierAmountToDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplierId");
        int amount = rs.getInt("amount");
        int discountId = rs.getInt("discount");

        DiscountDTO discount = DiscountDAO.getInstance().getById(discountId);

        return new SupplierAmountToDiscountDTO(supplierId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getSupplierAmountToDiscount(int supplierId) throws SQLException {
        TreeMap<Integer, DiscountDTO> amountToDiscount = new TreeMap<>();

        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, supplierId);
        ResultSet rs = statement.executeQuery();

        statement.close();
        con.close();

        while (rs.next()) {
            int amount = rs.getInt("amount");
            int discountId = rs.getInt("discount");
            DiscountDTO discount = discountDAO.getById(discountId);
            amountToDiscount.put(amount, discount);
        }

        return amountToDiscount;
    }

}
