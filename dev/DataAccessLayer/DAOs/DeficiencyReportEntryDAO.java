package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
    public DeficiencyReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int reportId = rs.getInt("reportId");
        int productId = rs.getInt("productId");
        int missingAmount = rs.getInt("missingAmount");

        return new DeficiencyReportEntryDTO(reportId, productId, missingAmount);
    }

    public DeficiencyReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
        ResultSet rs = repo.executeQuery(query, reportId);

        ReportDTO report = reportDAO.getById(reportId);

        Map<Integer, ProductBranchDTO> idToProductBranch = new HashMap<>();
        Map<Integer, Integer> idToMissingAmount = new HashMap<>();

        while (rs.next()) {
            int productId = rs.getInt("productId");
            int missingAmount = rs.getInt("missingAmount");
            ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranch(productId, report.getBranchId());
            idToProductBranch.put(productId, productBranchDTO);
            idToMissingAmount.put(productId, missingAmount);
        }

        if (idToProductBranch.isEmpty())
            return null;

        DeficiencyReportDTO dto = new DeficiencyReportDTO(report, idToProductBranch, idToMissingAmount);
        rs.close();

        return dto;
    }

}
