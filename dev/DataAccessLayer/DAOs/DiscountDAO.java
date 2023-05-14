package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.DiscountDTO;

public class DiscountDAO extends DAO<DiscountDTO> {
    private static DiscountDAO instance = null;
    private Repository repo;

    protected DiscountDAO() {
        super("Discounts");
        repo = Repository.getInstance();
    }

    public static DiscountDAO getInstance() {
        if (instance == null) {
            instance = new DiscountDAO();
        }
        return instance;
    }

    @Override
    public DiscountDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            return null;

        int id = rs.getInt("id");
        LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
        String endDateString = rs.getString("endDate");
        LocalDate endDate = null;
        if (endDateString != null) {
            endDate = LocalDate.parse(endDateString);
        }
        double val = rs.getDouble("val");
        String dType = rs.getString("dType");

        return new DiscountDTO(id, startDate, endDate, val, dType);
    }

    public DiscountDTO getById(int id) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM Discounts WHERE id = ?;", id);
        DiscountDTO discount = makeDTO(rs);
        rs.close();
        return discount;
    }

    public int getLastId() throws SQLException {
        String query = "SELECT * FROM Discounts WHERE id = (SELECT Max(id) FROM Discounts);";
        ResultSet rs = repo.executeQuery(query);
        DiscountDTO dto = makeDTO(rs);
        if (dto == null) {
            return -1;
        }
        rs.close();
        return dto.getId();
    }

}
