package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    public SupplierAmountToDiscountDTO makeDTO(Map<String, Object> row) throws SQLException {
        int supplierId = (int) row.get("supplierId");
        int amount = (int) row.get("amount");
        int discountId = (int) row.get("discount");

        DiscountDTO discount = DiscountDAO.getInstance().getById(discountId);

        return new SupplierAmountToDiscountDTO(supplierId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getSupplierAmountToDiscount(int supplierId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, supplierId);

        TreeMap<Integer, DiscountDTO> amountToDiscount = new TreeMap<>();
        for (Map<String, Object> row : rows) {
            int amount = (int) row.get("amount");
            int discountId = (int) row.get("discount");
            DiscountDTO discount = discountDAO.getById(discountId);
            amountToDiscount.put(amount, discount);
        }

        return amountToDiscount;
    }

}
