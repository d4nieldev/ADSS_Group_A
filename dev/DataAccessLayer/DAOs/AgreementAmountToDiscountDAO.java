package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.AgreementAmountToDiscountDTO;
import DataAccessLayer.DTOs.DiscountDTO;

public class AgreementAmountToDiscountDAO extends DAO<AgreementAmountToDiscountDTO> {
    private DiscountDAO discountDAO = null;
    private static AgreementAmountToDiscountDAO instance = null;
    private Repository repo;

    protected AgreementAmountToDiscountDAO() {
        super("AgreementAmountToDiscount");
        discountDAO = DiscountDAO.getInstance();
        repo = Repository.getInstance();
    }

    public static AgreementAmountToDiscountDAO getInstance() {
        if (instance == null)
            instance = new AgreementAmountToDiscountDAO();
        return instance;
    }

    @Override
    public AgreementAmountToDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int supplierId = rs.getInt("supplyId");
        int productId = rs.getInt("productId");
        int amount = rs.getInt("amount");
        int discountId = rs.getInt("discount");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new AgreementAmountToDiscountDTO(supplierId, productId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getAgreementAmountToDiscount(int supplierId, int productId)
            throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ? AND productId = ?;";
        ResultSet rs = repo.executeQuery(query, supplierId, productId);

        TreeMap<Integer, DiscountDTO> amountToDiscount = new TreeMap<>();
        while (rs.next()) {
            int amount = rs.getInt("amount");
            int discountId = rs.getInt("discount");
            DiscountDTO discount = discountDAO.getById(discountId);
            amountToDiscount.put(amount, discount);
        }

        rs.close();

        return amountToDiscount;

    }

}
