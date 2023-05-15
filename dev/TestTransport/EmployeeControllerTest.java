 package TestTransport;

 import BussinessLayer.EmployeesLayer.Employee;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
 import org.junit.jupiter.api.AfterEach;
 import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

 class EmployeeControllerTest {

     EmployeeFacade emp = new EmployeeFacade();

     @BeforeEach
     void setUp() {
         emp.logIn(123456789, "HRmanager");
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Cashier", "HRMANAGER", 0);
     }

     @AfterEach
     void tearDown(){
         emp.deleteEmployee(123);
         emp.logOut(123456789);
     }

     @Test
     void logIn() {

         emp.logOut(123456789);
         //////////////Test 1///////////////
         assertFalse(emp.getEmployeeById(123456789).getIsLoggedIn()); //check that at the begging the employee is logged off.
         emp.logIn(123456789, "HRmanager");
         assertTrue(emp.getEmployeeById(123456789).getIsLoggedIn()); //check that the login function worked.

         //////////////Test 2///////////////
         assertThrows(Error.class, () -> emp.logIn(123456789, "HRmanager")); //log in to logged in employee.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.logIn(123789, "HRmanager")); // log in to non exist employee

         //////////////Test 4///////////////
         assertThrows(Error.class, () -> emp.logIn(123456789, "HRmanagerrrrrr")); // log in with incorrect password.
     }

     @Test
     void logOut() {

         //////////////Test 1///////////////
         emp.logOut(123456789);
         assertFalse(emp.getEmployeeById(123456789).getIsLoggedIn()); //check the logout function works.

         //////////////Test 2///////////////
         assertThrows(Error.class, () -> emp.logOut(123456789)); //logout from logged out employee.
         emp.logIn(123456789, "HRmanager");
     }

     @Test
     void addEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         //////////////Test 1///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123, localDate,
                 "Chashier", "HRMANAGER", 0)); //add employee from offline HR.

         emp.logIn(123456789, "HRmanager");
         //////////////Test 2///////////////
         emp.addEmployee(123456789,"Tal", "Koren", 124, "124",
                 123, 123, 123, 123, 123,
                 localDate, "Cashier", "HRMANAGER", 0);

         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Inbar", "Chaim", 124, "124",
                 123, 123, 123, 123, 123,
                 localDate, "Cashier", "Cashier", 0)); //add employee with id that already exist in the system.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Tal", "Koren", 124, "124",
                 123, 123, 123, 123, 123, localDate,
                 "Chashier", "Chashier", 0)); //add employee from non HR employee.

         emp.deleteEmployee(124);
     }

     @Test
     void deleteEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         //////////////Test 1///////////////
         emp.addEmployee(123456789,"Tal", "Koren", 1244, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Cashier", "Cashier", 0);

         emp.deleteEmployee(1244);
         assertThrows(Error.class, () -> emp.getEmployeeById(1244)); //check that the employee got deleted.

         //////////////Test 2///////////////
         assertThrows(Error.class, () -> emp.deleteEmployee(1244)); //delete employee that do not exist.

     }

     @Test
     void addRoleToEmployee() {

         //////////////Test 1///////////////
         emp.addRoleToEmployee(123456789,123,1);
         Employee e1 = emp.getEmployeeById(123);
         assertTrue(e1.getRoles().contains("HRmanager")); //check if the function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,123,8)); //add role from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123,123,8)); //add role from non HR manager employee.
         emp.logOut(123);

         //////////////Test 4///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,12345,8)); //add role to not exist employee.

         //////////////Test 5///////////////
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,123,8)); //try to add role that already exist in that employee.

         emp.removeRoleFromEmployee(123456789,123,8);
     }

