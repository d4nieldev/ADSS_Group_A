package DataAccessLayer.DAO.EmployeesLayer;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

import DataAccessLayer.Repository;
import DataAccessLayer.DAO.DAO;
import DataAccessLayer.DTO.EmployeeLayer.*;
import Misc.License;

public class DriversDAO extends DAO<DriverDTO> {
    private DriversAvailableShiftDatesDAO driversAvailableShiftDatesDAO;
    private DriversWorkedDatesDAO driversWorkedDatesDAO;
    
    public DriversDAO() {
        this.tableName = "Drivers";
        driversAvailableShiftDatesDAO = new DriversAvailableShiftDatesDAO();
        driversWorkedDatesDAO = new DriversWorkedDatesDAO();
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
            int resES1 = insertToEmployeeRoles(Ob);
            int resES2 = insertToAvailableShiftDates(Ob);
            int resES3 = insertToWorkedDates(Ob);
            if (resES1 + resES2 + resES3 == 3) //If inserts worked
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

    private int insertToAvailableShiftDates(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfAvailableShiftDates(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "DriversAvailableShiftDates", Ob.getAvailableShiftDates(index));
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

    private int insertToWorkedDates(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null)
            return 0;
        for (int index = 0; index < Ob.getNumberOfWorkedDates(); index++) {
            String toInsertEmployeeRole = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "DriversWorkedDates", Ob.getWorkedDates(index));
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
    public int update(DriverDTO updatedOb) { // not allowed to change ID
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null)
            return 0;
        String updateString = String.format("UPDATE %s" +
                " SET \"FirstName\"= \"%s\", \"LastName\"= \"%s\", \"Password\"= \"%s\", \"BankNumber\"= \"%d\" " +
                ", \"BankBranchNumber\"=\"%d\", \"BankAccountNumber\"=%d,  \"Salary\"=\"%d\", \"Bonus\"=\"%d\" " +
                ", \"startDate\"=\"%s\", \"TempsEmployment\"=%s,  \"IsLoggedIn\"=\"%b\", \"SuperBranch\"=\"%d\" " +
                ", \"DriverLicense\"=\"%s\" WHERE \"ID\" = \"%d\";",
                tableName, updatedOb.firstName, updatedOb.lastName, updatedOb.password, updatedOb.bankNum,
                updatedOb.bankBranch, updatedOb.bankAccount,
                updatedOb.salary, updatedOb.bonus, updatedOb.startDate, updatedOb.tempsEmployment, updatedOb.isLoggedIn,
                updatedOb.superBranch, updatedOb.driverLicense, updatedOb.id);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public DriverDTO makeDTO(ResultSet RS) {
        DriverDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            Integer id = RS.getInt(1); // the first column is ID
            LinkedList<Integer> roles = getRolesList(id, conn);
            if (roles == null) {
                return null;
            }
            LinkedList<LocalDate> availableShiftDates = getAvailableShiftDatesList(id, conn);
            if (availableShiftDates == null) {
                return null;
            }
            LinkedList<LocalDate> workedDates = getWorkedDatesList(id, conn);
            if (workedDates == null) {
                return null;
            }
            output = new DriverDTO(/* Id */RS.getInt(1), /* first name */RS.getString(2),
                    /* last name */RS.getString(3), /* password */RS.getString(4),
                    /* bank number */RS.getInt(5), /* bank branch number */RS.getInt(6),
                    /* bank account number */RS.getInt(7), /* salary */RS.getInt(8),
                    /* bonus */ RS.getInt(9), /* start date */ LocalDate.parse(RS.getString(10)),
                    /* temps employment */ RS.getString(11), roles,
                    /* is logged in */ RS.getBoolean(12), /* super branch */ RS.getInt(13),
                    /* driver license */  License.valueOf(RS.getString(14)), availableShiftDates, workedDates);
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
    
    public LinkedList<LocalDate> getAvailableShiftDatesList(Integer id, Connection conn) {
        LinkedList<LocalDate> ans = new LinkedList<>();
        ResultSet rs = get("DriversAvailableShiftDates", "DriverID", id, conn);
        try {
            while (rs.next()) {
                ans.add(LocalDate.parse(rs.getString(2)));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }
    
    public LinkedList<LocalDate> getWorkedDatesList(Integer id, Connection conn) {
        LinkedList<LocalDate> ans = new LinkedList<>();
        ResultSet rs = get("DriversWorkedDates", "DriverID", id, conn);
        try {
            while (rs.next()) {
                ans.add(LocalDate.parse(rs.getString(2)));
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    public DriverDTO getDriverById(int id) {
        Connection conn = Repository.getInstance().connect();
        ResultSet res = get(tableName, "ID", id, conn);
        DriverDTO driver = null;

        try {
            if (!res.next()){
                return null;
            }
            driver = makeDTO(res);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }

        return driver;
    }
    
    public int addAvailableShiftDates(int empID, LocalDate dateToAdd) {
        return driversAvailableShiftDatesDAO.addAvailableShiftDates(empID, dateToAdd);
    }
    public int removeAvailableShiftDates(int empID, LocalDate dateToRemove) {
        return driversAvailableShiftDatesDAO.removeAvailableShiftDates(empID, dateToRemove);
    }

    public int addWorkedDates(int empID, LocalDate dateToAdd) {
        return driversWorkedDatesDAO.addWorkedDates(empID, dateToAdd);
    }
    public int removeWorkedDates(int empID, LocalDate dateToRemove) {
        return driversWorkedDatesDAO.removeWorkedDates(empID, dateToRemove);
    }

}
