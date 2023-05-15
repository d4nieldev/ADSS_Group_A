package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.InventoryReportDTO;
import DataAccessLayer.DTOs.InventoryReportEntryDTO;
import DataAccessLayer.DTOs.ReportDTO;

public class InventoryReportEntryDAO extends DAO<InventoryReportEntryDTO> {
    private static InventoryReportEntryDAO instance = null;
    private ReportDAO reportDAO;

    protected InventoryReportEntryDAO() {
        super("InventoryReportEntries");
        reportDAO = ReportDAO.getInstance();
    }

    public static InventoryReportEntryDAO getInstance() {
        if (instance == null)
            instance = new InventoryReportEntryDAO();
        return instance;
    }

    @Override
    public InventoryReportEntryDTO makeDTO(Map<String, Object> row) throws SQLException {
        int reportId = (int) row.get("entryId");
        int productId = (int) row.get("productId");
        int shelfAmount = (int) row.get("shelfAmount");
        int storageAmount = (int) row.get("storageAmount");

        return new InventoryReportEntryDTO(reportId, productId, shelfAmount, storageAmount);
    }

    public InventoryReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, reportId);

        Map<Integer, Integer> idToStorageAmount = new HashMap<>();
        Map<Integer, Integer> idToShelfAmount = new HashMap<>();

        for (Map<String, Object> row : rows) {
            int productId = (int) row.get("productId");
            int shelfAmount = (int) row.get("shelfAmount");
            int storageAmount = (int) row.get("storageAmount");

            idToStorageAmount.put(productId, storageAmount);
            idToShelfAmount.put(productId, shelfAmount);
        }

        if (idToStorageAmount.isEmpty())
            return null;

        ReportDTO reportDTO = reportDAO.getById(reportId);
        InventoryReportDTO dto = new InventoryReportDTO(reportDTO, idToStorageAmount, idToShelfAmount);

        return dto;
    }

}
