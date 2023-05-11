package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.DTOs.ExpiredAndFlawReportEntryDTO;

public class ExpiredAndFlawReportEntryDAO extends DAO<ExpiredAndFlawReportEntryDTO> {
    protected ExpiredAndFlawReportEntryDAO() {
        super("ExpiredAndFlawReportEntries");
    }

    @Override
    public ExpiredAndFlawReportEntryDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int reportId = rs.getInt("reportId");
        int specificId = rs.getInt("specificId");

        return new ExpiredAndFlawReportEntryDTO(reportId, specificId);
    }

}
