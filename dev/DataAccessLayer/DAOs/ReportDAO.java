package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.management.remote.rmi.RMIConnectionImpl;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.ReportDTO;

public class ReportDAO extends DAO<ReportDTO> {
    private static ReportDAO instance = null;
    private static Repository repo;

    protected ReportDAO() {
        super("Reports");
        repo = Repository.getInstance();
    }

    public static ReportDAO getInstance() {
        if (instance == null) {
            instance = new ReportDAO();
        }
        return instance;
    }

    @Override
    public ReportDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int id = rs.getInt("id");
        int branchId = rs.getInt("branchId");
        LocalDate createdDate = LocalDate.parse(rs.getString("createdDate"));

        return new ReportDTO(id, branchId, createdDate);
    }

    public ReportDTO getById(int id) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM " + tableName + " WHERE id = ?;", id);
        ReportDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

}
