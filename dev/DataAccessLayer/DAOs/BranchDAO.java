package DataAccessLayer.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.BranchDTO;

public class BranchDAO extends DAO<BranchDTO>  {

    private static BranchDAO instance = null;

    public static BranchDAO getInstance(){
        if(instance == null)
            instance = new BranchDAO();
        return instance;
    }

    private BranchDAO(){
        super("Branches");
    }

    @Override
    public BranchDTO makeDTO(ResultSet rs) throws SQLException {
        if (!rs.next())
            throw new SQLException("Can't make DTO from nothing!");

        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new BranchDTO(id, name);
    }

    public BranchDTO getById(int branchId) throws SQLException {
        Connection con = Repository.getInstance().connect();
        String query = "SELECT * FROM Branches WHERE id= ?;";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, branchId);
        ResultSet branchRS = statement.executeQuery();
        statement.close();
        con.close();
        return makeDTO(branchRS);
    }
        
}
