package BussinessLayer;

import java.time.LocalDate;
import java.util.LinkedList;

import BussinessLayer.EmployeesLayer.Driver;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import BussinessLayer.EmployeesLayer.ShiftFacade;

public class EmployeeTransportFacade {
    private EmployeeFacade employeeFacade;
    private ShiftFacade shiftFacade;
    
    // privates for transport moudle
    // ----------------------------------------------------

    public EmployeeTransportFacade(EmployeeFacade employeeFacade, ShiftFacade shiftFacade){
        this.employeeFacade = employeeFacade;
        this.shiftFacade = shiftFacade;
    }
    
    public String printDayTransports(int idEmployee, LocalDate date){
        String strTransports = "";
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        //Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintTransportsListAccess());
        // TODO - add to str all the transport in the date - transport moudle
        return strTransports;
    }

    public String printDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        //Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintAllEmployeesListAccess());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.printDayDriversPast(date);}
        else {return employeeFacade.printDayDriversFuture(date);}
    }

    public LinkedList<Driver> getDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        //Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employeeFacade.checkIfEmployeeAllowed(idEmployee, employeeFacade.getPrintAllEmployeesListAccess());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.getDayDriversPast(date);}
        else {return employeeFacade.getDayDriversFuture(date);}
    }

    public boolean checkstorekeeperInShift(String address, LocalDate date){
        return shiftFacade.checkstorekeeperInShift(address, date);
    }
}
