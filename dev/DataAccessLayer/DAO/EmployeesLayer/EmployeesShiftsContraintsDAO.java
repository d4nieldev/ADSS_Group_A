package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class EmployeesShiftsContraintsDAO {
    
    public EmployeesShiftsContraintsDAO() {}
    
    public int addConstraint(int empID, int shiftID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || shiftID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\");", "EmployeesShiftsContraints", shiftID, empID);
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
    
    public int removeConstraint(int empID, int shiftID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || shiftID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\";", "EmployeesShiftsContraints", "EmployeeID", empID, "ShiftID", shiftID);
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
