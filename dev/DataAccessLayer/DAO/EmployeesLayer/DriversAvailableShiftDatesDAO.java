package DataAccessLayer.DAO.EmployeesLayer;
import java.sql.*;
import java.time.LocalDate;
import DataAccessLayer.Repository;

public class DriversAvailableShiftDatesDAO {
    
    public DriversAvailableShiftDatesDAO() {}
    
    public int addAvailableShiftDates(int empID, LocalDate dateToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(dateToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%s\");", "DriversAvailableShiftDates", empID, dateToAdd);
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
    
    public int removeAvailableShiftDates(int empID, LocalDate dateToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(dateToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %s=\"%s\";", "DriversAvailableShiftDates", "DriverID", empID,"Date" ,dateToRemove);
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
