package BussinessLayer.EmployeesMoudle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.management.relation.Role;

import Misc.License;
import Misc.ShiftTime;

public class BranchController {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    private LinkedList<Branch> branchs;
    private static int branchIdConuter = 0;

    public BranchController(EmployeeController employeeController, ShiftController shiftController){
        this.employeeController = employeeController;
        this.shiftController = shiftController;
        branchs = new LinkedList<>();
    }
    
    public void addBranch(int managerId, String address) {
        employeeController.checkHrManager(managerId);
        branchs.add(new Branch(branchIdConuter, address));
        branchIdConuter++;
    }

    public void addNewEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branchId){
        employeeController.addEmployee(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, bonus, startDate, driverLicense, null, branchId);
        Branch branch = getBranchById(branchId);
        Employee employee = employeeController.getEmployeeById(id);
        branch.addNewEmployee(employee);
    }
    
    public void addForeignEmployee(int managerId, int idEmployee, int branchId){
        Employee employee = employeeController.getEmployeeById(idEmployee);
        Branch branch = getBranchById(branchId);
        branch.addForeignEmployee(employee);
        employee.addBranch(branchId);
    }

    public void addNotAllowEmployees(int managerId, int idEmployee, int branchId){
        Employee employee = employeeController.getEmployeeById(idEmployee);
        Branch branch = getBranchById(branchId);
        branch.addNotAllowEmployees(employee);
        employee.removeBranch(branchId);
    }

    // delete/remove employee from the system.
    public void deleteEmployee(int managerId, int id){
        employeeController.checkEmployee(managerId);
        employeeController.checkLoggedIn(managerId);
        employeeController.checkIfEmployeeAllowed(managerId, employeeController.getDeleteEmployeeListAccess());
        Employee employeeToRemove = employeeController.getEmployeeById(id);
        for (int branchId : employeeToRemove.getAllBranches()) {
            getBranchById(branchId).removeEmployeeFromSystem(employeeToRemove);
        }
        employeeController.deleteEmployee(id);
    }

    public void addShift(int managerId, int branchId, LocalDate date, int startHour, int endHour, ShiftTime time){
        employeeController.checkHrManager(managerId);
        int shiftID = shiftController.getShiftIdConuter();
        Shift newShift = new Shift(shiftID, branchId, date, startHour, endHour, time);
        shiftController.addShift(newShift);
        Branch branch = getBranchById(branchId);
        branch.addShift(newShift);
    }
    
    // add constaint to shift
    public void addConstraint(int idBranch, int idEmployee, int idShift){
        // check list is not finishSettingShift
        Branch branch = getBranchById(idBranch);
        Shift shift = shiftController.getShift(idShift);
        branch.checkShiftInBranch(shift);
        if(shift.getIsFinishSettingShift()) {throw new Error("This shift is not avalible for submiting constraints anymore.");}
        // check employee in branch
        Employee employee = employeeController.getEmployeeById(idEmployee);
        branch.checkEmployeeInBranch(employee);
        // check employee and role is not already in the constraints list in this shift
        // if not - add the  constraint to the shift
        shiftController.addConstraint(idShift, employee, employee.getRoles());
    }
    
    // aprove function for the HR manager to a final shift
    public void approveFinalShift(int managerID, int shiftID, int branchID, HashMap<Integer, String> hrAssigns){
        employeeController.checkHrManager(managerID);
        Branch branch = getBranchById(branchID);
        Shift shift = shiftController.getShift(shiftID);
        branch.checkShiftInBranch(shift);
        HashMap<Employee, String> hashMapEmployees = new HashMap<>();
        // new HashMap from Integer and roles to Employees and roles
        for (Integer employeeId : hrAssigns.keySet()) {
            hashMapEmployees.put(employeeController.getEmployeeById(employeeId), hrAssigns.get(employeeId));
        }
        // check: exist branch for all employees
        for (Employee employee : hashMapEmployees.keySet()) {
            branch.checkEmployeeInBranch(employee);
            employee.checkShiftInDate(shift.getDate());
        }
        // check: no employee have a shift on the same day
        for (Employee employee : hashMapEmployees.keySet()) {
            employeeController.checkShiftInDate(employee.getId(), shift.getDate());
        }
        // check: all the role are existing in the employees that needed
        for(Employee employee : hashMapEmployees.keySet()){
            employeeController.checkRoleInEmployee(employee.getId(), hrAssigns.get(employee.getId()));
        }
        // check: no over employees then needed
        shiftController.checkAssignFinalShift(shift.getID(), hashMapEmployees);
    }

    public void changeSuperBranchForEmployee(int managerID, int employeeID, int oldBranchID, int newBranchID){
        // check employee managerID and permitions
        // check employee employeeID
        // check super branch is correct
        // switch super branch for employee
    }

    //-------------------------------------Help Functions--------------------------------------------------------
    private Branch getBranchById(int branchId){
        for (Branch branch : branchs) {
            if (branch.getBranchId() == branchId)
                return branch;
        }
        throw new Error("The branch id " + branchId + "is not in the system. Please try again");
    }
}
