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
    public ResultSet getAll(Connection conn, String colName,LocalDate date) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\"", "DriversAvailableShiftDates", colName, date);

        ResultSet RS = null;
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(SELECT_SQL);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } 
        return RS;
    }
    
    public int removeAvailableShiftDates(int empID, LocalDate dateToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(dateToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%d\" AND %s=\"%s\";", "DriversAvailableShiftDates", "DriverID", empID,"Date" ,dateToRemove);
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