//     @Test
//     void removeRoleFromEmployee() {
//
//         //////////////Test 1///////////////
//         emp.addRoleToEmployee(123456789,123,8);
//         emp.removeRoleFromEmployee(123456789, 123, 8);
//         Employee e1 = emp.getEmployeeById(123);
//         assertFalse(e1.getRoles().contains("DriverNew")); //check if the function works.
//
//         //////////////Test 2///////////////
//         emp.addRoleToEmployee(123456789,123,8);
//         emp.removeRoleFromEmployee(123456789, 123, 8);
//         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,123,emp.getRoleClassInstance().getRoleByName("DriverNew").getId())); //try to remove role that the employee do not have.
//
//         //////////////Test 3///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,123,emp.getRoleClassInstance().getRoleByName("DriverNew").getId())); //try to remove role from logged out employee.
//
//         //////////////Test 4///////////////
//         emp.logIn(123, "123");
//         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123,123,emp.getRoleClassInstance().getRoleByName("DriverNew").getId())); //non HR employee try to remove role.
//
//         //////////////Test 5///////////////
////         emp.logIn(123456789,"abc");
////         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,12345,emp.getRoleClassInstance().getRoleByName("DriverNew").getId())); //try to remove role from non exist employee.
//
//         emp.logOut(123);
//         emp.logIn(123456789, "HRmanager");
//         emp.getRoleClassInstance().removeRole("DriverNew");
//     }

     @Test
     void changeFirstName() {

         //////////////Test 1///////////////
         emp.changeFirstName(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getFirstName()); //check if function works.

         //////////////Test 2///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.changeFirstName(123456789, 123, "Tal")); //try to change from logged out employee.
//
//         //////////////Test 3///////////////
//         emp.logIn(123456789, "HRmanager");
         assertThrows(Error.class, () -> emp.changeFirstName(123456789, 12345, "Tal")); //try to change non exist employee.

         //////////////Test 4///////////////
//         assertThrows(Error.class, () -> emp.changeFirstName(123, 123, "Tal")); //try to change from non HR manager employee.
     }

     @Test
     void changeLastName() {

         //////////////Test 1///////////////
         emp.changeLastName(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getLastName()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         //assertThrows(Error.class, () -> emp.changeLastName(123456789, 123, "Tal")); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "HRmanager");
         assertThrows(Error.class, () -> emp.changeLastName(123456789, 12345, "Tal")); //try to change non exist employee.

         //////////////Test 4///////////////
         //assertThrows(Error.class, () -> emp.changeLastName(123, 123, "Tal")); //try to change from non HR manager employee.

     }

     @Test
     void changePassword() {

         //////////////Test 1///////////////
         emp.changePassword(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getPassword()); //check if function works.

//         //////////////Test 2///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.changePassword(123456789, 123, "Tal")); //try to change from logged out employee.
//
//         //////////////Test 3///////////////
//         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changePassword(123456789, 12345, "Tal")); //try to change non exist employee.

//         emp.logOut(123456789);
//
//         //////////////Test 4///////////////
//         emp.logIn(123, "123");
//         assertThrows(Error.class, () -> emp.changePassword(123, 123, "Tal")); //try to change from non HR manager employee.
//
//         emp.logOut(123);
//         emp.logIn(123456789, "abc");
     }

     @Test
     void changeBankNum() {

         //////////////Test 1///////////////
         emp.changeBankNum(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankNum()); //check if function works.

         //////////////Test 2///////////////
         //assertThrows(Error.class, () -> emp.changeBankNum(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.changeBankNum(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
//         assertThrows(Error.class, () -> emp.changeBankNum(123, 123, 7777)); //try to change from non HR manager employee.

     }

     @Test
     void changeBankBranch() {

         //////////////Test 1///////////////
         emp.changeBankBranch(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankBranch()); //check if function works.

//         //////////////Test 2///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.changeBankBranch(123456789, 123, 7777)); //try to change from logged out employee.
//
//         //////////////Test 3///////////////
//         emp.logIn(123456789, "HRmanager");
         assertThrows(Error.class, () -> emp.changeBankBranch(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
         assertThrows(Error.class, () -> emp.changeBankBranch(123, 123, 7777)); //try to change from non HR manager employee.

     }

     @Test
     void changeBankAccount() {

         //////////////Test 1///////////////
         emp.changeBankAccount(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankAccount()); //check if function works.

         //////////////Test 2///////////////
         //assertThrows(Error.class, () -> emp.changeBankAccount(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.changeBankAccount(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.changeBankAccount(123, 123, 7777)); //try to change from non HR manager employee.
//
//         emp.logIn(123456789, "HRmanager");
     }

     @Test
     void changeSalary() {

         //////////////Test 1///////////////
         emp.changeSalary(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getSalary()); //check if function works.

         //////////////Test 2///////////////
//         emp.logOut(123456789);
//         assertThrows(Error.class, () -> emp.changeSalary(123456789, 123, 7777)); //try to change from logged out employee.
//
//         //////////////Test 3///////////////
//         emp.logIn(123456789, "HRmanager");
         assertThrows(Error.class, () -> emp.changeSalary(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
//         emp.logIn(123, "123");
//         assertThrows(Error.class, () -> emp.changeSalary(123, 123, 7777)); //try to change from non HR manager employee.
     }
 }