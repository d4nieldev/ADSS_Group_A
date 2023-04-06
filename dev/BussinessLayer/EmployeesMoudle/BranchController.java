package BussinessLayer.EmployeesMoudle;

import java.time.LocalDate;
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

    public void addShift(int managerId, int branchId, LocalDate date, int startHour, int endHour, ShiftTime time){
        employeeController.checkHrManager(managerId);
        shiftController.addShift(branchId, date, startHour, endHour, time);
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
