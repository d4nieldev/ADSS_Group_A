package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.BranchController;
import BussinessLayer.EmployeesMoudle.EmployeeController;
import BussinessLayer.EmployeesMoudle.ShiftController;

public class serviceFactory {
    private EmployeeController employeeController;
    private ShiftController shiftController;
    private BranchController branchController;
    private EmployeeService employeeService;
    private ShiftService shiftService;
    private BranchService branchService;

    public serviceFactory(){
        employeeController = new EmployeeController();
        shiftController = new ShiftController(employeeController);
        branchController = new BranchController(employeeController, shiftController);
        employeeService = new EmployeeService(employeeController);
        shiftService = new ShiftService(shiftController);
        branchService = new BranchService(branchController);
    }

    public EmployeeController getEmployeeController(){return employeeController;}
    public ShiftController getShiftController(){return shiftController;}
    public BranchController getBranchController(){return branchController;}
    public EmployeeService getEmployeeService(){return employeeService;}
    public ShiftService getShiftService(){return shiftService;}
    public BranchService getBranchService(){return branchService;}
}
