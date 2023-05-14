package DataAccessLayer.DAOs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import DataAccessLayer.DTOs.PeriodicReservationDTO;
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
            return null;

        int id = rs.getInt("id");
        String name = rs.getString("name");
        int minAmount = rs.getInt("minAmount");
        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
        List<PeriodicReservationDTO> allPeriodicReservationDTO = periodicReservationDAO.getByBranchId(id);
        return new BranchDTO(id, name, minAmount, allPeriodicReservationDTO);
    }

    public BranchDTO getById(int branchId) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM Branches WHERE id= ?;", branchId);
        BranchDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

    public BranchDTO getByName(String name) throws SQLException {
        ResultSet rs = repo.executeQuery("SELECT * FROM Branches WHERE name= ?;", name);
        BranchDTO dto = makeDTO(rs);
        rs.close();
        return dto;
    }

}
