package DataAccessLayer;

import java.sql.*;

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
            String url = "jdbc:sqlite:database.db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();}
        return conn;
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

    //     public List<Integer> getIds(String query){}

    private void createTables() {

        //Employees Layer Tables --------------------------------------------------------------------------------------------

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
                "\t\"SuperBranch\"\tINTEGER\n" +
                ");";
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
                "\t\"SuperBranch\"\tINTEGER\n" +
                "\t\"DriverLicense\"\tTEXT\n" +
                ");";
        String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"ShiftID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\t\"Date\"\tDateTime,\n" +
                "\t\"ShiftTine\"\tTEXT,\n" +
                "\t\"StartHour\"\tINTEGER,\n" +
                "\t\"EndHour\"\tINTEGER,\n" +
                "\t\"Duration\"\tINTEGER,\n" +
                "\t\"IsFinishSettingShift\"\tBOOLEAN,\n" +
                "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String BranchesTable = "CREATE TABLE IF NOT EXISTS \"Branches\" (\n" +
                "\t\"BranchID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"Address\"\tTEXT,\n" +
                "\t\"Location\"\tTEXT,\n" +
                ");";
        String RolesTable = "CREATE TABLE IF NOT EXISTS \"Roles\" (\n" +
                "\t\"RoleID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"RoleName\"\tTEXT,\n" +
                ");";
        String EmployeesRolesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesRoles\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"RoleID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"RoleID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE CASCADE\n" +
                ");";
        String EmployeesShiftsConstraintsTable = "CREATE TABLE IF NOT EXISTS \"EmployeesShiftsContraints\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
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

        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();

            //Employees Layer tables ------------------------------------------
            stmt.execute(EmployeesTable);
            stmt.execute(DriversTable);
            stmt.execute(ShiftsTable);
            stmt.execute(BranchesTable);
            stmt.execute(RolesTable);
            stmt.execute(EmployeesRolesTable);
            stmt.execute(EmployeesShiftsConstraintsTable);
            stmt.execute(EmployeesShiftsFinalsTable);
            stmt.execute(EmployeesBranchesTable);

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}
