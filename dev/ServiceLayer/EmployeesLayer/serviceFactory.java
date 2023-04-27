package ServiceLayer.EmployeesLayer;

import BussinessLayer.EmployeesLayer.BranchFacade;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import BussinessLayer.EmployeesLayer.ShiftFacade;

public class serviceFactory {
    private EmployeeFacade employeeFacade;
    private ShiftFacade shiftFacade;
    private BranchFacade branchFacade;
    private EmployeeService employeeService;
    private ShiftService shiftService;
    private BranchService branchService;

    public serviceFactory(){
        employeeFacade = new EmployeeFacade();
        shiftFacade = new ShiftFacade(employeeFacade);
        branchFacade = new BranchFacade(employeeFacade, shiftFacade);
        employeeService = new EmployeeService(employeeFacade);
        shiftService = new ShiftService(shiftFacade);
        branchService = new BranchService(branchFacade);
    }

    public EmployeeFacade getemployeeFacade(){return employeeFacade;}
    public ShiftFacade getshiftFacade(){return shiftFacade;}
    public BranchFacade getbranchFacade(){return branchFacade;}
    public EmployeeService getEmployeeService(){return employeeService;}
    public ShiftService getShiftService(){return shiftService;}
    public BranchService getBranchService(){return branchService;}
}