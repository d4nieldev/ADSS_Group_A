package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;
import java.sql.Driver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Misc.*;

public class EmployeeController {
    private List<Employee> employees;

    private List<String> addEmployeeListAccess;
    private List<String> printAllEmployeesListAccess;
    private List<String> deleteEmployeeListAccess;
    private List<String> addRolesListAccess;
    private List<String> removeRolesListAccess;
    private List<String> AddBonusListAccess;
    private List<String> getAllDriversListAccess;
    private List<String> changeFirstNameListAccess;
    private List<String> changeLastNameListAccess;
    private List<String> changePasswordListAccess;
    private List<String> changeBankNumListAccess;
    private List<String> changeBankBranchListAccess;
    private List<String> changeBankAccountListAccess;
    private List<String> changeSalaryListAccess;
    private List<String> changeStartDateListAccess;
    private List<String> changeDriverLicenceListAccess;

    public EmployeeController(){
        employees = new LinkedList<>();
        //Adding Hr manager manualy to the system.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-02-1980", formatter);
        addHRManagerForStartUpTheSystem("Rami", "Arnon", 123456789, "abc", 0, 0,
         0, 50000, 30000, localDate, null, Role.getRole("HRMANAGER"), 0);

         addEmployeeListAccess = new LinkedList<>();
    }

    // commit log in for employee, if exsist
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
            throw new Error("You must enter a valid Id and be logged out to that user before you log in back again.");
        }
    }

    // commit log out for employee, if exsist
    public void logOut(int id){
        if (isEmployeeExists(id) && isEmployeeLoggedIn(id)){
            Employee e = getEmployeeById(id);
            e.SetIsLoggedInToFalse();
            System.out.println("Bye Bye " + e.getFirstName() + " " + 
            e.getLastName() + " You have logged out successfully.");
        }
        else{
            throw new Error("You must enter a valid Id and be logged in to that user.");
        }
    }

    // add employee to the system.
    // only if its HR manager and the employee does not exsist already.
    public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branch){
        if (isEmployeeExists(managerId) && isEmployeeLoggedIn(managerId)){
            checkIfEmployeeAllowed(managerId, addEmployeeListAccess);
            //checkEmployee(id); //not needed - CHECK WITH INBAR.
            employees.add(new Employee(firstName, lastName, id, password, bankNum,
            bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role, branch));
            System.out.println("The employee " + firstName + " " + lastName + " has been added successfully");
        }
        else{
           throw new Error("You must be logged in, and be an HR manager in order to do that action.");
        }      
    }

    // print all employees in the system.
    // only if its HR manager.
    public void printAllEmployees(int id){
        checkEmployee(id);
        checkLoggedIn(id);
        checkIfEmployeeAllowed(id, printAllEmployeesListAccess);
        for (Employee employee : employees) {
            System.out.println(employee.toString());
        }
    }

    // delete/remove employee from the system.
    // only if its HR manager and the employee is exsist.
    public void deleteEmployee(int managerId, int id){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, deleteEmployeeListAccess);
        Employee employeeToRemove = getEmployeeById(id);
        employees.remove(employeeToRemove);
        System.out.println("The employee " + employeeToRemove.getFirstName() + " " + employeeToRemove.getLastName() + " has been removed successfully");
    }

    // return true if the employee already have a shift on this date
    public boolean checkShiftInDate(int idEmployee, LocalDate date){
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        return employee.checkShiftInDate(date);
    }
    
    // need to implement
    public void addRoles(int managerId, int idEmployee, String role){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, addRolesListAccess);
        getEmployeeById(idEmployee).addRole(role);
    }

    public void removeRoles(int managerId, int idEmployee, String role){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, removeRolesListAccess);
        getEmployeeById(idEmployee).removeRole(role);
    }
    
    public void AddBonus(int managerId, int idEmployee, int bonus){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, AddBonusListAccess);
        getEmployeeById(idEmployee).setBonus(getEmployeeById(idEmployee).getBonus() + bonus);
    }

    public void addPremissionRule(String option){
        //switch ()
    }

