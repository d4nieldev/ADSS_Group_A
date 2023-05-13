package DataAccessLayer.DAO.EmployeesLayer;
import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;

public class BranchesDAO extends DAO<BranchDTO> {
    private EmployeesBranchesDAO employeesBranchesDAO;
    private static Map<Integer, BranchDTO> BRANCH_IDENTITY_MAP = new HashMap<>();

    public BranchesDAO() {
        this.tableName = "Branches";
        employeesBranchesDAO = new EmployeesBranchesDAO();
    }

    @Override
    public int insert(BranchDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resES = insertToEmployeeBranches(Ob);
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

    private int insertToEmployeeBranches(BranchDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfOriginEmployees(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesBranches", Ob.getOriginEmployee(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsertEmployeeRole);
            } catch (Exception e) {
                return 0;
            }
        }
        for (int index = 0; index < Ob.getNumberOfForeignEmployees(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesBranches", Ob.getForeignEmployee(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsertEmployeeRole);
            } catch (Exception e) {
                return 0;
            }
        }
        for (int index = 0; index < Ob.getNumberOfnotAllowEmployees(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesBranches", Ob.getnotAllowEmployee(index));
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
    public int update(BranchDTO updatedOb) { // not allowed to change ID
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null)
            return 0;
        String updateString = String.format("UPDATE %s" +
                " SET \"Address\"= \"%s\", \"Location\"= \"%s\" " +
                "WHERE \"BranchID\" = \"%d\";",
                tableName, updatedOb.address, updatedOb.location, updatedOb.branchId);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public BranchDTO makeDTO(ResultSet RS) {
        BranchDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            //Integer idEmployee = RS.getInt(1); // the first column is ID employee
            Integer idBranch = RS.getInt(1); // the second column is ID branch
            LinkedList<Integer> originEmployees = getOriginEmployeesList(idBranch, conn);
            if (originEmployees == null) {
                return null;
            }
            LinkedList<Integer> foreignEmployees = getForeignEmployeesList(idBranch, conn);
            if (foreignEmployees == null) {
                return null;
            }
            LinkedList<Integer> notAllowEmployees = getNotAllowEmployeesList(idBranch, conn);
            if (notAllowEmployees == null) {
                return null;
            }
            output = new BranchDTO(/* branch Id */RS.getInt(1), /* address */RS.getString(2),
                    /* location */RS.getString(3), originEmployees, foreignEmployees, notAllowEmployees);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    public LinkedList<Integer> getOriginEmployeesList(Integer idBranch, Connection conn) {
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("EmployeesBranches", "BranchID", idBranch,
                             "Status", "ORIGIN", conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(1));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public LinkedList<Integer> getForeignEmployeesList(Integer idBranch, Connection conn) {
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("EmployeesBranches", "BranchID", idBranch,
                             "Status", "FOREIGN", conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(1));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }
    
    public LinkedList<Integer> getNotAllowEmployeesList(Integer idBranch, Connection conn) {
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("EmployeesBranches", "BranchID", idBranch,
                             "Status", "NOTALLOW", conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(1));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public BranchDTO getBranchById(int id) {

        BranchDTO bra = BRANCH_IDENTITY_MAP.get(id);
        if (bra != null)
            return bra;

        Connection conn = Repository.getInstance().connect();
        ResultSet res = get(tableName, "BranchID", id, conn);

        BranchDTO newBra = null;

        try {
            if (!res.next()){
                return null;
            }
            newBra = makeDTO(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }
    
        BRANCH_IDENTITY_MAP.put(id, newBra);
        return newBra;
    }

    public BranchDTO getBranchByAddress(String address) {

        for (Integer intBranch : BRANCH_IDENTITY_MAP.keySet()) {
            BranchDTO bra = BRANCH_IDENTITY_MAP.get(intBranch);
            if(bra.getBranchAddress() == address){
                return bra; 
            }
        }

        Connection conn = Repository.getInstance().connect();
        ResultSet res = get(tableName, "Address", address, conn);

        BranchDTO newBra = null;

        try {
            if (!res.next()){
                return null;
            }
            newBra = makeDTO(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }
    
        BRANCH_IDENTITY_MAP.put(newBra.branchId, newBra);
        return newBra;
    }

    public int addOriginEmployee(int empID, int branchID) {
        return employeesBranchesDAO.addOriginEmployee(empID, branchID);
    }
    public int removeOriginEmployee(int empID, int roleToRemove) {
        return employeesBranchesDAO.removeOriginEmployee(empID, roleToRemove);
    }
    
    public int addForeignEmployee(int empID, int branchID) {
        return employeesBranchesDAO.addForeignEmployee(empID, branchID);
    }
    public int removeForeignEmployee(int empID, int roleToRemove) {
        return employeesBranchesDAO.removeForeignEmployee(empID, roleToRemove);
    }
    
    public int addNotAllowEmployee(int empID, int branchID) {
        return employeesBranchesDAO.addNotAllowEmployee(empID, branchID);
    }
    public int removeNotAllowEmployee(int empID, int roleToRemove) {
        return employeesBranchesDAO.removeNotAllowEmployee(empID, roleToRemove);
    }
}
