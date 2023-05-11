package DataAccessLayer.DAOs;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DTOs.SpecificProductDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import DataAccessLayer.Repository;

public class SpecificProductDAO extends DAO<SpecificProductDTO> {

    private static SpecificProductDAO instance = null;

    public static SpecificProductDAO getInstance() {
        if (instance == null)
            instance = new SpecificProductDAO();
        return instance;
    }

    private SpecificProductDAO() {
        super("SpecificProducts");
    }

    @Override
    public SpecificProductDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int specificId = rs.getInt("specificId");
        int generalId = rs.getInt("generalId");
        int branchId = rs.getInt("branchId");
        double buyPrice = rs.getDouble("buyPrice");
        double sellPrice = rs.getDouble("sellPrice");
        ProductStatus.status status = stringToStatus(rs.getString("status"));

        String flaw = rs.getString("flaw");
        LocalDate expDate = LocalDate.parse(rs.getString("expDate"));

        return new SpecificProductDTO(specificId, generalId, branchId, buyPrice, sellPrice, status, flaw, expDate);
    }

    public SpecificProductDTO getById(int specificId) throws SQLException {

        Connection con = Repository.getInstance().connect();
        String query = "SELECT * FROM SpecificProducts WHERE id= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, specificId);
        ResultSet catId = statement.executeQuery();

        return makeDTO(catId);
    }

    private ProductStatus.status stringToStatus(String status) {
        switch (status) {
            case "SOLD":
                return ProductStatus.status.SOLD;
            case "IS_FLAW":
                return ProductStatus.status.IS_FLAW;
            case "EXPIRED":
                return ProductStatus.status.EXPIRED;
            case "ABORTED":
                return ProductStatus.status.ON_STORAGE;
        }
        return null;
    }

}