//-------------------------------------Getters And Setters--------------------------------------------------------

    public LinkedList<Employee> getAllDrivers(){
        LinkedList<Employee> drivers = new LinkedList<>();
        for (Employee employee : employees) {
            if(employee.getRoles().contains(Role.getRole("DRIVER"))){drivers.add(employee);}
        }
        return drivers;
    }

    public void changeFirstName(int managerId, int idEmployee, String firstName){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeFirstNameListAccess);
        getEmployeeById(idEmployee).setFirstName(firstName);
    }

    public void changeLastName(int managerId, int idEmployee, String lastName){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeLastNameListAccess);
        getEmployeeById(idEmployee).setLastName(lastName);
    }

    // dont want to be able to change an uniqe ID in the system
    //public void changeId(int managerId, int idEmployee, int id){}

    public void changePassword(int managerId, int idEmployee, String password){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changePasswordListAccess);
        getEmployeeById(idEmployee).setPassword(password);
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankNumListAccess);
        getEmployeeById(idEmployee).setBankNum(bankNum);
    }
    
    public void changeBankBranch(int managerId, int idEmployee, int bankBranch){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankBranchListAccess);
        getEmployeeById(idEmployee).setBankBranch(bankBranch);
    }
    
    public void changeBankAccount(int managerId, int idEmployee, int bankAccount){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankAccountListAccess);
        getEmployeeById(idEmployee).setBankAccount(bankAccount);
    }
    
    public void changeSalary(int managerId, int idEmployee, int salary){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeSalaryListAccess);
        getEmployeeById(idEmployee).setSalary(salary);
    }
    
    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeStartDateListAccess);
        getEmployeeById(idEmployee).setStartDate(stastDate);
    }
    
    public void changeDriverLicence(int managerId, int idEmployee, License licene){
        checkEmployee(managerId);
        //checkEmployee(idEmployee);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeDriverLicenceListAccess);
        getEmployeeById(idEmployee).setDriverLicense(licene);
    }
    
//-------------------------------------------------------Help Functions------------------------------------------------------------

    //called only if the employee exist, else will return error.
    public Employee getEmployeeById(int id){ 
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return employee;
        }
        throw new Error("The id " + id + "is not in the system. Please try again");
    }

    // return true if the employee exsist already in the system
    private boolean isEmployeeExists(int id){
        for (Employee employee : employees) {
            if (employee.getId() == id)
                return true;
        }
        return false;
    }

    // return true if the employee logged in to the system
    private boolean isEmployeeLoggedIn(int id){
        Employee employee = getEmployeeById(id);
        return employee.getIsLoggedIn();
    }

    // throw error if the employee is not logged in to the system
    public void checkLoggedIn(int id){
        Employee employee = getEmployeeById(id);
        if (!employee.getIsLoggedIn()){
            throw new Error("You must be logged in to the system in order to do that action.");
        }
    }
    
    // return true if the employee is HR manager
    private boolean isEmployeeHRManager(int id){
        Employee employee = getEmployeeById(id);
        List<String> managerRoles = employee.getRoles();
        return managerRoles.contains(Role.getRole("HRMANAGER"));
    }

    // check if the employee is a HRmanager and is sign in to the system
    // throw an error if something went wrong
    public void checkHrManager(int managerId){
        if (!isEmployeeHRManager(managerId) || !isEmployeeLoggedIn(managerId)){
            throw new Error("You must be logged in, and be an HR manager in order to do that action.");
        }
    }
    
    // check for exsisting employee with current id
    // throw an error if something went wrong
    public void checkEmployee(int idEmployee){
        if (!isEmployeeExists(idEmployee)){
            throw new Error("The id " + idEmployee + "is not in the system. Please try again");
        }
    }

    public void checkIfEmployeeAllowed(int idEmployee, List<String> allowed){
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        for (String role : employee.getRoles()) {
            if (allowed.contains(role)){
                return;
            }
        }
        throw new Error("The id " + idEmployee + " is not allowed to do that function.");
    }

    // help function that create HR manager to start up the system
    private void addHRManagerForStartUpTheSystem(String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branch){
        employees.add(new Employee(firstName, lastName, id, password, bankNum,
        bankBranch, bankAccount, salary, bonus, startDate, driverLicense, role, branch));
    }
}
