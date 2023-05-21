package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        if (Ob == null)
            return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resES = insertToEmployeeRoles(Ob);
            if (resES == 1) // If inserts worked
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
        if (Ob == null)
            return 0;
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
    public int update(EmployeeDTO updatedOb) { // not allowed to change ID
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null)
            return 0;
        String updateString = String.format("UPDATE %s" +
                " SET \"FirstName\"= \"%s\", \"LastName\"= \"%s\", \"Password\"= \"%s\", \"BankNumber\"= \"%d\" " +
                ", \"BankBranchNumber\"=\"%d\", \"BankAccountNumber\"=%d,  \"Salary\"=\"%d\", \"Bonus\"=\"%d\" " +
                ", \"startDate\"=\"%s\", \"TempsEmployment\"=%s,  \"IsLoggedIn\"=\"%b\", \"SuperBranch\"=\"%d\" " +
                "WHERE \"ID\" = \"%d\";",
                tableName, updatedOb.firstName, updatedOb.lastName, updatedOb.password, updatedOb.bankNum,
                updatedOb.bankBranch, updatedOb.bankAccount,
                updatedOb.salary, updatedOb.bonus, updatedOb.startDate, updatedOb.tempsEmployment, updatedOb.isLoggedIn,
                updatedOb.superBranch, updatedOb.id);
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
            Integer id = RS.getInt(1); // the first column is ID
            LinkedList<Integer> roles = getRolesList(id, conn);
            LinkedList<Integer> branches = getBranchesList(id, conn);
            if (roles == null) {
                return null;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            output = new EmployeeDTO(/* Id */RS.getInt(1), /* first name */RS.getString(2),
                    /* last name */RS.getString(3), /* password */RS.getString(4),
                    /* bank number */RS.getInt(5), /* bank branch number */RS.getInt(6),
                    /* bank account number */RS.getInt(7), /* salary */RS.getInt(8),
                    /* bonus */ RS.getInt(9), /* start date */ LocalDate.parse(RS.getString(10), formatter),
                    /* temps employment */ RS.getString(11), roles,
                    /* is logged in */ RS.getBoolean(12), /* super branch */ RS.getInt(13), branches);
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    public LinkedList<Integer> getRolesList(Integer id, Connection conn) {
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

    public EmployeeDTO getEmployeeById(int id) {
        Connection conn = Repository.getInstance().connect();
        ResultSet res = get(tableName, "ID", id, conn);
        EmployeeDTO emp = null;

        try {
            if (!res.next()){
                return null;
            }
            emp = makeDTO(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }

        return emp;
    }

    public void getShiftList(Integer id, Connection conn) {

    }

    public LinkedList<Integer> getBranchesList(Integer id, Connection conn) {
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("EmployeesBranches", "EmployeeID", id, conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public void removeAllRolesForEmployee (int employeeId, LinkedList<Integer> employeeRoles) {
        for (Integer roleId : employeeRoles) {
            removeRole(employeeId, roleId);
        }
    }

    // private List<Integer> getAllRolesForEmployee(int id, Connection conn) {
    //     String statement = "SELECT * FROM " + "EmployeesRoles" + " WHERE EmployeeID = " + id + ";";
    //     ResultSet RS = null;
    //     List<Integer> output = new LinkedList<>();
    //     try {
    //         Statement S = conn.createStatement();
    //         RS = S.executeQuery(statement);
    //         while (RS.next()){
    //             output.add(RS.getInt(2));
    //         }    
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     } finally {
    //         Repository.getInstance().closeConnection(conn);
    //     }
    //     return output;
    // }
}
