package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;
import java.sql.Driver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Misc.*;

public class EmployeeController {
    private LinkedList<Employee> employees;

    private LinkedList<String> addEmployeeListAccess;
    private LinkedList<String> printAllEmployeesListAccess;
    private LinkedList<String> deleteEmployeeListAccess;
    private LinkedList<String> addRolesListAccess;
    private LinkedList<String> removeRolesListAccess;
    private LinkedList<String> AddBonusListAccess;
    private LinkedList<String> getAllDriversListAccess;
    private LinkedList<String> changeFirstNameListAccess;
    private LinkedList<String> changeLastNameListAccess;
    private LinkedList<String> changePasswordListAccess;
    private LinkedList<String> changeBankNumListAccess;
    private LinkedList<String> changeBankBranchListAccess;
    private LinkedList<String> changeBankAccountListAccess;
    private LinkedList<String> changeSalaryListAccess;
    private LinkedList<String> changeStartDateListAccess;
    private LinkedList<String> changeDriverLicenceListAccess;
    private LinkedList<String> addCancelationListAccess;
    private LinkedList<String> printFinalShiftListAccess;
    private LinkedList<String> missingStaffToRoleListAccess;

    public EmployeeController(){
        employees = new LinkedList<>();
        //Adding Hr manager manualy to the system.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse("01-02-1980", formatter);
        addHRManagerForStartUpTheSystem("Rami", "Arnon", 123456789, "abc", 0, 0,
         0, 50000, 30000, localDate, null, Role.getRole("HRMANAGER"), 0);

         addEmployeeListAccess = new LinkedList<>(); addEmployeeListAccess.add(Role.getRole("HRMANAGER"));
         printAllEmployeesListAccess = new LinkedList<>(); printAllEmployeesListAccess.add(Role.getRole("HRMANAGER"));
         deleteEmployeeListAccess = new LinkedList<>(); deleteEmployeeListAccess.add(Role.getRole("HRMANAGER"));
         addRolesListAccess = new LinkedList<>(); addRolesListAccess.add(Role.getRole("HRMANAGER"));
         removeRolesListAccess = new LinkedList<>(); removeRolesListAccess.add(Role.getRole("HRMANAGER"));
         AddBonusListAccess = new LinkedList<>(); AddBonusListAccess.add(Role.getRole("HRMANAGER"));
         getAllDriversListAccess = new LinkedList<>(); getAllDriversListAccess.add(Role.getRole("HRMANAGER"));
         changeFirstNameListAccess = new LinkedList<>(); changeFirstNameListAccess.add(Role.getRole("HRMANAGER"));
         changeLastNameListAccess = new LinkedList<>(); changeLastNameListAccess.add(Role.getRole("HRMANAGER"));
         changePasswordListAccess = new LinkedList<>(); changePasswordListAccess.add(Role.getRole("HRMANAGER"));
         changeBankNumListAccess = new LinkedList<>(); changeBankNumListAccess.add(Role.getRole("HRMANAGER"));
         changeBankBranchListAccess = new LinkedList<>(); changeBankBranchListAccess.add(Role.getRole("HRMANAGER"));
         changeBankAccountListAccess = new LinkedList<>(); changeBankAccountListAccess.add(Role.getRole("HRMANAGER"));
         changeSalaryListAccess = new LinkedList<>(); changeSalaryListAccess.add(Role.getRole("HRMANAGER"));
         changeStartDateListAccess = new LinkedList<>(); changeStartDateListAccess.add(Role.getRole("HRMANAGER"));
         changeDriverLicenceListAccess = new LinkedList<>(); changeDriverLicenceListAccess.add(Role.getRole("HRMANAGER"));
         addCancelationListAccess = new LinkedList<>(); addCancelationListAccess.add(Role.getRole("HRMANAGER"));
         printFinalShiftListAccess = new LinkedList<>(); printFinalShiftListAccess.add(Role.getRole("HRMANAGER"));
         missingStaffToRoleListAccess = new LinkedList<>(); missingStaffToRoleListAccess.add(Role.getRole("HRMANAGER"));
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
        System.out.println("The employee " + employeeToRemove.getFirstName() + " " + employeeToRemove.getLastName() + 
        " has been removed successfully");
    }

