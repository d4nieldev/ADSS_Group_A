package ServiceLayer.EmployeesLayer;
import java.time.LocalDate;
import java.time.Month;

import BussinessLayer.EmployeesLayer.*;
import Misc.*;

public class EmployeeService {
    private EmployeeFacade employeeFacade;

    public EmployeeService(EmployeeFacade employeeFacade){
        this.employeeFacade = employeeFacade;
    }

    public void logIn(int id, String password){
        employeeFacade.logIn(id, password);
    }

    public void logOut(int id){
        employeeFacade.logOut(id);
    }

    public String printAllEmployees(int id){
        return employeeFacade.printAllEmployees(id);
    }

    public void addRoleToEmployee(int managerId, int idEmployee, String role){
        employeeFacade.addRoleToEmployee(managerId, idEmployee, role);
    }

    public void removeRoleFromEmployee(int managerId, int idEmployee, String role){
        employeeFacade.removeRoleFromEmployee(managerId, idEmployee, role);
    }

    public void addBonus(int managerId, int idEmployee, int bonus, int year, Month month){
        employeeFacade.addBonus(managerId, idEmployee, bonus, year, month);
    }

    public void addRoleToSystem(int managerHR, String role){
        employeeFacade.addRoleToSystem(managerHR, role);
    }

    public void addPremissionRole(int managerID, String function, String role){
        employeeFacade.addPremissionRole(managerID, function.toUpperCase(), Role.getRole(role));
    }
    
    public void RemovePremissionRole(int managerID, String function, String role){
        employeeFacade.RemovePremissionRole(managerID, function.toUpperCase(), Role.getRole(role));
    }


    //-------------------------------------Getters And Setters--------------------------------------------------------

    public void changeFirstName(int managerId, int idEmployee, String firstName){
        employeeFacade.changeFirstName(managerId, idEmployee, firstName);
    }

    public void changeLastName(int managerId, int idEmployee, String lastName){
        employeeFacade.changeLastName(managerId, idEmployee, lastName);
    }

    public void changePassword(int managerId, int idEmployee, String password){
        employeeFacade.changePassword(managerId, idEmployee, password);
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum){
        employeeFacade.changeBankNum(managerId, idEmployee, bankNum);
    }

    public void changeBankBranch(int managerId, int idEmployee, int bankBranch){
        employeeFacade.changeBankBranch(managerId, idEmployee, bankBranch);
    }

    public void changeBankAccount(int managerId, int idEmployee, int bankAccount){
        employeeFacade.changeBankAccount(managerId, idEmployee, bankAccount);
    }

    public void changeSalary(int managerId, int idEmployee, int salary){
        employeeFacade.changeSalary(managerId, idEmployee, salary);
    }

    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate){
        employeeFacade.changeStartDate(managerId, idEmployee, stastDate);
    }

    public void changeDriverLicence(int managerId, int idEmployee, License licene){
        employeeFacade.changeDriverLicence(managerId, idEmployee, licene);
    }

    public String getManagerType(int id){
        return employeeFacade.getManagerType(id);
    }
}
