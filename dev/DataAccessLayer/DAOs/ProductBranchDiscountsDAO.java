package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.ProductBranchDiscountDTO;

public class ProductBranchDiscountsDAO extends DAO<ProductBranchDiscountDTO> {

    protected ProductBranchDiscountsDAO(String tableName) {
        super(tableName);
        // TODO Auto-generated constructor stub
    }

    @Override
    public ProductBranchDiscountDTO makeDTO(ResultSet rs) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeDTO'");
    }

}
