package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.BranchDTO;

public class BranchDAO extends DAO<BranchDTO> {

    private static BranchDAO instance = null;
    private Repository repo;

    private BranchDAO() {
        super("Branches");
        Repository.getInstance();
    }

    public static BranchDAO getInstance() {
        if (instance == null)
            instance = new BranchDAO();
        return instance;
    }

    @Override
    public BranchDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        int minAmount = rs.getInt("minAmount");

        return new BranchDTO(id, name,minAmount);
    }

    public BranchDTO getById(int branchId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM Branches WHERE id= ?;", branchId);
        BranchDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

}
