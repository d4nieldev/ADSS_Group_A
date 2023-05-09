package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class EmployeesRolesDAO {
    
    public EmployeesRolesDAO() {}
    
    public int addRole(int empID, Integer roleToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || roleToAdd < 0 || roleToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\");", "EmployeesRoles", empID, roleToAdd);
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
    
    public int removeRole(int empID, Integer roleToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || roleToRemove < 0 || roleToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\";", "EmployeesRoles", "EmployeeID", empID,"Role" ,roleToRemove);
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
