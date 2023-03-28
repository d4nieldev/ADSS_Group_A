package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.EmployeeController;

import java.time.LocalDate;
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

    public void addEmployee(String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role){
        employeeController.addEmployee(firstName, lastName, id, password, bankNum,
        bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role);
    }

    public void printAllEmployees(int id){

    }
}
