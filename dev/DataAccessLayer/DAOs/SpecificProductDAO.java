package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;

import BusinessLayer.Inventory.ProductStatus;
import BusinessLayer.Inventory.SpecificProduct;
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
            throw new SQLException("Can't make DTO from nothing!");

        int specificId = rs.getInt("specificId");
        int generalId = rs.getInt("generalId");
        int branchId = rs.getInt("branchId");
        double buyPrice = rs.getDouble("buyPrice");
        double sellPrice = rs.getDouble("sellPrice");
        ProductStatus.status status = stringToStatus(rs.getString("status"));
        String flaw = rs.getString("flaw");
        LocalDate expDate = LocalDate.parse(rs.getString("expDate"));
        LocalDate arrivedDate = LocalDate.parse(rs.getString("arrivedDate"));

        return new SpecificProductDTO(specificId, generalId, branchId, buyPrice, sellPrice, status, flaw, expDate,arrivedDate);
    }

    public SpecificProductDTO getById(int specificId) throws SQLException {
        String query = "SELECT * FROM SpecificProducts WHERE specificId= ?;";
        ResultSet rs = repo.executeQuery(query, specificId);
        SpecificProductDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }
    public HashMap<Integer, SpecificProductDTO> getByGeneralId(int productId, int branchId) throws SQLException {
        String query = "SELECT * FROM SpecificProducts WHERE specificId= ? AND generalId= ?;";
        ResultSet rs = repo.executeQuery(query, productId,branchId);
        HashMap<Integer, SpecificProductDTO> resultMap = new HashMap<>();

        while (rs.next()) {
            SpecificProductDTO dto = makeDTO(rs);
            resultMap.put(dto.getSpecificId(), dto);
        }

        rs.close();
        return resultMap;
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
