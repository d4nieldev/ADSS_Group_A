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
                "VALUES (\"%d\",\"%d\",\"%d\");", "ShiftsCancellations", empID, ProductCode, ProductID);
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
                "WHERE %d=\"%d\" AND %d=\"%d\" AND %d=\"%d\";", "ShiftsCancellations", "ShiftID", empID, 
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
