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
    private Repository repo;
    private ReportDAO reportDAO;

    protected InventoryReportEntryDAO() {
        super("InventoryReportEntries");
        repo = Repository.getInstance();
        reportDAO = ReportDAO.getInstance();
    }

    @Override
    public InventoryReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int reportId = rs.getInt("reportId");
        int productId = rs.getInt("productId");
        int shelfAmount = rs.getInt("shelfAmount");
        int storageAmount = rs.getInt("storageAmount");

        return new InventoryReportEntryDTO(reportId, productId, shelfAmount, storageAmount);
    }

    public InventoryReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
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
