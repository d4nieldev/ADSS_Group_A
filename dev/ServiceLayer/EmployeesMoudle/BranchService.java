package ServiceLayer.EmployeesMoudle;

import java.security.interfaces.RSAPrivateCrtKey;

import BussinessLayer.EmployeesMoudle.BranchController;
import BussinessLayer.EmployeesMoudle.EmployeeController;
import BussinessLayer.EmployeesMoudle.ShiftController;
import Misc.*;

public class BranchService {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    private BranchController branchController;

    public BranchService(EmployeeController employeeController, ShiftController shiftController, BranchController branchController){
        this.employeeController = employeeController;
        this.shiftController = shiftController;
        this.branchController = branchController;
    }
}
