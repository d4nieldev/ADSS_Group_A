package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class NumEmployeesForRolesDAO {
    
    public NumEmployeesForRolesDAO() {}
    
    public int addNumEmployeeForRole(int shiftID, int roleID, int numberNedded)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(shiftID < 0 || roleID < 0 || numberNedded < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\",\"%d\");", "NumEmployeesForRoles", shiftID, roleID, numberNedded);
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
    
    public int removeNumEmployeeForRole(int shiftID, int roleID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(shiftID < 0 || roleID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\";", "EmployeesRoles", "ShiftID", shiftID, "RoleID", roleID);
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
