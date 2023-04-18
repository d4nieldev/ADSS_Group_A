package ServiceLayer.EmployeesMoudle;
import BussinessLayer.EmployeesMoudle.*;
import java.time.LocalDate;
import java.time.Month;

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

    public String printAllEmployees(int id){
        return employeeController.printAllEmployees(id);
    }

    public void addRoleToEmployee(int managerId, int idEmployee, String role){
        employeeController.addRoleToEmployee(managerId, idEmployee, role);
    }

    public void removeRoleFromEmployee(int managerId, int idEmployee, String role){
        employeeController.removeRoleFromEmployee(managerId, idEmployee, role);
    }

    public void addBonus(int managerId, int idEmployee, int bonus, int year, Month month){
        employeeController.addBonus(managerId, idEmployee, bonus, year, month);
    }

    public void addRoleToSystem(int managerHR, String role){
        employeeController.addRoleToSystem(managerHR, role);
    }

    public void addPremissionRole(int managerID, String function, String role){
        employeeController.addPremissionRole(managerID, function.toUpperCase(), Role.getRole(role));
    }
    
    public void RemovePremissionRole(int managerID, String function, String role){
        employeeController.RemovePremissionRole(managerID, function.toUpperCase(), Role.getRole(role));
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
