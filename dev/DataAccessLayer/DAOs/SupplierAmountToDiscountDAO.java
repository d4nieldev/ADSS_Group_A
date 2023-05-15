package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeMap;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.SupplierAmountToDiscountDTO;

public class SupplierAmountToDiscountDAO extends DAO<SupplierAmountToDiscountDTO> {
    private static SupplierAmountToDiscountDAO instance = null;
    private DiscountDAO discountDAO;
    private Repository repo;

    protected SupplierAmountToDiscountDAO() {
        super("SupplierAmountToDiscount");
        discountDAO = DiscountDAO.getInstance();
        repo = Repository.getInstance();
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
            return null;

        int supplierId = rs.getInt("supplierId");
        int amount = rs.getInt("amount");
        int discountId = rs.getInt("discount");

        DiscountDTO discount = DiscountDAO.getInstance().getById(discountId);

        return new SupplierAmountToDiscountDTO(supplierId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getSupplierAmountToDiscount(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        ResultSet rs = repo.executeQuery(query, supplierId);

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
