package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class EmployeesShiftsFinalsDAO {
    
    public EmployeesShiftsFinalsDAO() {}
    
    public int addShiftFinal(int empID, int shiftID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || shiftID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\");", "EmployeesShiftsFinals", "EmployeesShiftsFinals", empID, shiftID);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    
    public int removeShiftFinal(int empID, int shiftID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || shiftID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\";", "EmployeesShiftsFinals", "EmployeeID", empID, "ShiftID", shiftID);
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

    public int removeAllFromFinalShift(int employeeId) {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(employeeId < 0) return 0;
        updateString = String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%d\";", "EmployeesShiftsFinals", "EmployeeID", employeeId);
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
