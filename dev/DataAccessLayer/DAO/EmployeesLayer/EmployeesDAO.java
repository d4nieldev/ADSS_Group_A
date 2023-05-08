package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

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
            LinkedList<Integer> roles = getRolesList(id, conn);
            if (roles == null) {
                return null;
            }
            output = new EmployeeDTO(/*Id*/RS.getInt(1), /*first name*/RS.getString(2),
                /*last name*/RS.getString(3), /*password*/RS.getString(4),
                    /*bank number*/RS.getInt(5), /*bank branch number*/RS.getInt(6), 
                    /*bank account number*/RS.getInt(7), /*salary*/RS.getInt(8),
                    /*bonus*/ RS.getInt(9), /*start date*/ LocalDate.parse(RS.getString(10)),
                    /*temps employment*/ RS.getString(11), roles,
                     /*is logged in*/ RS.getBoolean(13), /*super branch*/ RS.getInt(14));
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    public LinkedList<Integer> getRolesList(String id,Connection conn){
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("EmployeesRoles", "EmployeeID", id, conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }
    public int addRole(int empID, Integer roleToAdd) {
        return employeeRoleDAO.addRole(empID, roleToAdd);
    }

    public int removeRole(int empID, Integer roleToRemove) {
        return employeeRoleDAO.removeRole(empID, roleToRemove);
    }
}
