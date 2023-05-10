package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.AgreementAmountToDiscountDTO;
import DataAccessLayer.DTOs.DiscountDTO;

public class AgreementAmountToDiscountDAO extends DAO<AgreementAmountToDiscountDTO> {
    private DiscountDAO discountDAO = null;

    protected AgreementAmountToDiscountDAO() {
        super("AgreementAmountToDiscount");
        discountDAO = DiscountDAO.getInstance();
    }

    @Override
    public AgreementAmountToDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int supplierId = rs.getInt("supplyId");
        int productId = rs.getInt("productId");
        int amount = rs.getInt("amount");
        int discountId = rs.getInt("discount");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new AgreementAmountToDiscountDTO(supplierId, productId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getAgreementAmountToDiscount(int supplierId, int productId)
            throws SQLException {
        TreeMap<Integer, DiscountDTO> amountToDiscount = new TreeMap<>();

        Connection con = Repository.getInstance().connect();

        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ? AND productId = ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, supplierId);
        statement.setInt(2, productId);
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
