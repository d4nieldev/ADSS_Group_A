package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.Employee;
import BussinessLayer.EmployeesMoudle.EmployeeController;

import java.time.LocalDate;
import java.util.List;

import Misc.*;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(EmployeeController employeeController){
        this.employeeController = employeeController;
    }

    public void logIn(int id, String password){
        employeeController.logIn(id, password);
    }

    public void logOut(int id){
        employeeController.logOut(id);
    }

    // public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    // int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branch){
    //     employeeController.addEmployee(managerId, firstName, lastName, id, password, bankNum,
    //     bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role, branch);
    // }

    public void deleteEmployee(int managerId, int id){
        employeeController.deleteEmployee(managerId, id);
    }

    public void printAllEmployees(int id){
        employeeController.printAllEmployees(id);
    }

    //-------------------------------------Getters And Setters--------------------------------------------------------

    public void changeFirstName(int managerId, int idEmployee, String firstName){
        employeeController.changeFirstName(managerId, idEmployee, firstName);
    }

    public void changeLastName(int managerId, int idEmployee, String lastName){
        employeeController.changeLastName(managerId, idEmployee, lastName);
    }

    public void changePassword(int managerId, int idEmployee, String password){
        employeeController.changePassword(managerId, idEmployee, password);
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum){
        employeeController.changeBankNum(managerId, idEmployee, bankNum);
    }

    public void changeBankBranch(int managerId, int idEmployee, int bankBranch){
        employeeController.changeBankBranch(managerId, idEmployee, bankBranch);
    }

    public void changeBankAccount(int managerId, int idEmployee, int bankAccount){
        employeeController.changeBankAccount(managerId, idEmployee, bankAccount);
    }

    public void changeSalary(int managerId, int idEmployee, int salary){
        employeeController.changeSalary(managerId, idEmployee, salary);
    }

    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate){
        employeeController.changeStartDate(managerId, idEmployee, stastDate);
    }

    public void changeDriverLicence(int managerId, int idEmployee, License licene){
        employeeController.changeDriverLicence(managerId, idEmployee, licene);
    }
}
