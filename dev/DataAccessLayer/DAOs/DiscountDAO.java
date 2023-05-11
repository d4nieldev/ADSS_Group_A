package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        LocalDate startDate = LocalDate.parse(rs.getString("startDate"));
        LocalDate endDate = LocalDate.parse(rs.getString("endDate"));
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

}
