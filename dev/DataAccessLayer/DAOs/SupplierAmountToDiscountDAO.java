package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.DiscountDTO;
import DataAccessLayer.DTOs.SupplierAmountToDiscountDTO;

public class SupplierAmountToDiscountDAO extends DAO<SupplierAmountToDiscountDTO> {

    protected SupplierAmountToDiscountDAO() {
        super("SupplierAmountToDiscount");
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

}