    public void checkShiftInDate(int idEmployee, LocalDate date){
        checkEmployee(idEmployee);
        Employee employee = getEmployeeById(idEmployee);
        if(employee.checkShiftInDate(date)){
            throw new Error("The employee " + toString() + " already have ha shift on the date " + date.toString());
        }
    }
    
    public void addRoles(int managerId, int idEmployee, String role){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, addRolesListAccess);
        getEmployeeById(idEmployee).addRole(role);
    }

    public void removeRoles(int managerId, int idEmployee, String role){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, removeRolesListAccess);
        getEmployeeById(idEmployee).removeRole(role);
    }
    
    public void checkRoleInEmployee(int idEmployee, String role){
        checkEmployee(idEmployee);
        getEmployeeById(idEmployee).checkRoleInEmployee(role);
    }

    public void AddBonus(int managerId, int idEmployee, int bonus){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, AddBonusListAccess);
        getEmployeeById(idEmployee).setBonus(getEmployeeById(idEmployee).getBonus() + bonus);
    }

    public void addPremissionRole(int managerId, String function, String role){
        checkHrManager(managerId);
        switch (function){
            case("ADDEMPLOYEE") : {
                if(addEmployeeListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                addEmployeeListAccess.add(role);
            }
            case("PRINTALLEMPLOYEES") : {
                if(printAllEmployeesListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                printAllEmployeesListAccess.add(role);
            }
            case("DELETEEMPLOYEE") : {
                if(deleteEmployeeListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                deleteEmployeeListAccess.add(role);
            }
            case("ADDROLETOEMPLOYEE") : {
                if(addRolesListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                addRolesListAccess.add(role);
            }
            case("REMOVEROLEFROMEMPLOYEE") : {
                if(removeRolesListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                removeRolesListAccess.add(role);
            }
            case("ADDBONUS") : {
                if(AddBonusListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                AddBonusListAccess.add(role);
            }
            case("GETALLDRIVERS") : {
                if(getAllDriversListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                getAllDriversListAccess.add(role);
            }
            case("CHANGEFIRSTNAME") : {
                if(changeFirstNameListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeFirstNameListAccess.add(role);
            }
            case("CHANGELASTNAME") : {
                if(changeLastNameListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeLastNameListAccess.add(role);
            }
            case("CHANGEPASSWORD") : {
                if(changePasswordListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changePasswordListAccess.add(role);
            }
            case("CHANGEBANKNUMBER") : {
                if(changeBankNumListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeBankNumListAccess.add(role);
            }
            case("CHANGEBANKBRANCH") : {
                if(changeBankBranchListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeBankBranchListAccess.add(role);
            }
            case("CHANGEBANKACCOUNT") : {
                if(changeBankAccountListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeBankAccountListAccess.add(role);
            }
            case("CHANGRSALARY") : {
                if(changeSalaryListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeSalaryListAccess.add(role);
            }
            case("CHANGESTARTDATE") : {
                if(changeStartDateListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeStartDateListAccess.add(role);
            }
            case("CHANGEDRIVERLIVENCE") : {
                if(changeDriverLicenceListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                changeDriverLicenceListAccess.add(role);
            }
            case("ADDCANCELATION") : {
                if(addCancelationListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                addCancelationListAccess.add(role);
            }
            case("PRINTFINALSHIFT") : {
                if(printFinalShiftListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                printFinalShiftListAccess.add(role);
            }
            case("MISSINGSTAFFTOROLE") : {
                if(missingStaffToRoleListAccess.contains(role)){throw new Error("This role can already do this function in the system.");}
                missingStaffToRoleListAccess.add(role);
            }
        }
    }
        
    public void RemovePremissionRole(int managerId, String function, String role){
        checkHrManager(managerId);
        switch (function){
            case("ADDEMPLOYEE") : {
                if(!addEmployeeListAccess.contains(role)){throw new Error("This role can not do this function according to system.");}
                addEmployeeListAccess.remove(role);
            }
            case("PRINTALLEMPLOYEES") : {
                if(!printAllEmployeesListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                printAllEmployeesListAccess.remove(role);
            }
            case("DELETEEMPLOYEE") : {
                if(!deleteEmployeeListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                deleteEmployeeListAccess.remove(role);
            }
            case("ADDROLETOEMPLOYEE") : {
                if(!addRolesListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                addRolesListAccess.remove(role);
            }
            case("REMOVEROLEFROMEMPLOYEE") : {
                if(!removeRolesListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                removeRolesListAccess.remove(role);
            }
            case("ADDBONUS") : {
                if(!AddBonusListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                AddBonusListAccess.remove(role);
            }
            case("GETALLDRIVERS") : {
                if(!getAllDriversListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                getAllDriversListAccess.remove(role);
            }
            case("CHANGEFIRSTNAME") : {
                if(!changeFirstNameListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeFirstNameListAccess.remove(role);
            }
            case("CHANGELASTNAME") : {
                if(!changeLastNameListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeLastNameListAccess.remove(role);
            }
            case("CHANGEPASSWORD") : {
                if(!changePasswordListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changePasswordListAccess.remove(role);
            }
            case("CHANGEBANKNUMBER") : {
                if(!changeBankNumListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeBankNumListAccess.remove(role);
            }
            case("CHANGEBANKBRANCH") : {
                if(!changeBankBranchListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeBankBranchListAccess.remove(role);
            }
            case("CHANGEBANKACCOUNT") : {
                if(!changeBankAccountListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeBankAccountListAccess.remove(role);
            }
            case("CHANGRSALARY") : {
                if(!changeSalaryListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeSalaryListAccess.remove(role);
            }
            case("CHANGESTARTDATE") : {
                if(!changeStartDateListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeStartDateListAccess.remove(role);
            }
            case("CHANGEDRIVERLIVENCE") : {
                if(!changeDriverLicenceListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                changeDriverLicenceListAccess.remove(role);
            }
            case("ADDCANCELATION") : {
                if(!addCancelationListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                addCancelationListAccess.remove(role);
            }
            case("PRINTFINALSHIFT") : {
                if(!printFinalShiftListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                printFinalShiftListAccess.remove(role);
            }
            case("MISSINGSTAFFTOROLE") : {
                if(!missingStaffToRoleListAccess.contains(role)){throw new Error("This role can not do this function according to the system.");}
                missingStaffToRoleListAccess.remove(role);
            }
        }
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
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeFirstNameListAccess);
        getEmployeeById(idEmployee).setFirstName(firstName);
    }

    public void changeLastName(int managerId, int idEmployee, String lastName){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeLastNameListAccess);
        getEmployeeById(idEmployee).setLastName(lastName);
    }

    public void changePassword(int managerId, int idEmployee, String password){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changePasswordListAccess);
        getEmployeeById(idEmployee).setPassword(password);
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankNumListAccess);
        getEmployeeById(idEmployee).setBankNum(bankNum);
    }
    
    public void changeBankBranch(int managerId, int idEmployee, int bankBranch){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankBranchListAccess);
        getEmployeeById(idEmployee).setBankBranch(bankBranch);
    }
    
    public void changeBankAccount(int managerId, int idEmployee, int bankAccount){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeBankAccountListAccess);
        getEmployeeById(idEmployee).setBankAccount(bankAccount);
    }
    
    public void changeSalary(int managerId, int idEmployee, int salary){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeSalaryListAccess);
        getEmployeeById(idEmployee).setSalary(salary);
    }
    
    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeStartDateListAccess);
        getEmployeeById(idEmployee).setStartDate(stastDate);
    }
    
    public void changeDriverLicence(int managerId, int idEmployee, License licene){
        checkEmployee(managerId);
        checkLoggedIn(managerId);
        checkIfEmployeeAllowed(managerId, changeDriverLicenceListAccess);
        getEmployeeById(idEmployee).setDriverLicense(licene);
    }

    public LinkedList<String> getAddCancelationListAccess(){return addCancelationListAccess;}
    public LinkedList<String> getPrintFinalShiftListAccess(){return printFinalShiftListAccess;}
    public LinkedList<String> getMissingStaffToRoleListAccess(){return missingStaffToRoleListAccess;}
    
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
