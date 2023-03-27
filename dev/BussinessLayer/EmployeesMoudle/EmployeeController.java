package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;

public class EmployeeController {
    private List<Employee> employees;

    public EmployeeController(){
        employees = new LinkedList<>();
    }
    public void addEmployee(){
        employees.add(new Employee());
    }

    public void getAllDrivers(){}

    public void changeFirstName(){}
    public void changeLastName(){}
    public void changeId(){}
    public void changeUsername(){}
    public void changePassword(){}
    public void changeBankNum(){}
    public void changeBankBranch(){}
    public void changeBankAccount(){}
    public void changeSalary(){}
    public void AddBonus(){}
    public void changeStartDate(){}

    public void AddDriverLicense(){}
    public void RemoveriverLicense(){}
    public void addRoles(){}
    public void removeRoles(){}

    public void logIn(int id, String password){
        if (isEmployeeExists(id)){
            Employee e = getEmployeeById(id);
            if (e.getId() == id && e.getPassword().equals(password)) {
                e.SetIsLoggedInToTrue();
                System.out.println("Hello " + e.getFirstName() + " " + 
                e.getLastName() + " You have logged in successfully");
            }
            else {
                System.out.println("Id or password are incorrect.");
            }
        }
        else {
            System.out.println("Employee did not found.");
        }
    }

    //Help Functions//
    private Employee getEmployeeById(int id){ //called only if the employee exist, else will return null.
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return employee;
        }
        return null;
    }

    private boolean isEmployeeExists(int id){
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return true;
        }
        return false;
    }
    //Help Functions//
}
