package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DeficiencyReportDTO;
import DataAccessLayer.DTOs.DeficiencyReportEntryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ReportDTO;

public class DeficiencyReportEntryDAO extends DAO<DeficiencyReportEntryDTO> {
    private static DeficiencyReportEntryDAO instance = null;
    private Repository repo;
    private ProductBranchDAO productBranchDAO;
    private ReportDAO reportDAO;

    protected DeficiencyReportEntryDAO() {
        super("DeficiencyReportEntries");
        repo = Repository.getInstance();
        productBranchDAO = ProductBranchDAO.getInstance();
        reportDAO = ReportDAO.getInstance();
    }

    public static DeficiencyReportEntryDAO getInstance() {
        if (instance == null)
            instance = new DeficiencyReportEntryDAO();
        return instance;
    }

    @Override
    public DeficiencyReportEntryDTO makeDTO(Map<String, Object> row) throws SQLException {
        int reportId = (int) row.get("reportId");
        int productId = (int) row.get("productId");
        int missingAmount = (int) row.get("missingAmount");

        return new DeficiencyReportEntryDTO(reportId, productId, missingAmount);
    }

    public DeficiencyReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
        List<Map<String, Object>> rows = repo.executeQuery(query, reportId);

        ReportDTO report = reportDAO.getById(reportId);

        Map<Integer, ProductBranchDTO> idToProductBranch = new HashMap<>();
        Map<Integer, Integer> idToMissingAmount = new HashMap<>();

        for (Map<String, Object> row : rows) {
            int productId = (int) row.get("productId");
            int missingAmount = (int) row.get("missingAmount");
            ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranch(productId, report.getBranchId());
            idToProductBranch.put(productId, productBranchDTO);
            idToMissingAmount.put(productId, missingAmount);
        }

        if (idToProductBranch.isEmpty())
            return null;

        return new DeficiencyReportDTO(report, idToProductBranch, idToMissingAmount);
    }

}
