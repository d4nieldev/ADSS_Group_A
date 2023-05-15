 package TestTransport;

 import BussinessLayer.EmployeesLayer.Employee;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

 class EmployeeControllerTest {

     EmployeeFacade emp = new EmployeeFacade();

     @BeforeEach
     void setUp() {

     }

     @Test
     void logIn() {

         //////////////Test 1///////////////
         assertFalse(emp.getEmployeeById(123456789).getIsLoggedIn()); //check that at the begging the employee is logged off.
         emp.logIn(123456789, "abc");
         assertTrue(emp.getEmployeeById(123456789).getIsLoggedIn()); //check that the login function worked.
         emp.logOut(123456789);

         //////////////Test 2///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.logIn(123456789, "abc")); //log in to logged in employee.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.logIn(123789, "abc")); // log in to non exist employee

         //////////////Test 4///////////////
         assertThrows(Error.class, () -> emp.logIn(123456789, "ab")); // log in with incorrect password.
     }

     @Test
     void logOut() {

         //////////////Test 1///////////////
         emp.logIn(123456789, "abc");
         emp.logOut(123456789);
         assertFalse(emp.getEmployeeById(123456789).getIsLoggedIn()); //check the logout function works.

         //////////////Test 2///////////////
         assertThrows(Error.class, () -> emp.logOut(123456789)); //logout from logged out employee.
     }

     @Test
     void addEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         //////////////Test 1///////////////
         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123, localDate,
                 "Chashier", "HRMANAGER", 0)); //add employee from offline HR.

         //////////////Test 2///////////////
         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "HRMANAGER", 0);

         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Inbar", "Chaim", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0)); //add employee with id that already exist in the system.

         //////////////Test 3///////////////
         assertThrows(Error.class, () -> emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123, localDate,
                 "Chashier", "Chashier", 0)); //add employee from non HR employee.

     }

     @Test
     void deleteEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");

         //////////////Test 1///////////////
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         emp.deleteEmployee(123);
         assertThrows(Error.class, () -> emp.getEmployeeById(123)); //check that the employee got deleted.

         //////////////Test 2///////////////
         assertThrows(Error.class, () -> emp.deleteEmployee(123)); //delete employee that do not exist.

     }

     @Test
     void addRoleToEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.addRoleToEmployee(123456789,123,8);
         Employee e1 = emp.getEmployeeById(123);
         assertTrue(e1.getRoles().contains("Driver")); //check if the function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,123,8)); //add role from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123,123,8)); //add role from non HR manager employee.

         //////////////Test 4///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,12345,8)); //add role to not exist employee.

         //////////////Test 5///////////////
         assertThrows(Error.class, () -> emp.addRoleToEmployee(123456789,123,8)); //try to add role that already exist in that employee.

     }

     @Test
     void removeRoleFromEmployee() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.addRoleToEmployee(123456789,123,8);
         emp.removeRoleFromEmployee(123456789, 123, 8);
         Employee e1 = emp.getEmployeeById(123);
         assertFalse(e1.getRoles().contains("Driver")); //check if the function works.

         //////////////Test 2///////////////
         emp.addRoleToEmployee(123456789,123,8);
         emp.removeRoleFromEmployee(123456789, 123, 8);
         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,123,8)); //try to remove role that the employee do not have.

         //////////////Test 3///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,123,8)); //try to remove role from logged out employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123,123,8)); //non HR employee try to remove role.

         //////////////Test 5///////////////
         emp.logIn(123456789,"abc");
         assertThrows(Error.class, () -> emp.removeRoleFromEmployee(123456789,12345,8)); //try to remove role from non exist employee.
     }

     @Test
     void changeFirstName() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeFirstName(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getFirstName()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeFirstName(123456789, 123, "Tal")); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeFirstName(123456789, 12345, "Tal")); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeFirstName(123, 123, "Tal")); //try to change from non HR manager employee.
     }

     @Test
     void changeLastName() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeLastName(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getLastName()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeLastName(123456789, 123, "Tal")); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeLastName(123456789, 12345, "Tal")); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeLastName(123, 123, "Tal")); //try to change from non HR manager employee.
     }

     @Test
     void changePassword() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(1123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changePassword(123456789, 123, "Inbar");
         assertEquals("Inbar", emp.getEmployeeById(123).getPassword()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changePassword(123456789, 123, "Tal")); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changePassword(123456789, 12345, "Tal")); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changePassword(123, 123, "Tal")); //try to change from non HR manager employee.
     }

     @Test
     void changeBankNum() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeBankNum(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankNum()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeBankNum(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeBankNum(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeBankNum(123, 123, 7777)); //try to change from non HR manager employee.
     }

     @Test
     void changeBankBranch() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeBankBranch(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankBranch()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeBankBranch(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeBankBranch(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeBankBranch(123, 123, 7777)); //try to change from non HR manager employee.
     }

     @Test
     void changeBankAccount() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeBankAccount(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getBankAccount()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeBankAccount(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeBankAccount(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeBankAccount(123, 123, 7777)); //try to change from non HR manager employee.
     }

     @Test
     void changeSalary() {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         LocalDate localDate = LocalDate.parse("05-06-2003", formatter);

         emp.logIn(123456789, "abc");
         emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                 123, 123, 123, 123, 123,
                 localDate, "Chashier", "Chashier", 0);

         //////////////Test 1///////////////
         emp.changeSalary(123456789, 123, 8888);
         assertEquals(8888, emp.getEmployeeById(123).getSalary()); //check if function works.

         //////////////Test 2///////////////
         emp.logOut(123456789);
         assertThrows(Error.class, () -> emp.changeSalary(123456789, 123, 7777)); //try to change from logged out employee.

         //////////////Test 3///////////////
         emp.logIn(123456789, "abc");
         assertThrows(Error.class, () -> emp.changeSalary(123456789, 12345, 7777)); //try to change non exist employee.

         //////////////Test 4///////////////
         emp.logIn(123, "123");
         assertThrows(Error.class, () -> emp.changeSalary(123, 123, 7777)); //try to change from non HR manager employee.
     }
 }