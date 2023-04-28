package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;
import java.sql.*;

public class EmployeeRoleDAO extends DAO<EmployeeRoleDTO> {
    
    public EmployeeRoleDAO() {
        this.tableName = "EmployeesRoles";
    }
    
    public int addSkill(String empID, String roleToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || roleToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\");", "EmployeesRoles", empID, roleToAdd);
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
    public int removeSkill(String empID, String roleToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || roleToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%s\" AND %s=\"%s\";", "EmployeesRoles", "EmployeeID", empID,"Role" ,roleToRemove);
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
