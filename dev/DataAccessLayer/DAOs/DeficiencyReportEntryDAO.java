package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.DeficiencyReportEntryDTO;

public class DeficiencyReportEntryDAO extends DAO<DeficiencyReportEntryDTO> {

    protected DeficiencyReportEntryDAO() {
        super("DeficiencyReportEntries");
    }

    @Override
    public DeficiencyReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int reportId = rs.getInt("reportId");
        int productId = rs.getInt("productId");
        int missingAmount = rs.getInt("missingAmount");

        return new DeficiencyReportEntryDTO(reportId, productId, missingAmount);
    }

}
