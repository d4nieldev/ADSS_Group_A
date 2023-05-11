package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class SpecificProductDAO extends DAO<SpecificProductDTO> {
    private static SpecificProductDAO instance = null;
    private Repository repo;

    private SpecificProductDAO() {
        super("SpecificProducts");
        this.repo = Repository.getInstance();
    }

    public static SpecificProductDAO getInstance() {
        if (instance == null)
            instance = new SpecificProductDAO();
        return instance;
    }

    @Override
    public SpecificProductDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

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
        String query = "SELECT * FROM SpecificProducts WHERE id= ?;";
        ResultSet rs = repo.executeQuery(query, specificId);
        SpecificProductDTO dto = makeDTO(rs);
        rs.close();
        return dto;
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
