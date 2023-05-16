package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class SpecificProductDAO extends DAO<SpecificProductDTO> {
    private static SpecificProductDAO instance = null;

    private SpecificProductDAO() {
        super("SpecificProduct");
    }

    public static SpecificProductDAO getInstance() {
        if (instance == null)
            instance = new SpecificProductDAO();
        return instance;
    }

    @Override
    public SpecificProductDTO makeDTO(Map<String, Object> row) throws SQLException {
        int specificId = (int) row.get("specificId");
        int generalId = (int) row.get("generalId");
        int branchId = (int) row.get("branchId");
        double buyPrice = (double) row.get("buyPrice");
        double sellPrice = (double) row.get("sellPrice");
        ProductStatus.status status = stringToStatus((String) row.get("status"));
        String flaw = (String) row.get("flaw");
        LocalDate expDate = LocalDate.parse((String) row.get("expDate"));
        LocalDate arrivedDate = LocalDate.parse((String) row.get("arrivedDate"));

        return new SpecificProductDTO(specificId, generalId, branchId, buyPrice, sellPrice, status, flaw, expDate,
                arrivedDate);
    }

    public SpecificProductDTO getById(int specificId) throws SQLException {
        String query = "SELECT * FROM SpecificProduct WHERE specificId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, specificId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

    public HashMap<Integer, SpecificProductDTO> getByGeneralId(int productId, int branchId) throws SQLException {
        String query = "SELECT * FROM SpecificProduct WHERE specificId= ? AND generalId= ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, productId, branchId);
        List<SpecificProductDTO> dtos = makeDTOs(rows);
        HashMap<Integer, SpecificProductDTO> resultMap = new HashMap<>();
        for (SpecificProductDTO dto : dtos) {
            resultMap.put(dto.getSpecificId(), dto);
        }
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
            case "ON_STORAGE":
                return ProductStatus.status.ON_STORAGE;
            case "ON_SHELF":
                return ProductStatus.status.ON_SHELF;
        }
        return null;
    }

    public int getLastId() throws SQLException {
        String query = "SELECT * FROM SpecificProduct WHERE specificId = (SELECT Max(specificId) FROM SpecificProduct);";
        List<Map<String, Object>> rows = repo.executeQuery(query);
        if (rows.size() > 0)
            return makeDTO(rows.get(0)).getSpecificId();

        return -1;
    }

    public List<SpecificProductDTO> selectAllById(int id) throws SQLException {
        String query = "SELECT * FROM SpecificProduct Where generalId= ?;";
        return makeDTOs(repo.executeQuery(query ,id));
    }

}
