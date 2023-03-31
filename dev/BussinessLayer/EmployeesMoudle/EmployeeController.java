package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Misc.*;

public class EmployeeController {
    private List<Employee> employees;

    public EmployeeController(){
        employees = new LinkedList<>();

        //Adding Hr manager manualy to the system.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-02-1980", formatter);
        addHRManagerForStartUpTheSystem("Rami", "Arnon", 123456789, "abc", 0, 0, 0, 50000, 30000, localDate, null, Role.HRMANAGER);
        //Adding Hr manager manualy to the system.
    }

    public void logIn(int id, String password){
        if (isEmployeeExists(id) && !isEmployeeLoggedIn(id)){
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
            System.out.println("You must enter a valid Id and be logged out to that user before you log in back again.");
        }
    }

    public void logOut(int id){
        if (isEmployeeExists(id) && isEmployeeLoggedIn(id)){
            Employee e = getEmployeeById(id);
            e.SetIsLoggedInToFalse();
            System.out.println("Bye Bye " + e.getFirstName() + " " + 
            e.getLastName() + " You have logged out successfully.");
        }
        else{
            System.out.println("You must enter a valid Id and be logged in to that user.");
        }
    }

    public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role){
        if (isEmployeeExists(managerId) && isEmployeeLoggedIn(managerId) && isEmployeeHRManager(managerId)){
            employees.add(new Employee(firstName, lastName, id, password, bankNum,
            bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role));
            System.out.println("The employee " + firstName + " " + lastName + " has been added successfully");
        }
        else{
            System.out.println("You must be logged in, and be an HR manager in order to do that action.");
        }
        
    }

    public void printAllEmployees(int id){
        if (isEmployeeExists(id) && isEmployeeLoggedIn(id) && isEmployeeHRManager(id)){
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
        }
        else{
            System.out.println("You must be logged in, and be an HR manager in order to do that action.");
        }
    }

    public void deleteEmployee(int managerId, int id){
        if(isEmployeeExists(managerId) && isEmployeeLoggedIn(managerId) && isEmployeeHRManager(managerId)){
            Employee employeeToRemove = getEmployeeById(id);
            employees.remove(employeeToRemove);
            System.out.println("The employee " + employeeToRemove.getFirstName() + " " + employeeToRemove.getLastName() + " has been removed successfully");
        }
        else{
            System.out.println("You must be logged in, and be an HR manager in order to do that action.");
        }
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

    private boolean isEmployeeLoggedIn(int id){
        Employee employee = getEmployeeById(id);
        return employee.getIsLoggedIn();
    }

    private boolean isEmployeeHRManager(int id){
        Employee employee = getEmployeeById(id);
        List<Role> managerRoles = employee.getRoles();
        return managerRoles.contains(Role.HRMANAGER);
    }

    private void addHRManagerForStartUpTheSystem(String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role){
        employees.add(new Employee(firstName, lastName, id, password, bankNum,
        bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role));
    }
    //Help Functions//
}
