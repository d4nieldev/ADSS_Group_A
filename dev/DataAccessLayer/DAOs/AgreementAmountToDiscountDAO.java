package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
    public AgreementAmountToDiscountDTO makeDTO(Map<String, Object> rs) throws SQLException {
        int supplierId = (int) rs.get("supplyId");
        int productId = (int) rs.get("productId");
        int amount = (int) rs.get("amount");
        int discountId = (int) rs.get("discount");

        DiscountDTO discount = discountDAO.getById(discountId);

        return new AgreementAmountToDiscountDTO(supplierId, productId, amount, discount);
    }

    public TreeMap<Integer, DiscountDTO> getAgreementAmountToDiscount(int supplierId, int productId)
            throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE supplierId = ? AND productId = ?;";
        List<Map<String, Object>> rs = repo.executeQuery(query, supplierId, productId);

        TreeMap<Integer, DiscountDTO> amountToDiscount = new TreeMap<>();
        for (Map<String, Object> row : rs) {
            int amount = (int) row.get("amount");
            int discountId = (int) row.get("discount");
            DiscountDTO discount = discountDAO.getById(discountId);
            amountToDiscount.put(amount, discount);
        }

        return amountToDiscount;

    }

}
