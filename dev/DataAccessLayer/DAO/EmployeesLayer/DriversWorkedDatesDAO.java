package DataAccessLayer.DAO.EmployeesLayer;
import java.sql.*;
import java.time.LocalDate;
import DataAccessLayer.Repository;

public class DriversWorkedDatesDAO {
    
    public DriversWorkedDatesDAO() {}
    
    public int addWorkedDates(int empID, LocalDate dateToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(dateToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\");", "DriversWorkedDates", empID, dateToAdd);
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
    
    public int removeWorkedDates(int empID, LocalDate dateToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(dateToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%s\" AND %s=\"%s\";", "DriversWorkedDates", "DriverID", empID,"Date" ,dateToRemove);
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
