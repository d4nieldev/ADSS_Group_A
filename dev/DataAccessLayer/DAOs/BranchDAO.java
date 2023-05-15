package DataAccessLayer.DAOs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DTOs.BranchDTO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;

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
    public BranchDTO makeDTO(Map<String, Object> row) throws SQLException {
        int id = (int) row.get("id");
        String name = (String) row.get("name");
        int minAmount = (int) row.get("minAmount");
        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
        List<PeriodicReservationDTO> allPeriodicReservationDTO = periodicReservationDAO.getByBranchId(id);
        return new BranchDTO(id, name, minAmount, allPeriodicReservationDTO);
    }

    public BranchDTO getById(int branchId) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery("SELECT * FROM Branches WHERE id= ?;", branchId);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));
        return null;
    }

    public BranchDTO getByName(String name) throws SQLException {
        List<Map<String, Object>> rows = repo.executeQuery("SELECT * FROM Branches WHERE name= ?;", name);
        if (rows.size() > 0)
            return makeDTO(rows.get(0));

        return null;
    }

}
