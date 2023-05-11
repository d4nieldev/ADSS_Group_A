package DataAccessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class Repository {

    private static Repository instance = null;

    private Repository() {
        createTables();
    }

    // instance of the class - singletone
    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    // connect to the DATABASE
    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:database.db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = connect();
        PreparedStatement stmt = conn.prepareStatement(query);
        int i = 1;
        for (Object param : params) {
            if (param instanceof String) {
                stmt.setString(i, (String) param);
            } else if (param instanceof Integer) {
                stmt.setInt(i, (Integer) param);
            } else if (param instanceof Double) {
                stmt.setDouble(i, (Double) param);
            } else if (param instanceof LocalDate) {
                stmt.setString(i, param.toString());
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + param.getClass().getName());
            }
            i++;
        }

        ResultSet rs = stmt.executeQuery();

        stmt.close();
        conn.close();

        return rs;
    }

    // disconnect from the DATABASE
    public void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // public List<Integer> getIds(String query){}

    private void createTables() {

        // --------------------------------------------------------------------------------------------
        // Employees Layer Tables
        String EmployeesTable = "CREATE TABLE IF NOT EXISTS \"Employees\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"FirstName\"\tTEXT,\n" +
                "\t\"LastName\"\tTEXT,\n" +
                "\t\"Password\"\tTEXT,\n" +
                "\t\"BankNumber\"\tINTEGER,\n" +
                "\t\"BankBranchNumber\"\tINTEGER,\n" +
                "\t\"BankAccountNumber\"\tINTEGER,\n" +
                "\t\"Salary\"\tINTEGER,\n" +
                "\t\"Bonus\"\tINTEGER,\n" +
                "\t\"startDate\"\tDateTime,\n" +
                "\t\"TempsEmployment\"\tTEXT,\n" +
                "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" + ");";
        String DriversTable = "CREATE TABLE IF NOT EXISTS \"Drivers\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"FirstName\"\tTEXT,\n" +
                "\t\"LastName\"\tTEXT,\n" +
                "\t\"Password\"\tTEXT,\n" +
                "\t\"BankNumber\"\tINTEGER,\n" +
                "\t\"BankBranchNumber\"\tINTEGER,\n" +
                "\t\"BankAccountNumber\"\tINTEGER,\n" +
                "\t\"Salary\"\tINTEGER,\n" +
                "\t\"Bonus\"\tINTEGER,\n" +
                "\t\"startDate\"\tDateTime,\n" +
                "\t\"TempsEmployment\"\tTEXT,\n" +
                "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\t\"DriverLicense\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"ShiftID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\t\"Date\"\tDateTime,\n" +
                "\t\"ShiftTime\"\tTEXT,\n" +
                "\t\"StartHour\"\tINTEGER,\n" +
                "\t\"EndHour\"\tINTEGER,\n" +
                "\t\"Duration\"\tINTEGER,\n" +
                "\t\"IsFinishSettingShift\"\tBOOLEAN,\n" +
                "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String BranchesTable = "CREATE TABLE IF NOT EXISTS \"Branches\" (\n" +
                "\t\"BranchID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"Address\"\tTEXT,\n" +
                "\t\"Location\"\tTEXT\n" +
                ");";
        String RolesTable = "CREATE TABLE IF NOT EXISTS \"Roles\" (\n" +
                "\t\"RoleID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"RoleName\"\tTEXT\n" +
                ");";
        String EmployeesRolesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesRoles\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"RoleID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"RoleID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +

                "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE CASCADE\n" +
                ");";
        String DriversAvailableShiftDatesTable = "CREATE TABLE IF NOT EXISTS \"DriversAvailableShiftDates\" (\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"Date\"\tDateTime,\n" +
                "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String DriversWorkedDatesTable = "CREATE TABLE IF NOT EXISTS \"DriversWorkedDates\" (\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"Date\"\tDateTime,\n" +
                "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String EmployeesShiftsConstraintsTable = "CREATE TABLE IF NOT EXISTS \"EmployeesShiftsContraints\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +

                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE\n" +
                ");";
        String EmployeesShiftsFinalsTable = "CREATE TABLE IF NOT EXISTS \"EmployeesShiftsFinals\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE\n" +
                ");";
        String EmployeesBranchesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesBranches\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"BranchID\"\tINTEGER,\n" +
                "\t\"Status\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"BranchID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"BranchID\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String ShiftsCancellationsTable = "CREATE TABLE IF NOT EXISTS \"ShiftsCancellations\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"ProductCode\"\tINTEGER,\n" +
                "\t\"ProductID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ShiftID\",\"ProductCode\",\"ProductID\"),\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE\n" +
                // "\tFOREIGN KEY(\"ProductCode\") REFERENCES \"???\"(\"???\") ON DELETE
                // CASCADE\n" +
                // "\tFOREIGN KEY(\"ProductID\") REFERENCES \"???\"(\"???\") ON DELETE
                // CASCADE\n" +
                ");";

        // --------------------------------------------------------------------------------------------
        // Suppliers and Inventory Layer Tables
        StringBuilder suppliersInventoryDDL = new StringBuilder();
        try {
            File f = new File("./superli_ddl.txt");
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine())
                suppliersInventoryDDL.append(scanner.nextLine()).append("\n");
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Expected to find a ddl file for suppliers and inventory called 'superli_ddl.txt'.");
            e.printStackTrace();
        }
        Connection conn = connect();
        if (conn == null)
            return;

        try {
            Statement stmt = conn.createStatement();

            // Employees Layer tables ------------------------------------------
            stmt.execute(EmployeesTable);
            stmt.execute(DriversTable);
            stmt.execute(ShiftsTable);
            stmt.execute(BranchesTable);
            stmt.execute(RolesTable);
            stmt.execute(DriversAvailableShiftDatesTable);
            stmt.execute(DriversWorkedDatesTable);
            stmt.execute(EmployeesRolesTable);
            stmt.execute(EmployeesShiftsConstraintsTable);
            stmt.execute(EmployeesShiftsFinalsTable);
            stmt.execute(EmployeesBranchesTable);
            stmt.execute(ShiftsCancellationsTable);

            for (String table : suppliersInventoryDDL.toString().split(";"))
                stmt.execute(table + ";");

            // Transports Layer tables ------------------------------------------

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}