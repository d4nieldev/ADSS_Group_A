package TestEmployee;


import BussinessLayer.EmployeesLayer.EmployeeFacade;
import Misc.License;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class test3 {

    EmployeeFacade emp = new EmployeeFacade();

    @BeforeEach
    void setUp() {
        emp.logIn(123456789, "HRmanager");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("05-06-2003", formatter);
        emp.addEmployee(123456789,"Tal", "Koren", 123, "123",
                123, 123, 123, 123, 123,
                localDate, "Cashier", "HRMANAGER", 0);
        emp.addDriver(123456789,"Tal", "Koren", 12345, "123",
                123, 123, 123, 123, 123,
                localDate, "Cashier", License.B);
    }

    @AfterEach
    void tearDown(){
        emp.deleteEmployee(123);
        emp.deleteDriver(123456789, 12345);
        emp.logOut(123456789);
    }

    @Test
    void changePassword_1() {
        emp.logOut(123456789);
        //////////////Test 1///////////////
        assertThrows(Error.class, () -> emp.changePassword(123456789, 123, "newpass"));
        emp.logIn(123456789, "HRmanager");
    }

    @Test
    void changePassword_2() {
        emp.changePassword(123456789, 123, "newpass");
        assertTrue(emp.getEmployeeById(123).getPassword() == "newpass");
    }

    @Test
    void changeBankBranch_1() {
        emp.logOut(123456789);
        //////////////Test 1///////////////
        assertThrows(Error.class, () -> emp.changeBankBranch(123456789, 123, 10));
        emp.logIn(123456789, "HRmanager");
    }

    @Test
    void changeBankBranch_2() {
        emp.changeBankBranch(123456789, 123, 10);
        assertTrue(emp.getEmployeeById(123).getBankBranch() == 10);
    }

    @Test
    void changeStartDate_1() {
        emp.logOut(123456789);
        //////////////Test 1///////////////
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate_NEW = LocalDate.parse("20-10-2020", formatter);
        assertThrows(Error.class, () -> emp.changeStartDate(123456789, 123, localDate_NEW));
        emp.logIn(123456789, "HRmanager");
    }

    @Test
    void changeStartDate_2() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate_NEW = LocalDate.parse("20-10-2020", formatter);
        emp.changeStartDate(123456789, 123, localDate_NEW);
        assertTrue(emp.getEmployeeById(123).getStartDate().equals(localDate_NEW));
    }

    @Test
    void changeFirstName_1() {
        emp.logOut(123456789);
        //////////////Test 1///////////////
        assertThrows(Error.class, () -> emp.changeFirstName(123456789, 123, "inbar"));
        emp.logIn(123456789, "HRmanager");
    }

    @Test
    void changeFirstName_2() {
        emp.changeFirstName(123456789, 123, "inbar");
        assertTrue(emp.getEmployeeById(123).getFirstName() == "inbar");
    }

    @Test
    void changeDriverLicence_1() {
        emp.logOut(123456789);
        //////////////Test 1///////////////
        assertThrows(Error.class, () -> emp.changeDriverLicence(123456789, 123456, License.C));
        emp.logIn(123456789, "HRmanager");
    }

    @Test
    void changeDriverLicence_2() {
        emp.changeDriverLicence(123456789, 12345, License.C);
        assertTrue(emp.getDriverById(12345).getDriverLicense() == License.C);
    }
}
