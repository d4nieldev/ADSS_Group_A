package ServiceLayer.EmployeesLayer;

import BussinessLayer.EmployeeTransportFacade;
import BussinessLayer.EmployeesLayer.BranchFacade;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import BussinessLayer.EmployeesLayer.ShiftFacade;
import ServiceLayer.TransportLayer.TransportService;
import ServiceLayer.TransportLayer.TruckService;

public class serviceFactory {
    ////////// HR/////////////////
    private EmployeeFacade employeeFacade;
    private ShiftFacade shiftFacade;
    private BranchFacade branchFacade;
    private EmployeeTransportFacade employeeTransportFacade;
    private EmployeeService employeeService;
    private ShiftService shiftService;
    private BranchService branchService;
    ///////// Transport/////////////
    private TruckService truckService;
    private TransportService transportService;

    public serviceFactory() {
        ////////// HR/////////////////
        employeeFacade = new EmployeeFacade();
        shiftFacade = new ShiftFacade(employeeFacade);
        branchFacade = new BranchFacade(employeeFacade, shiftFacade);
        employeeTransportFacade = new EmployeeTransportFacade(employeeFacade, shiftFacade);
        employeeService = new EmployeeService(employeeFacade, employeeTransportFacade);
        shiftService = new ShiftService(shiftFacade);
        branchService = new BranchService(branchFacade);
        ///////// Transport/////////////
        transportService = new TransportService(employeeTransportFacade);
        truckService = new TruckService();
    }

    ////////// HR/////////////////
    public EmployeeFacade getemployeeFacade() {
        return employeeFacade;
    }

    public ShiftFacade getshiftFacade() {
        return shiftFacade;
    }

    public BranchFacade getbranchFacade() {
        return branchFacade;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public ShiftService getShiftService() {
        return shiftService;
    }

    public BranchService getBranchService() {
        return branchService;
    }

    ///////// Transport/////////////
    public TransportService getTransportService() {
        return transportService;
    }

    public TruckService getTruckService() {
        return truckService;
    }
}