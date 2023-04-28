package BussinessLayer;

import java.io.EOFException;
import java.time.LocalDate;
import java.util.LinkedList;

import BussinessLayer.EmployeesLayer.Driver;
import BussinessLayer.EmployeesLayer.Employee;
import BussinessLayer.EmployeesLayer.EmployeeFacade;

public class EmployeeTransportFacade {
    private EmployeeFacade employeeFacade;
    
    // privates for transport moudle
    // ----------------------------------------------------

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

    public String printDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintAllEmployeesListAccess());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.printDayDriversPast(date);}
        else {return employeeFacade.printDayDriversFuture(date);}
    }

    public LinkedList<Driver> getDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintAllEmployeesListAccess());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.getDayDriversPast(date);}
        else {return employeeFacade.getDayDriversFuture(date);}
    }
}
