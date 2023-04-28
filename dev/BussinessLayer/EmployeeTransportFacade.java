package BussinessLayer;

import BussinessLayer.EmployeesLayer.EmployeeFacade;

public class EmployeeTransportFacade {
    private EmployeeFacade employeeFacade;
    // privates for starnsport moudle

    public EmployeeTransportFacade(EmployeeFacade employeeFacade){
        this.employeeFacade = employeeFacade;
    }
}
