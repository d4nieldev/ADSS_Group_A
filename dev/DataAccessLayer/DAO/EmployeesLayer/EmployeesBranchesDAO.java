package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class EmployeesBranchesDAO {
    
    public EmployeesBranchesDAO() {}
    
    public int addOriginEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\",\"%s\");", "EmployeesBranches", empID, branchID, "ORIGIN");
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
    
    public int removeOriginEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\" AND %s=\"%s\"", "EmployeesBranches", "EmployeeID", empID,
                 "BranchID" , branchID, "Status", "ORIGIN");
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
    
    public int addForeignEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\",\"%s\");", "EmployeesBranches", empID, branchID, "FOREIGN");
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
    
    public int removeForeignEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\" AND %s=\"%s\"", "EmployeesBranches", "EmployeeID", empID,
                 "BranchID" , branchID, "Status", "FOREIGN");
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
    
    public int addNotAllowEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%d\",\"%d\",\"%s\");", "EmployeesBranches", empID, branchID, "NOTALLOW");
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
    
    public int removeNotAllowEmployee(int empID, int branchID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || branchID < 0) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %d=\"%d\" AND %d=\"%d\" AND %s=\"%s\"", "EmployeesBranches", "EmployeeID", empID,
                 "BranchID" , branchID, "Status", "NOTALLOW");
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
