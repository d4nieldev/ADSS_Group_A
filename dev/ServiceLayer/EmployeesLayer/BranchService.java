package ServiceLayer.EmployeesLayer;
import java.time.LocalDate;
import java.util.HashMap;

import BussinessLayer.EmployeesLayer.*;
import Misc.*;

public class BranchService {
    private BranchFacade branchFacade;

    public BranchService(BranchFacade branchFacade){
        this.branchFacade = branchFacade;
    }

    public void addBranch(int managerId, String address){
        branchFacade.addBranch(managerId, address);
    }
    
    public void addNewEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int InitializeBonus, LocalDate startDate, License driverLicense, String role, int branch, String status){
        branchFacade.addNewEmployee(managerId, firstName, lastName, id, password, bankNum,
        bankBranch, bankAccount, salary, InitializeBonus, startDate, driverLicense, role, branch, status);
    }

    public void addForeignEmployee(int managerId, int idEmployee, int branch){
        branchFacade.addForeignEmployee(managerId, idEmployee, branch);
    }

    public void addNotAllowEmployees(int managerId, int idEmployee, int branchId){
        branchFacade.addNotAllowEmployees(managerId, idEmployee, branchId);
    }

    public void deleteEmployee(int managerId, int id){
        branchFacade.deleteEmployee(managerId, id);
    }

    public void addShift(int managerId, int branch, LocalDate date, int startHour, int endHour, ShiftTime time, HashMap<String, Integer> numEmployeesForRole){
        branchFacade.addShift(managerId, branch, date, startHour, endHour, time, numEmployeesForRole);
    }
    
    public void addConstraint(int branch, int idEmployee, int shift){
        branchFacade.addConstraint(branch, idEmployee, shift);
    }
    
    public void removeConstraint(int branch, int idEmployee, int shift){
        branchFacade.removeConstraint(branch, idEmployee, shift);
    }
    
    public void approveFinalShift(int managerID, int shiftID, int branchID, HashMap<Integer, String> hrAssigns){
        branchFacade.approveFinalShift(managerID, shiftID, branchID, hrAssigns);
    }
}
