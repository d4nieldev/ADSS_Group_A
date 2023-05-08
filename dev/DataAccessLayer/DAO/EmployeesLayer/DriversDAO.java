package DataAccessLayer.DAO.EmployeesLayer;

import java.sql.*;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

public class DriversDAO extends DAO<DriverDTO> {
    
    public DriversDAO() {
        this.tableName = "Drivers";
    }

    @Override
    public int insert(DriverDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resES = insertToEmployeeRoles(Ob);
            // TODO - add insert statment dor tables drivers in dates worked and constarints for drivers, and add to repository
            if (resES == 1) //If inserts worked
                ans = 1;
            else {
                ans = 0;
            }
        } catch (Exception e) {
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }
    
    private int insertToEmployeeRoles(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesRoles", Ob.getRole());
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(toInsertEmployeeRole);
        } catch (Exception e) {
            return 0;
        }
        return 1;
    }


    @Override
    public int update(DriverDTO updatedOb) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public DriverDTO makeDTO(ResultSet RS) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makeDTO'");
    }
}
