package DataAccessLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        // //
        // --------------------------------------------------------------------------------------------
        // // Employees Layer Tables
        // String EmployeesTable = "CREATE TABLE IF NOT EXISTS \"Employees\" (\n" +
        // "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
        // "\t\"FirstName\"\tTEXT,\n" +
        // "\t\"LastName\"\tTEXT,\n" +
        // "\t\"Password\"\tTEXT,\n" +
        // "\t\"BankNumber\"\tINTEGER,\n" +
        // "\t\"BankBranchNumber\"\tINTEGER,\n" +
        // "\t\"BankAccountNumber\"\tINTEGER,\n" +
        // "\t\"Salary\"\tINTEGER,\n" +
        // "\t\"Bonus\"\tINTEGER,\n" +
        // "\t\"startDate\"\tDateTime,\n" +
        // "\t\"TempsEmployment\"\tTEXT,\n" +
        // "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
        // "\t\"SuperBranch\"\tINTEGER,\n" +
        // "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON
        // DELETE CASCADE\n" + ");";
        // String DriversTable = "CREATE TABLE IF NOT EXISTS \"Drivers\" (\n" +
        // "\t\"ID\"\tINTEGER PRIMARY KEY,\n" +
        // "\t\"FirstName\"\tTEXT,\n" +
        // "\t\"LastName\"\tTEXT,\n" +
        // "\t\"Password\"\tTEXT,\n" +
        // "\t\"BankNumber\"\tINTEGER,\n" +
        // "\t\"BankBranchNumber\"\tINTEGER,\n" +
        // "\t\"BankAccountNumber\"\tINTEGER,\n" +
        // "\t\"Salary\"\tINTEGER,\n" +
        // "\t\"Bonus\"\tINTEGER,\n" +
        // "\t\"startDate\"\tDateTime,\n" +
        // "\t\"TempsEmployment\"\tTEXT,\n" +
        // "\t\"IsLoggedIn\"\tBOOLEAN,\n" +
        // "\t\"SuperBranch\"\tINTEGER,\n" +
        // "\t\"DriverLicense\"\tTEXT,\n" +
        // "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON
        // DELETE CASCADE\n" +
        // ");";
        // String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
        // "\t\"ShiftID\"\tINTEGER PRIMARY KEY,\n" +
        // "\t\"SuperBranch\"\tINTEGER,\n" +
        // "\t\"Date\"\tDateTime,\n" +
        // "\t\"ShiftTime\"\tTEXT,\n" +
        // "\t\"StartHour\"\tINTEGER,\n" +
        // "\t\"EndHour\"\tINTEGER,\n" +
        // "\t\"Duration\"\tINTEGER,\n" +
        // "\t\"IsFinishSettingShift\"\tBOOLEAN,\n" +
        // "\tFOREIGN KEY(\"SuperBranch\") REFERENCES \"Branches\"(\"BranchID\") ON
        // DELETE CASCADE\n" +
        // ");";
        // String BranchesTable = "CREATE TABLE IF NOT EXISTS \"Branches\" (\n" +
        // "\t\"BranchID\"\tINTEGER PRIMARY KEY,\n" +
        // "\t\"Address\"\tTEXT,\n" +
        // "\t\"Location\"\tTEXT\n" +
        // ");";
        // String RolesTable = "CREATE TABLE IF NOT EXISTS \"Roles\" (\n" +
        // "\t\"RoleID\"\tINTEGER PRIMARY KEY,\n" +
        // "\t\"RoleName\"\tTEXT\n" +
        // ");";
        // String EmployeesRolesTable = "CREATE TABLE IF NOT EXISTS \"EmployeesRoles\"
        // (\n" +
        // "\t\"EmployeeID\"\tINTEGER,\n" +
        // "\t\"RoleID\"\tINTEGER,\n" +
        // "\tPRIMARY KEY(\"EmployeeID\",\"RoleID\"),\n" +
        // "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE
        // CASCADE,\n" +

        // "\tFOREIGN KEY(\"RoleID\") REFERENCES \"Roles\"(\"RoleID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String DriversAvailableShiftDatesTable = "CREATE TABLE IF NOT EXISTS
        // \"DriversAvailableShiftDates\" (\n" +
        // "\t\"DriverID\"\tINTEGER,\n" +
        // "\t\"Date\"\tDateTime,\n" +
        // "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
        // "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String DriversWorkedDatesTable = "CREATE TABLE IF NOT EXISTS
        // \"DriversWorkedDates\" (\n" +
        // "\t\"DriverID\"\tINTEGER,\n" +
        // "\t\"Date\"\tDateTime,\n" +
        // "\tPRIMARY KEY(\"DriverID\",\"Date\"),\n" +
        // "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String EmployeesShiftsConstraintsTable = "CREATE TABLE IF NOT EXISTS
        // \"EmployeesShiftsContraints\" (\n" +
        // "\t\"EmployeeID\"\tINTEGER,\n" +
        // "\t\"ShiftID\"\tINTEGER,\n" +
        // "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
        // "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE
        // CASCADE,\n" +

        // "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String EmployeesShiftsFinalsTable = "CREATE TABLE IF NOT EXISTS
        // \"EmployeesShiftsFinals\" (\n" +
        // "\t\"EmployeeID\"\tINTEGER,\n" +
        // "\t\"ShiftID\"\tINTEGER,\n" +
        // "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
        // "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE
        // CASCADE\n" +
        // "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String EmployeesBranchesTable = "CREATE TABLE IF NOT EXISTS
        // \"EmployeesBranches\" (\n" +
        // "\t\"EmployeeID\"\tINTEGER,\n" +
        // "\t\"BranchID\"\tINTEGER,\n" +
        // "\t\"Status\"\tTEXT,\n" +
        // "\tPRIMARY KEY(\"EmployeeID\",\"BranchID\"),\n" +
        // "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE
        // CASCADE\n" +
        // "\tFOREIGN KEY(\"BranchID\") REFERENCES \"Branches\"(\"BranchID\") ON DELETE
        // CASCADE\n" +
        // ");";
        // String ShiftsCancellationsTable = "CREATE TABLE IF NOT EXISTS
        // \"ShiftsCancellations\" (\n" +
        // "\t\"ShiftID\"\tINTEGER,\n" +
        // "\t\"ProductCode\"\tINTEGER,\n" +
        // "\t\"ProductID\"\tINTEGER,\n" +
        // "\tPRIMARY KEY(\"ShiftID\",\"ProductCode\",\"ProductID\"),\n" +
        // "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ShiftID\") ON DELETE
        // CASCADE\n" +
        // // "\tFOREIGN KEY(\"ProductCode\") REFERENCES \"???\"(\"???\") ON DELETE
        // // CASCADE\n" +
        // // "\tFOREIGN KEY(\"ProductID\") REFERENCES \"???\"(\"???\") ON DELETE
        // // CASCADE\n" +
        // ");";

        // --------------------------------------------------------------------------------------------
        // Suppliers and Inventory Layer Tables
        String suppliersInventoryDDL = """
                ---------------- Discounts ----------------------

                CREATE TABLE IF NOT EXISTS Discounts (
                    id        INTEGER NOT NULL,
                    startDate TEXT,
                    endDate   TEXT,
                    val       REAL    NOT NULL,
                    dType     TEXT    NOT NULL,

                    CHECK(dType IN ('Fixed', 'Precentage')),

                    PRIMARY KEY (id)
                );

                ---------------- Suppliers ----------------------

                CREATE TABLE IF NOT EXISTS Suppliers (
                    id               INTEGER NOT NULL,
                    name             TEXT    NOT NULL,
                    bankAccount      TEXT    NOT NULL,
                    paymentCondition TEXT    NOT NULL,

                    CHECK(paymentCondition IN ('net 30 EOM', 'net 60 EOM')),

                    PRIMARY KEY (id)
                );

                CREATE TABLE IF NOT EXISTS Contacts (
                    supplierId INTEGER NOT NULL,
                    phone      TEXT    NOT NULL,
                    name       TEXT    NOT NULL,

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, phone)
                );

                CREATE TABLE IF NOT EXISTS SuppliersFields (
                    supplierId INTEGER NOT NULL,
                    fieldName  TEXT    NOT NULL,

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    FOREIGN KEY (fieldName)  REFERENCES Fields(name)  ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, fieldName)
                );

                CREATE TABLE IF NOT EXISTS SupplierAmountToDiscount (
                    supplierId INTEGER NOT NULL,
                    amount     INTEGER NOT NULL,
                    discount   INTEGER NOT NULL UNIQUE,

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    FOREIGN KEY (discount)   REFERENCES Discounts(id) ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, amount)
                );

                CREATE TABLE IF NOT EXISTS FixedDaysSuppliers (
                    supplierId  INTEGER NOT NULL,
                    dayOfSupply INTEGER NOT NULL,

                    CHECK(dayOfSupply BETWEEN 1 AND 7),

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, dayOfSupply)
                );

                CREATE TABLE IF NOT EXISTS OnOrderSuppliers (
                    supplierId    INTEGER NOT NULL,
                    maxSupplyDays INTEGER NOT NULL,

                    CHECK (maxSupplyDays >= 0),

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    PRIMARY KEY (supplierId)
                );

                CREATE TABLE IF NOT EXISTS SelfPickupSuppliers (
                    supplierId         INTEGER NOT NULL,
                    maxPreperationDays INTEGER NOT NULL,
                    address            TEXT    NOT NULL,

                    CHECK (maxPreperationDays >= 0),

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    PRIMARY KEY (supplierId)
                );

                ------------------ Branches ------------------------

                CREATE TABLE IF NOT EXISTS Branches (
                    id   INTEGER NOT NULL,
                    name TEXT    NOT NULL,

                    PRIMARY KEY (id)
                );

                ------------------ Products ------------------------

                CREATE TABLE IF NOT EXISTS Categories (
                    id     INTEGER NOT NULL,
                    name   TEXT    NOT NULL,
                    parent INTEGER,

                    FOREIGN KEY (parent) REFERENCES Categories(id) ON DELETE CASCADE,
                    PRIMARY KEY (id)
                );

                CREATE TABLE IF NOT EXISTS Products (
                    id           INTEGER NOT NULL,
                    name         TEXT    NOT NULL,
                    manufacturer TEXT    NOT NULL,
                    categoryId   INTEGER NOT NULL,

                    FOREIGN KEY (categoryId) REFERENCES Categories(id) ON DELETE SET NULL,
                    PRIMARY KEY (id)
                );

                CREATE TABLE IF NOT EXISTS ProductBranch (
                    productId   INTEGER NOT NULL,
                    branchId    INTEGER NOT NULL,
                    price       REAL    NOT NULL,
                    minQuantity INTEGER NOT NULL,

                    CHECK (minQuantity > 0 AND price >= 0),

                    FOREIGN KEY (productId) REFERENCES Products(id) ON DELETE CASCADE,
                    FOREIGN KEY (branchId)  REFERENCES Branches(id)  ON DELETE CASCADE,
                    PRIMARY KEY(branchId, productId)
                );

                CREATE TABLE IF NOT EXISTS ProductBranchDiscounts (
                    productId  INTEGER NOT NULL,
                    branchId   INTEGER NOT NULL,
                    discountId INTEGER NOT NULL,

                    FOREIGN KEY (productId, branchId) REFERENCES ProductBranch(productId, branchId) ON DELETE CASCADE,
                    FOREIGN KEY (discountId)          REFERENCES Discounts(id)                      ON DELETE SET NULL,
                    PRIMARY KEY (discountId)
                );

                CREATE TABLE IF NOT EXISTS SpecificProduct (
                    specificId INTEGER NOT NULL,
                    generalId  INTEGER NOT NULL,
                    branchId   INTEGER NOT NULL,
                    buyPrice   REAL    NOT NULL,
                    sellPrice  REAL,
                    status     TEXT,
                    flaw       TEXT,
                    expDate    TEXT    NOT NULL,

                    CHECK (buyPrice >= 0 AND sellPrice >= 0 AND status IN ('SOLD', 'ON_SHELF', 'ON_STORAGE')),

                    FOREIGN KEY (generalId, branchId) REFERENCES ProductBranch(productId, branchId) ON DELETE SET NULL,
                    PRIMARY KEY (specificId)
                );

                ---------------- Agreements ----------------------

                CREATE TABLE IF NOT EXISTS ProductAgreement (
                    supplierId        INTEGER NOT NULL,
                    productId         INTEGER NOT NULL,
                    stockAmount       INTEGER NOT NULL,
                    basePrice         REAL NOT NULL,
                    productSupplierId INTEGER NOT NULL, -- the catalogical number of the product in the supplier system

                    CHECK(stockAmount >= 0 AND basePrice >= 0),

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    FOREIGN KEY (productId)  REFERENCES Products(id)  ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, productId)
                );

                CREATE TABLE IF NOT EXISTS AgreementAmountToDiscount (
                    supplierId INTEGER NOT NULL,
                    productId  INTEGER NOT NULL,
                    amount     INTEGER NOT NULL,
                    discount   INTEGER NOT NULL,

                    CHECK (amount > 0),

                    FOREIGN KEY (supplierId, productId) REFERENCES ProductAgreement(supplierId, productId) ON DELETE CASCADE,
                    FOREIGN KEY (discount)              REFERENCES Discounts(id),
                    PRIMARY KEY (discount)

                );

                ---------------- Reservations ----------------------

                CREATE TABLE IF NOT EXISTS Reservations (
                    id                INTEGER NOT NULL,
                    supplierId        INTEGER,
                    rDate             TEXT    NOT NULL,
                    status            TEXT    NOT NULL,
                    destinationBranch INTEGER,
                    contactPhone      INTEGER,

                    CHECK(status IN ('NotReady', 'Ready', 'Closed', 'Aborted')),

                    FOREIGN KEY (supplierId)               REFERENCES Suppliers(id)               ON DELETE SET NULL,
                    FOREIGN KEY (destinationBranch)        REFERENCES Branches(id)                  ON DELETE SET NULL,
                    FOREIGN KEY (supplierId, contactPhone) REFERENCES Contacts(supplierId, phone) ON DELETE SET NULL,
                    PRIMARY KEY (id, supplierId)
                );

                CREATE TABLE IF NOT EXISTS ReceiptItem (
                    reservationId              INTEGER NOT NULL,
                    productId                  INTEGER,
                    amount                     INTEGER NOT NULL,
                    pricePerUnitBeforeDiscount REAL    NOT NULL,
                    pricePerUnitAfterDiscount  REAL    NOT NULL,

                    CHECK(amount > 0),

                    FOREIGN KEY (reservationId) REFERENCES Reservations(id) ON DELETE CASCADE,
                    FOREIGN KEY (productId)     REFERENCES Products(id)     ON DELETE SET NULL,
                    PRIMARY KEY (reservationId, productId)
                );

                CREATE TABLE IF NOT EXISTS PeriodicReservation (
                    supplierId INTEGER NOT NULL,
                    branchId   INTEGER NOT NULL,
                    dayOfOrder INTEGER NOT NULL,

                    CHECK(dayOfOrder BETWEEN 1 AND 7),

                    FOREIGN KEY (supplierId) REFERENCES Suppliers(id) ON DELETE CASCADE,
                    FOREIGN KEY (branchId)   REFERENCES Branches(id)    ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, branchId)
                );

                CREATE TABLE IF NOT EXISTS PeriodicReservationItem (
                    supplierId INTEGER NOT NULL,
                    branchId   INTEGER NOT NULL,
                    productId  INTEGER NOT NULL,
                    amount     INTEGER NOT NULL,

                    CHECK (amount > 0),

                    FOREIGN KEY (supplierId, branchId) REFERENCES PeriodicReservation(supplierId, branchId) ON DELETE CASCADE,
                    FOREIGN KEY (productId, branchId)  REFERENCES ProductBranch(productId, branchId)        ON DELETE CASCADE,
                    PRIMARY KEY (supplierId, branchId, productId)
                );

                ---------------- Reports ----------------------

                CREATE TABLE IF NOT EXISTS Reports (
                    id          INTEGER NOT NULL,
                    branchId    INTEGER,
                    createdDate TEXT NOT NULL,

                    FOREIGN KEY (branchId) REFERENCES Branches(id),
                    PRIMARY KEY (id)
                );


                CREATE TABLE IF NOT EXISTS InventoryReportEntries (
                    reportId      INTEGER NOT NULL,
                    productId     INTEGER NOT NULL,
                    shelfAmount   INTEGER NOT NULL,
                    storageAmount INTEGER NOT NULL,

                    CHECK (shelfAmount >= 0 AND storageAmount >= 0),

                    FOREIGN KEY (reportId)  REFERENCES Reports(id)  ON DELETE CASCADE,
                    FOREIGN KEY (productId) REFERENCES Products(id) ON DELETE SET NULL,
                    PRIMARY KEY (reportId, productId)
                );

                CREATE TABLE IF NOT EXISTS DeficiencyReportEntries (
                    reportId      INTEGER NOT NULL,
                    productId     INTEGER NOT NULL,
                    missingAmount INTEGER NOT NULL,

                    CHECK (missingAmount >=0),

                    FOREIGN KEY (reportId)  REFERENCES Reports(id)  ON DELETE CASCADE,
                    FOREIGN KEY (productId) REFERENCES Products(id) ON DELETE SET NULL,
                    PRIMARY KEY (reportId, productId)
                );


                CREATE TABLE IF NOT EXISTS ExpiredAndFlawReportEntries (
                    reportId   INTEGER NOT NULL,
                    specificId INTEGER NOT NULL,

                    FOREIGN KEY (reportId)   REFERENCES Reports(id)                 ON DELETE CASCADE,
                    FOREIGN KEY (specificId) REFERENCES SpecificProduct(specificId) ON DELETE SET NULL,
                    PRIMARY KEY (reportId, specificId)
                )

                    """;
        Connection conn = connect();
        if (conn == null)
            return;

        try {
            Statement stmt = conn.createStatement();

            // Employees Layer tables ------------------------------------------
            // stmt.execute(EmployeesTable);
            // stmt.execute(DriversTable);
            // stmt.execute(ShiftsTable);
            // stmt.execute(BranchesTable);
            // stmt.execute(RolesTable);
            // stmt.execute(DriversAvailableShiftDatesTable);
            // stmt.execute(DriversWorkedDatesTable);
            // stmt.execute(EmployeesRolesTable);
            // stmt.execute(EmployeesShiftsConstraintsTable);
            // stmt.execute(EmployeesShiftsFinalsTable);
            // stmt.execute(EmployeesBranchesTable);
            // stmt.execute(ShiftsCancellationsTable);

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