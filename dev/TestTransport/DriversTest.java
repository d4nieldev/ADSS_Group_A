// package TestTransport;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.junit.jupiter.api.Assertions.assertThrows;

// import Misc.*;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import BussinessLayer.EmployeesLayer.*;
// import org.junit.jupiter.api.extension.ExtendWith;

// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;

// class DriversTest {

//     EmployeeFacade driversFacade = new EmployeeFacade();
//     Employee manager;

//     @BeforeEach
//     void setUp() {
//         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
//         manager = new Driver("rotem", "kfir", 987, "pass", 1, 3, 4444,
//                 40, 1000, localDate, "driver temp", 8, License.B);
//         driversFacade.addDriver(driver);
//     }
//     @AfterEach
//     void tearDown() {}

//     @Test
//     void logIn() {}

//     @Test
//     void logOut() {}

//     @Test
//     void addEmployee() {}

//     @Test
//     void deleteEmployee() {}

//     @Test
//     void addRoleToEmployee() {}

//     @Test
//     void removeRoleFromEmployee() {}

//     @Test
//     void changeFirstName() {}

//     @Test
//     void changeLastName() {}

//     @Test
//     void changePassword() {}

//     @Test
//     void changeBankNum() {}

//     @Test
//     void changeBankBranch() {}

//     @Test
//     void changeBankAccount() {}

//     @Test
//     void changeSalary() {}
// }