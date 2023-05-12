package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;
import DataAccessLayer.DTOs.ExpiredAndFlawReportEntryDTO;
import DataAccessLayer.DTOs.ReportDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

import BusinessLayer.Inventory.ProductStatus;

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
    public ExpiredAndFlawReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int reportId = rs.getInt("reportId");
        int specificId = rs.getInt("specificId");

        return new ExpiredAndFlawReportEntryDTO(reportId, specificId);
    }

    public ExpiredAndFlawReportDTO getFullReportById(int reportId) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE reportId = ?;";
        ResultSet rs = repo.executeQuery(query, reportId);

        List<SpecificProductDTO> expiredProducts = new ArrayList<>();
        List<SpecificProductDTO> flawProducts = new ArrayList<>();

        while (rs.next()) {
            int specificId = rs.getInt("specificId");
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
        rs.close();

        return dto;
    }

}
