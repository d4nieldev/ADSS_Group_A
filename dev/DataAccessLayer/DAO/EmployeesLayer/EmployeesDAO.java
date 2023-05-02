package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeesDAO extends DAO<EmployeeDTO> {
    private EmployeesRolesDAO employeeRoleDAO;

    public EmployeesDAO() {
        this.tableName = "Employees";
        employeeRoleDAO = new EmployeesRolesDAO();
    }

    @Override
    public int insert(EmployeeDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resES = insertToEmployeeRoles(Ob);
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

    private int insertToEmployeeRoles(EmployeeDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        for (int index = 0; index < Ob.getNumberOfRoles(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesRoles", Ob.getRole(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsertEmployeeRole);
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int update(EmployeeDTO updatedOb) { //not allowed to change ID
            Connection conn = Repository.getInstance().connect();
            if (updatedOb == null) return 0;
            String updateString = String.format("UPDATE %s" +
                            " SET \"FirstName\"= \"%s\", \"LastName\"= \"%s\", \"Password\"= \"%s\", \"BankNumber\"= \"%s\" " +
                            ", \"BankBranchNumber\"=\"%s\", \"BankAccountNumber\"=%s,  \"Salary\"=\"%s\", \"Bonus\"=\"%s\" " +
                            ", \"startDate\"=\"%s\", \"TempsEmployment\"=%s,  \"IsLoggedIn\"=\"%s\", \"SuperBranch\"=\"%s\" " +
                            "WHERE \"ID\" == \"%s\";",
                updatedOb.firstName, updatedOb.lastName, updatedOb.password, updatedOb.bankNum, updatedOb.bankBranch, updatedOb.bankAccount, 
                updatedOb.salary, updatedOb.bonus, updatedOb.startDate, updatedOb.tempsEmployment, updatedOb.isLoggedIn, updatedOb.superBranch);
            Statement s;
            try {
                s = conn.createStatement();
                return s.executeUpdate(updateString);
            } catch (Exception e) {
                return 0;
            }
    }

    @Override
    public EmployeeDTO makeDTO(ResultSet RS) {
        EmployeeDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            String id = RS.getString(1); // the first column is ID
            List<String> skills = getRolesList(id, conn);
            if (skills == null) {
                return null;
            }
            output = new EmployeeDTO(/*Id*/RS.getInt(1), /*first name*/RS.getString(2),
                /*last name*/RS.getString(3), /*password*/RS.getString(4),
                    /*bank number*/RS.getInt(5), /*bank branch number*/RS.getInt(6), 
                    /*bank account number*/RS.getInt(7), /*salary*/RS.getInt(8),
                    /*bonus*/ RS.getInt(9), /*start date*/ new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(10)),
                    /*temps employment*/ RS.getString(11), /*is logged in*/ RS.getBoolean(12),
                    /*super branch*/ RS.getInt(13));
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    // TODO - Implement
    public List<String> getRolesList(String id,Connection conn){return null;}
    
}
