package DataAccessLayer.DAO.EmployeesLayer;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;
import Misc.ShiftTime;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ShiftsDAO extends DAO<ShiftDTO> {
    private EmployeesShiftsContraintsDAO employeeShiftContraintDAO;
    private NumEmployeesForRolesDAO numEmployeesForRolesDAO;
    private EmployeesShiftsFinalsDAO employeeShiftFinalDAO;
    private ShiftsCancellationsDAO shiftsCancellationsDAO;
    private DriversInShiftsDAO driversInShiftsDAO;

    public ShiftsDAO() {
        this.tableName = "Shifts";
        employeeShiftContraintDAO = new EmployeesShiftsContraintsDAO();
        numEmployeesForRolesDAO = new NumEmployeesForRolesDAO();
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
            int resES2 = insertToNumEmployeesForRole(Ob);
            int resES3 = insertToShiftsFinals(Ob);
            int resES4 = insertToShiftsCancellations(Ob);
            if (resES1 + resES2 + resES3 + resES4 == 4) // If inserts worked
                ans = 1;
            else {
                ans = 0;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        for (Integer employeeID : Ob.getListConstraintsKeys()) {
            for (int index = 0; index < Ob.getNumberOfEmployeeRolesConstraint(employeeID); index++) {
                String toInsertConstraint = String.format("INSERT INTO %s \n" +
                        "VALUES %s;", "EmployeesShiftsContraints", Ob.getConstraint(employeeID, index));
                Statement s;
                try {
                    s = conn.createStatement();
                    s.executeUpdate(toInsertConstraint);
                } catch (Exception e) {
                    return 0;
                }
            }
        }
        return 1;
    }

    private int insertToNumEmployeesForRole(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (Integer roleID : Ob.getListNumEmployeesForRoleKeys()) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "NumEmployeesForRoles", Ob.getNumEmployeesForRole(roleID));
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
        for (Integer employeeID : Ob.getListFinalShiftKeys()) {
            String toInsertShiftsFinal = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesShiftsFinals", Ob.getFinalShift(employeeID));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsertShiftsFinal);
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
        for (Integer productCode : Ob.getListCancellationsKeys()) {
            for (int index = 0; index < Ob.getNumberOfProductCodeCancellations(productCode); index++) {
                String toInsertCancellation = String.format("INSERT INTO %s \n" +
                        "VALUES %s;", "ShiftsCancellations", Ob.getCancellation(productCode, index));
                Statement s;
                try {
                    s = conn.createStatement();
                    s.executeUpdate(toInsertCancellation);
                } catch (Exception e) {
                    return 0;
                }
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
            LinkedList<Integer> driversInShift = getDriversInShiftList(id, conn);
            if (driversInShift == null) {
                return null;
            }
            output = new ShiftDTO(/* Id */RS.getInt(1), /* super branch */RS.getInt(2),
                    /* date */LocalDate.parse(RS.getString(3)), /* shift time */ShiftTime.valueOf(RS.getString(4)),
                    /* start hour */RS.getInt(5), /* end hour */RS.getInt(6),
                    /* duration */RS.getInt(7), /* is finish setting shift */RS.getBoolean(8),
                    constraints, numEmployeesForRole, finalShift, cancellations, driversInShift);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    public int getMaxShiftIdPlusOne() {
        int output = 0;

        Connection conn = Repository.getInstance().connect();
        String SELECT_SQL = String.format("SELECT MAX(\"%s\") FROM \"%s\"","ShiftID", tableName);
        ResultSet res = null;
        try {
            Statement stmt = conn.createStatement();
            res = stmt.executeQuery(SELECT_SQL);
            output = res.getInt(1);
        } catch (Exception e) {
            output = -1;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output + 1;
    }

    public HashMap<Integer, LinkedList<Integer>> getConstraintsList(Integer id, Connection conn) {
        HashMap<Integer, LinkedList<Integer>> ans = new HashMap<>();
        ResultSet rs = get("EmployeesShiftsContraints", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                ans.putIfAbsent(rs.getInt(2), getRolesList(rs.getInt(2), conn));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
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

    public HashMap<Integer, Integer> getNumEmployeesForRoleList(Integer id, Connection conn) {
        HashMap<Integer, Integer> ans = new HashMap<>();
        ResultSet rs = get("NumEmployeesForRoles", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                ans.putIfAbsent(rs.getInt(2), rs.getInt(3));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public HashMap<Integer, Integer> getFinalShiftList(Integer id, Connection conn) {
        HashMap<Integer, Integer> ans = new HashMap<>();
        ResultSet rs = get("EmployeesShiftsFinals", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                ans.putIfAbsent(rs.getInt(2), rs.getInt(3));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public HashMap<Integer, LinkedList<Integer>> getCancellationsList(Integer id, Connection conn) {
        HashMap<Integer, LinkedList<Integer>> ans = new HashMap<>();
        ResultSet rs = get("ShiftsCancellations", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                ans.putIfAbsent(rs.getInt(2), new LinkedList<>());
                ans.get(rs.getInt(2)).add(rs.getInt(3));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public LinkedList<Integer> getDriversInShiftList(Integer id, Connection conn) {
        LinkedList<Integer> ans = new LinkedList<>();
        ResultSet rs = get("DriversInShifts", "ShiftID", id, conn);
        try {
            while (rs.next()) {
                ans.add(rs.getInt(2));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public ShiftDTO getShiftById(int id) {
        Connection conn = Repository.getInstance().connect();
        ResultSet res = get(tableName, "ShiftID", id, conn);
        ShiftDTO shift = null;
        try {
            if (!res.next()){
                return null;
            }
            shift = makeDTO(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }

        return shift;
    }

    public List<ShiftDTO> getShiftsByDate(LocalDate date) {
        List<ShiftDTO> shifts = getAll("Date", date);
        return shifts;
    }

    public int addConstraint(int empID, int shiftID) {
        return employeeShiftContraintDAO.addConstraint(empID, shiftID);
    }
    public int removeConstraint(int empID, int shiftID) {
        return employeeShiftContraintDAO.removeConstraint(empID, shiftID);
    }

    public int removeAllConstraints(int empID) {
        return employeeShiftContraintDAO.removeAllConstraints(empID);
    }

    public int removeAllFromFinalShift(int empID) {
        return employeeShiftFinalDAO.removeAllFromFinalShift(empID);
    }
    
    public int addNumEmployeeForRole(int shiftID, int roleID, int numberNedded) {
        return numEmployeesForRolesDAO.addNumEmployeeForRole(shiftID, roleID, numberNedded);
    }
    public int removeNumEmployeeForRole(int shiftID, int roleID) {
        return numEmployeesForRolesDAO.removeNumEmployeeForRole(shiftID, roleID);
    }
    
    public int addShiftFinal(int empID, int shiftID) {
        return employeeShiftFinalDAO.addShiftFinal(empID, shiftID);
    }
    public int removeShiftFinal(int empID, int shiftID) {
        return employeeShiftFinalDAO.removeShiftFinal(empID, shiftID);
    }
    
    public int addCancellation(int empID, Integer ProductCode, Integer ProductID) {
        return shiftsCancellationsDAO.addCancellation(empID, ProductCode, ProductID);
    }
    public int removeCancellation(int empID, Integer ProductCode, Integer ProductID) {
        return shiftsCancellationsDAO.removeCancellation(empID, ProductCode, ProductID);
    }
    
    public int addDriverInShift(int shiftID, int driverID) {
        return driversInShiftsDAO.addDriverInShift(shiftID, driverID);
    }
    public int removeDriverInShift(int shiftID, int driverID) {
        return driversInShiftsDAO.removeDriverInShift(shiftID, driverID);
    }
    
}
