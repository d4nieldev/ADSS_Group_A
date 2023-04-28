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
                "\t\"FirstName\"\tTEXT,\n" +
                "\t\"LastName\"\tTEXT,\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"Password\"\tTEXT,\n" +
                "\t\"BankNumber\"\tINTEGER,\n" +
                "\t\"BankBranchNumber\"\tINTEGER,\n" +
                "\t\"BankAccountNumber\"\tINTEGER,\n" +
                "\t\"Salary\"\tINTEGER,\n" +
                "\t\"Bonus\"\tINTEGER,\n" +
                "\t\"startDate\"\tDATE,\n" +
                "\t\"TempsEmployment\"\tTEXT,\n" +
                "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
                "\t\"SuperBranch\"\tINTEGER\n" +
                ");";
        String EmployeesRolesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesRoles\" (\n" +
                "\t\"EmployeeID\"\tINTEGER,\n" +
                "\t\"RoleID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"RoleID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE CASCADE\n" +
                ");";
        String RolesTable = "CREATE TABLE IF NOT EXISTS \"Roles\" (\n" +
                "\t\"RoleID\"\tINTEGER PRIMARY KEY,\n" +
                "\t\"RoleName\"\tTEXT,\n" +
                ");";


        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();

            //Employees Layer tables ------------------------------------------
            stmt.execute(EmployeesTable);
            stmt.execute(EmployeesRolesTable);
            stmt.execute(RolesTable);

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}
