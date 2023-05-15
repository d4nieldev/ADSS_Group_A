package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.InventoryReportDTO;
import DataAccessLayer.DTOs.InventoryReportEntryDTO;
import DataAccessLayer.DTOs.ReportDTO;

public class InventoryReportEntryDAO extends DAO<InventoryReportEntryDTO> {
    private static InventoryReportEntryDAO instance = null;
    private Repository repo;
    private ReportDAO reportDAO;

    protected InventoryReportEntryDAO() {
        super("InventoryReportEntries");
        repo = Repository.getInstance();
        reportDAO = ReportDAO.getInstance();
    }

    public static InventoryReportEntryDAO getInstance() {
        if (instance == null)
            instance = new InventoryReportEntryDAO();
        return instance;
    }

    @Override
    public InventoryReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int reportId = rs.getInt("entryId");
        int productId = rs.getInt("productId");
        int shelfAmount = rs.getInt("shelfAmount");
        int storageAmount = rs.getInt("storageAmount");

        return new InventoryReportEntryDTO(reportId, productId, shelfAmount, storageAmount);
    }

    public InventoryReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE entryId = ?;";
        ResultSet rs = repo.executeQuery(query, reportId);

        Map<Integer, Integer> idToStorageAmount = new HashMap<>();
        Map<Integer, Integer> idToShelfAmount = new HashMap<>();

        while (rs.next()) {
            int productId = rs.getInt("productId");
            int shelfAmount = rs.getInt("shelfAmount");
            int storageAmount = rs.getInt("storageAmount");

            idToStorageAmount.put(productId, storageAmount);
            idToShelfAmount.put(productId, shelfAmount);
        }

        if (idToStorageAmount.isEmpty())
            return null;

        ReportDTO reportDTO = reportDAO.getById(reportId);
        InventoryReportDTO dto = new InventoryReportDTO(reportDTO, idToStorageAmount, idToShelfAmount);
        rs.close();

        return dto;
    }

}
