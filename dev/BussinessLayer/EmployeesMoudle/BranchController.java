package BussinessLayer.EmployeesMoudle;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.management.relation.Role;

import Misc.License;

public class BranchController {
    EmployeeController employeeController;
    ShiftController shiftController;
    List<Branch> branchs;

    public BranchController(EmployeeController employeeController, ShiftController shiftController){
        this.employeeController = employeeController;
        this.shiftController = shiftController;
        branchs = new LinkedList<>();
    }

    public void addEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role, int branchId){
        employeeController.addEmployee(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, bonus, startDate, driverLicense, null, branchId);
        Branch branch = getBranchById(branchId);
        Employee employee = employeeController.getEmployeeById(id);
        branch.addEmployee(employee);
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
