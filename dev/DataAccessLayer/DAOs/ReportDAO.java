package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.InventoryReportDTO;
import DataAccessLayer.DTOs.ReportDTO;

public class ReportDAO extends DAO<ReportDTO> {
    private static ReportDAO instance = null;

    protected ReportDAO() {
        super("Reports");
    }

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    @Override
    public ReportDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("id");
        int branchId = (int) row.get("branchId");
        LocalDate createdDate = LocalDate.parse((String) row.get("createdDate"));

        return new ReportDTO(id, branchId, createdDate);
    }

    public ReportDTO getById(int id) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery("SELECT * FROM " + tableName + " WHERE id = ?;", id);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

    public int getLastId() throws SQLException {
        String query = "SELECT * FROM Reports WHERE id = (SELECT Max(id) FROM Reports);";
        List<Map<String, Object>> rows = repo.executeQuery(query);
        if (rows.size() > 0)
            return makeDTO(rows.get(0)).getId();

        return -1;
    }
}
