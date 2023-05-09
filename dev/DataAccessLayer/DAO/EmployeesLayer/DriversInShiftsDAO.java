package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class DriversInShiftsDAO {
    
    public DriversInShiftsDAO() {}
    
    public int addDriverInShift(int shiftID, Integer driverID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(shiftID < 0 || driverID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\");", "DriversInShifts", shiftID, driverID);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }
    
    public int removeDriverInShift(int shiftID, Integer driverID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(shiftID < 0 || driverID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\";", "DriversInShifts", "ShiftID", shiftID, "DriverID", driverID);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }
    
}
