package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;
import Misc.ShiftTime;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class ShiftsDAO extends DAO<ShiftDTO> {
    private EmployeesShiftsContraintsDAO employeeShiftContraintDAO;
    private EmployeesShiftsFinalsDAO employeeShiftFinalDAO;
    private ShiftsCancellationsDAO shiftsCancellationsDAO;

    public ShiftsDAO() {
        this.tableName = "Shifts";
        employeeShiftContraintDAO = new EmployeesShiftsContraintsDAO();
        employeeShiftFinalDAO = new EmployeesShiftsFinalsDAO();
        shiftsCancellationsDAO = new ShiftsCancellationsDAO();
    }

    @Override
    public int insert(ShiftDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resES1 = insertToShiftsConstraints(Ob);
            int resES2 = insertToShiftsFinals(Ob);
            int resES3 = insertToShiftsCancellations(Ob);
            if (resES1 + resES2 + resES3 == 3) // If inserts worked
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

    private int insertToShiftsConstraints(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfConstraints(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesShiftsContraints", Ob.getConstraint(index));
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

    private int insertToShiftsFinals(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfFinalShift(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesShiftsFinals", Ob.getFinalShift(index));
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

    private int insertToShiftsCancellations(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfCancellations(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "ShiftsCancellations", Ob.getCancellation(index));
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
    public int update(ShiftDTO updatedOb) { // not allowed to change ID
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null)
            return 0;
        String updateString = String.format("UPDATE %s" +
                " SET \"SuperBranch\"= \"%d\", \"Date\"= \"%s\", \"ShiftTime\"= \"%s\", \"StartHour\"= \"%d\" " +
                ", \"EndHour\"=\"%d\", \"Duration\"=%d,  \"IsFinishSettingShift\"=\"%b\" " +
                "WHERE \"ShiftID\" = \"%d\";",
                tableName, updatedOb.superBranch, updatedOb.date, updatedOb.time, updatedOb.startHour,
                updatedOb.duration, updatedOb.finishSettingShift, updatedOb.idShift);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public ShiftDTO makeDTO(ResultSet RS) {
        ShiftDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            Integer id = RS.getInt(1); // the first column is ID
            HashMap<Integer, LinkedList<Integer>> constraints = getConstraintsList(id, conn);
            if (constraints == null) {
                return null;
            }
            HashMap<Integer, Integer> numEmployeesForRole = getNumEmployeesForRoleList(id, conn);
            if (numEmployeesForRole == null) {
                return null;
            }
            HashMap<Integer, Integer> finalShift = getFinalShiftList(id, conn);
            if (finalShift == null) {
                return null;
            }
            HashMap<Integer, LinkedList<Integer>> cancellations = getCancellationsList(id, conn);
            if (cancellations == null) {
                return null;
            }
            output = new ShiftDTO(/* Id */RS.getInt(1), /* super branch */RS.getInt(2),
                    /* date */LocalDate.parse(RS.getString(3)), /* shift time */ShiftTime.valueOf(RS.getString(4)),
                    /* start hour */RS.getInt(5), /* end hour */RS.getInt(6),
                    /* duration */RS.getInt(7), /* is finish setting shift */RS.getBoolean(8),
                    constraints, numEmployeesForRole, finalShift, cancellations);
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    // TODO - implement
    public HashMap<Integer, LinkedList<Integer>> getConstraintsList(Integer id, Connection conn) {
        HashMap<Integer, LinkedList<Integer>> ans = new HashMap<>();
        ResultSet rs = get("EmployeesShiftsContraints", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                // ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    // TODO - implement
    public HashMap<Integer, Integer> getFinalShiftList(Integer id, Connection conn) {
        HashMap<Integer, Integer> ans = new HashMap<>();
        ResultSet rs = get("EmployeesShiftsFinals", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                // ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    // TODO - implement
    public HashMap<Integer, Integer> getNumEmployeesForRoleList(Integer id, Connection conn) {
        HashMap<Integer, Integer> ans = new HashMap<>();
        ResultSet rs = get("?????", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                // ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    // TODO - implement
    public HashMap<Integer, LinkedList<Integer>> getCancellationsList(Integer id, Connection conn) {
        HashMap<Integer, LinkedList<Integer>> ans = new HashMap<>();
        ResultSet rs = get("ShiftsCancellations", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                // ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public int addConstraint(int empID) {
        return employeeShiftContraintDAO.addConstraint(empID);
    }
    public int removeConstraint(int empID) {
        return employeeShiftContraintDAO.removeConstraint(empID);
    }
    
    public int addShiftFinal(int empID) {
        return employeeShiftFinalDAO.addShiftFinal(empID);
    }
    public int removeShiftFinal(int empID) {
        return employeeShiftFinalDAO.removeShiftFinal(empID);
    }
    
    public int addCancellation(int empID, Integer ProductCode, Integer ProductID) {
        return shiftsCancellationsDAO.addCancellation(empID, ProductCode, ProductID);
    }
    public int removeCancellation(int empID, Integer ProductCode, Integer ProductID) {
        return shiftsCancellationsDAO.removeCancellation(empID, ProductCode, ProductID);
    }
    
}
