package DataAccessLayer.DAO.EmployeesLayer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

public class RolesDAO extends DAO<RoleDTO> {
    
    public RolesDAO() {
        this.tableName = "Roles";
    }
    
    @Override
    public int insert(RoleDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
        } catch (Exception e) {
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }

    @Override
    public int update(RoleDTO updatedOb) { //not allowed to change ID
            Connection conn = Repository.getInstance().connect();
            if (updatedOb == null) return 0;
            String updateString = String.format("UPDATE %s" +
                            " SET \"RoleName\"= \"%s\" " +
                            "WHERE \"RoleID\" == \"%s\";",
                tableName, updatedOb.roleName, updatedOb.roleId);
            Statement s;
            try {
                s = conn.createStatement();
                return s.executeUpdate(updateString);
            } catch (Exception e) {
                return 0;
            }
    }


    @Override
    public RoleDTO makeDTO(ResultSet RS) {
        RoleDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            output = new RoleDTO(/*Id*/RS.getInt(1), /*name*/RS.getString(2));
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }
    
}
