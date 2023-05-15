package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;
import DataAccessLayer.DTOs.ExpiredAndFlawReportEntryDTO;
import DataAccessLayer.DTOs.ReportDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

public class ExpiredAndFlawReportEntryDAO extends DAO<ExpiredAndFlawReportEntryDTO> {
    private static ExpiredAndFlawReportEntryDAO instance = null;
    private Repository repo;
    private ReportDAO reportDAO;
    private SpecificProductDAO specificProductDAO;

    protected ExpiredAndFlawReportEntryDAO() {
        super("ExpiredAndFlawReportEntries");
        repo = Repository.getInstance();
        reportDAO = ReportDAO.getInstance();
        specificProductDAO = SpecificProductDAO.getInstance();
    }

    public static ExpiredAndFlawReportEntryDAO getInstance() {
        if (instance == null)
            instance = new ExpiredAndFlawReportEntryDAO();
        return instance;
    }

    @Override
    public ExpiredAndFlawReportEntryDTO makeDTO(Map<String, Object> row) throws SQLException {
        int reportId = (int) row.get("reportId");
        int specificId = (int) row.get("specificId");

        return new ExpiredAndFlawReportEntryDTO(reportId, specificId);
    }

    public ExpiredAndFlawReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, reportId);

        List<SpecificProductDTO> expiredProducts = new ArrayList<>();
        List<SpecificProductDTO> flawProducts = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            int specificId = (int) row.get("specificId");
            SpecificProductDTO specificProductDTO = specificProductDAO.getById(specificId);
            if (specificProductDTO.getStatus() == ProductStatus.status.EXPIRED)
                expiredProducts.add(specificProductDTO);
            else if (specificProductDTO.getStatus() == ProductStatus.status.IS_FLAW)
                flawProducts.add(specificProductDTO);
        }

        if (expiredProducts.isEmpty() && flawProducts.isEmpty())
            return null;

        ReportDTO reportDTO = reportDAO.getById(reportId);
        ExpiredAndFlawReportDTO dto = new ExpiredAndFlawReportDTO(reportDTO, expiredProducts, flawProducts);

        return dto;
    }

}
