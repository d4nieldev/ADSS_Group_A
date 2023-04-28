package BussinessLayer;

import java.io.EOFException;
import java.time.LocalDate;

import BussinessLayer.EmployeesLayer.Employee;
import BussinessLayer.EmployeesLayer.EmployeeFacade;

public class EmployeeTransportFacade {
    private EmployeeFacade employeeFacade;
    // privates for starnsport moudle

    public EmployeeTransportFacade(EmployeeFacade employeeFacade){
        this.employeeFacade = employeeFacade;
    }
    
    public String printDayTransports(int idEmployee, LocalDate date){
        String strTransports = "";
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintTransportsListAccess());
        // TODO - add to str all the transport in the date - transport moudle
        return strTransports;
    }
}
