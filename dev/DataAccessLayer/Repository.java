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
                "\t\"startDate\"\tTEXT,\n" +
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
                "\t\"startDate\"\tTEXT,\n" +
                "\t\"TempsEmployment\"\tTEXT,\n" +
                "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\t\"DriverLicense\"\tTEXT,\n" + 
                "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"ShiftID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"SuperBranch\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT,\n" +
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
        String EmployeesBranchesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesBranches\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"BranchID\"\tINTEGER,\n" +
                "\t\"Status\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"BranchID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"BranchID\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE CASCADE\n" +
                ");";
        String DriversAvailableShiftDatesTable = "CREATE TABLE IF NOT EXISTS \"DriversAvailableShiftDates\" (\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" + 
                ");";
        String DriversWorkedDatesTable = "CREATE TABLE IF NOT EXISTS \"DriversWorkedDates\" (\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" + 
                ");";
        String EmployeesShiftsConstraintsTable = "CREATE TABLE IF NOT EXISTS \"EmployeesShiftsContraints\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +                           
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE\n" +
                ");";
        String NumEmployeesForRolesTable = "CREATE TABLE IF NOT EXISTS \"NumEmployeesForRoles\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"RoleID\"\tINTEGER,\n" +
                "\t\"NumberNeeded\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ShiftID\",\"RoleID\"),\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE CASCADE\n" + 
                ");";
        String EmployeesShiftsFinalsTable = "CREATE TABLE IF NOT EXISTS \"EmployeesShiftsFinals\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"EmployeeRole\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"EmployeeRole\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE CASCADE\n" +
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
        String DriversInShiftsTable = "CREATE TABLE IF NOT EXISTS \"DriversInShifts\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ShiftID\",\"DriverID\"),\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE CASCADE,\n" +                                                               
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
                
        // --------------------------------------------------------------------------------------------
        // Transports Layer Tables
        String TransportsDriversTable = "CREATE TABLE IF NOT EXIST \"TransportsDrivers\" (\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"TransportID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"DriverID\",\"TransportID\"),\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"TransportID\") REFERENCES \"Transport\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String TrucksTable = "CREATE TABLE IF NOT EXISTS \"Trucks\" (\n" +
                "\t\"PlateNumber\"\tTEXT PRIMARY KEY,\n" +
                "\t\"Model\"\tTEXT,\n" +
                "\t\"WeightNeto\"\tINTEGER,\n" +
                "\t\"WeightMax\"\tINTEGER,\n" +
                "\t\"IsAvailable\"\tBOOLEAN\n" +
                ");";
        String DestinationsTable = "CREATE TABLE IF NOT EXISTS \"Destinations\" (\n" +
                "\t\"Address\"\tTEXT PRIMARY KEY,\n" +
                "\t\"PhoneNumber\"\tTEXT,\n" +
                "\t\"ContactName\"\tTEXT,\n" +
                "\t\"location\"\tTEXT,\n" +
                "\t\"destinationType\"\tTEXT\n" +
                ");";
        String DeliveriesTable = "CREATE TABLE IF NOT EXISTS \"Deliveries\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"Source\"\tTEXT,\n" +
                "\t\"Destination\"\tTEXT,\n" +
                "\t\"Status\"\tTEXT,\n" +
                "\t\"Weight\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"Source\") REFERENCES \"Destinations\"(\"Address\") ON DELETE CASCADE,\n" +   
                "\tFOREIGN KEY(\"Destination\") REFERENCES \"Destinations\"(\"Address\") ON DELETE CASCADE\n" +   
                ");";
        String TransportsTable = "CREATE TABLE IF NOT EXISTS \"Transports\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\t\"LeavingTime\"\tTEXT,\n" +
                "\t\"TruckNumber\"\tTEXT,\n" +
                "\t\"DriverName\"\tTEXT,\n" +
                "\t\"DriverID\"\tINTEGER,\n" +
                "\t\"Source\"\tTEXT,\n" +
                "\t\"TruckWeightNeto\"\tINTEGER,\n" +
                "\t\"TruckWeightMax\"\tINTEGER,\n" +
                "\t\"CurrentWeight\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE,\n" +   
                "\tFOREIGN KEY(\"TruckNumber\") REFERENCES \"Trucks\"(\"PlateNumber\") ON DELETE CASCADE\n" +   
                ");";


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
            stmt.execute(NumEmployeesForRolesTable);
            stmt.execute(EmployeesShiftsFinalsTable);
            stmt.execute(EmployeesBranchesTable);
            stmt.execute(ShiftsCancellationsTable);
            stmt.execute(DriversInShiftsTable);

            // Transports Layer tables ------------------------------------------
            stmt.execute(TransportsDriversTable);
            stmt.execute(TrucksTable);
            stmt.execute(DestinationsTable);
            stmt.execute(DeliveriesTable);
            stmt.execute(TransportsTable);

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}
