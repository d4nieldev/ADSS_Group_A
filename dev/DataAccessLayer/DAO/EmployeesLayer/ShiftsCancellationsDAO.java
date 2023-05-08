package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import java.sql.*;

public class ShiftsCancellationsDAO {
    
    
    public ShiftsCancellationsDAO() {}
    
    public int addCancellation(int empID, Integer ProductCode, Integer ProductID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || ProductCode == null || ProductID == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\",\"%s\");", "ShiftsCancellations", empID, ProductCode, ProductID);
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
    
    public int removeCancellation(int empID, Integer ProductCode, Integer ProductID)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID < 0 || ProductCode == null || ProductID == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%s\" AND %s=\"%s\" AND %s=\"%s\";", "ShiftsCancellations", "ShiftID", empID, 
                "ProductCode" , ProductCode, "ProductID", ProductID);
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
