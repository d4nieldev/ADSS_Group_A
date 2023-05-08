package ServiceLayer.EmployeesLayer;

import BussinessLayer.EmployeeTransportFacade;
import BussinessLayer.EmployeesLayer.BranchFacade;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import BussinessLayer.EmployeesLayer.ShiftFacade;
import ServiceLayer.TransportLayer.DriverService;
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
    private TransportService transportServices;
    private DriverService driverService;
    private TruckService truckService;

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
        transportServices = new TransportService();
        driverService = new DriverService();
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
        return transportServices;
    };

    public DriverService getDriverService() {
        return driverService;
    };

    public TruckService getTruckService() {
        return truckService;
    };
}
