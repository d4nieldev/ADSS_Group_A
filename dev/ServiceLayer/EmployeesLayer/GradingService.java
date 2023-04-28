package ServiceLayer.EmployeesLayer;

import java.time.LocalDate;
import java.util.HashMap;

import Misc.*;

public class GradingService {
    private EmployeeService employeeS;
    private ShiftService shiftS;
    private BranchService branchS;
    private serviceFactory serviceFactory;

    public GradingService(){
        serviceFactory = new serviceFactory();
        employeeS = serviceFactory.getEmployeeService();
        shiftS = serviceFactory.getShiftService();
        branchS = serviceFactory.getBranchService();
    }

    // ------------------------------------------- EMPLOYEE SERVICE ------------------------------------------------------------

    public EmployeeService getEmployeeService(){return employeeS;}

    public void logIn(int id, String password){
        /*
        try{employeeS.logIn(id, password);}
        catch (Error error){System.out.println(error.toString());}   
         */     
        employeeS.logIn(id, password);
    }
    
    public void logOut(int id){
        /*
        try{employeeS.logOut(id);}
        catch (Error error){System.out.println(error.toString());}    
         */    
        employeeS.logOut(id);
    }

    public void addDriver(int managerId, String firstName, String lastName, int id, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, License licence){
        employeeS.addDriver(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, InitializeBonus,
        startDate, tempsEmployment, licence);
    }

    public void deleteDriver(int managerId, int id){
        employeeS.deleteDriver(managerId, id);
    }

    public String printAllEmployees(int id){
        return employeeS.printAllEmployees(id);
    }

    public String printAllDrivers(int id){
        return employeeS.printAllDrivers(id);
    }
    
    public String printDayTransports(int idEmployee, LocalDate date){
        return employeeS.printDayTransports(idEmployee, date);
    }

    public String printDayDrivers(int idEmployee, LocalDate date){
        return employeeS.printDayDrivers(idEmployee, date);
    }

    public String getDayDrivers(int idEmployee, LocalDate date){
        return employeeS.getDayDrivers(idEmployee, date);
    }

    public void changeFirstName(int managerId, int idEmployee, String firstName){
        employeeS.changeFirstName(managerId, idEmployee, firstName);
    }

    public void changeLastName(int managerId, int idEmployee, String lastName){
        employeeS.changeLastName(managerId, idEmployee, lastName);
    }

    public void changePassword(int managerId, int idEmployee, String password){
        employeeS.changePassword(managerId, idEmployee, password);
    }

    public void changeBankNum(int managerId, int idEmployee, int bankNum){
        employeeS.changeBankNum(managerId, idEmployee, bankNum);
    }

    public void changeBankBranch(int managerId, int idEmployee, int bankBranch){
        employeeS.changeBankBranch(managerId, idEmployee, bankBranch);
    }

    public void changeBankAccount(int managerId, int idEmployee, int bankAccount){
        employeeS.changeBankAccount(managerId, idEmployee, bankAccount);
    }

    public void changeSalary(int managerId, int idEmployee, int salary){
        employeeS.changeSalary(managerId, idEmployee, salary);
    }

    public void changeStartDate(int managerId, int idEmployee, LocalDate stastDate){
        employeeS.changeStartDate(managerId, idEmployee, stastDate);
    }

    public void changeDriverLicence(int managerId, int idEmployee, License licene){
        employeeS.changeDriverLicence(managerId, idEmployee, licene);
    }
    
    public void addPremissionRole(int managerID, String function, String role){
        employeeS.addPremissionRole(managerID, function, role);
    }
    
    public void RemovePremissionRole(int managerID, String function, String role){
        employeeS.RemovePremissionRole(managerID, function, role);
    }
    
    public String getManagerType(int id){
        return employeeS.getManagerType(id);
    }

    public void AddConstraintDriver(int driverId, LocalDate date){
        employeeS.AddConstraintDriver(driverId, date);
    }

    public void RemoveConstraintDriver(int driverId, LocalDate date){
        employeeS.RemoveConstraintDriver(driverId, date);
    }

    // ------------------------------------------- SHIFT SERVICE ------------------------------------------------------------

    public void addCancelation(int shiftId, int employeeId ,int itemId, int itemCode){
        shiftS.addCancelation(shiftId, employeeId, itemId, itemCode);
    }

    public String printFinalShift(int employeeId, int idShift){
        return shiftS.printFinalShift(employeeId, idShift);
    }
    
    public String missingStaffToRole(int employeeId, int shiftId){
        return shiftS.missingStaffToRole(employeeId, shiftId);
    }
    
    // ------------------------------------------- BRANCH SERVICE ------------------------------------------------------------

    public BranchService getBranchService(){return branchS;}

    public void addBranch(int managetId, String address, Location location){
        branchS.addBranch(managetId, address, location);
    }

    public void addNewEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, License driverLicense, String role, int branch){
        branchS.addNewEmployee(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary,
         InitializeBonus, startDate, tempsEmployment, driverLicense, role, branch);
    }
    
    public void addForeignEmployee(int managerId, int idEmployee, int branch){
        branchS.addForeignEmployee(managerId, idEmployee, branch);
    }
       
    public void addNotAllowEmployees(int managerId, int idEmployee, int branchId){
        branchS.addNotAllowEmployees(managerId, idEmployee, branchId);
    }
 
    public void deleteEmployee(int managerId, int id){
        branchS.deleteEmployee(managerId, id);
    }
    
    public void addShift(int managerId, int branch, LocalDate date, int startHour, int endHour, ShiftTime time, HashMap<String, Integer> numEmployeesForRole){
        branchS.addShift(managerId, branch, date, startHour, endHour, time, numEmployeesForRole);
    }
    
    public void addConstraint(int branch, int idEmployee, int shift){
        branchS.addConstraint(branch, idEmployee, shift);
    }

    public void removeConstraint(int branch, int idEmployee, int shift){
        branchS.removeConstraint(branch, idEmployee, shift);
    }

    public void approveFinalShift(int managerID, int shiftID, int branchID, HashMap<Integer, String> hrAssigns){
        branchS.approveFinalShift(managerID, shiftID, branchID, hrAssigns);
    }
}
