package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.Employee;
import BussinessLayer.EmployeesMoudle.EmployeeController;

import java.time.LocalDate;
import java.util.List;

import Misc.*;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(){
        employeeController = new EmployeeController();
    }

    public void logIn(int id, String password){
        employeeController.logIn(id, password);
    }

    public void logOut(int id){
        employeeController.logOut(id);
    }

    public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role){
        // Employee manager = employeeController.getEmployeeById(managerId);
        // List<Role> managerRoles = manager.getRoles();
        // if (managerRoles.contains(Role.HRMANAGER)){
        //     employeeController.addEmployee(managerId, firstName, lastName, id, password, bankNum,
        //     bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role);
        // }
        // else{
        //     System.out.println("This function is only available for HR manager.");
        // }

        employeeController.addEmployee(managerId, firstName, lastName, id, password, bankNum,
        bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role);
    }

    public void printAllEmployees(int id){
        employeeController.printAllEmployees(id);
    }
}
