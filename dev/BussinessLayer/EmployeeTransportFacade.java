package BussinessLayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import BussinessLayer.EmployeesLayer.Driver;
import BussinessLayer.EmployeesLayer.Employee;
import BussinessLayer.EmployeesLayer.EmployeeFacade;
import BussinessLayer.EmployeesLayer.Shift;
import BussinessLayer.EmployeesLayer.ShiftFacade;
import BussinessLayer.TransPortLayer.Delivery;
import BussinessLayer.TransPortLayer.TransportFacade;
import BussinessLayer.TransPortLayer.TruckFacade;

public class EmployeeTransportFacade {
    private EmployeeFacade employeeFacade;
    private ShiftFacade shiftFacade;



    // privates for transport moudle
    private TransportFacade transportFacade =TransportFacade.getInstance();
    private TruckFacade truckFacade = TruckFacade.getInstance();

    public EmployeeTransportFacade(EmployeeFacade employeeFacade, ShiftFacade shiftFacade){
        this.employeeFacade = employeeFacade;
        this.shiftFacade = shiftFacade;
    }



    public String printDayTransports(int idEmployee, LocalDate date){
        String strTransports = "";
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("TRANSPOERMANAGER").getId());
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("BRANCHMANAGER").getId());
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId());
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("STOREKEEPER").getId());
        strTransports= transportFacade.getTransportsByDate(date);
        return strTransports;
    }

    public String printDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("TRANSPOERMANAGER").getId());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.printDayDriversPast(date);}
        else {return employeeFacade.printDayDriversFuture(date);}
    }

    public LinkedList<Driver> getDayDrivers(int idEmployee, LocalDate date){
        employeeFacade.checkLoggedIn(idEmployee);
        employeeFacade.checkEmployee(idEmployee);
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        employee.checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("TRANSPOERMANAGER").getId());
        if(date.compareTo(LocalDate.now()) <= 0) {return employeeFacade.getDayDriversPast(date);}
        else {return employeeFacade.getDayDriversFuture(date);}
    }

    public boolean checkstorekeeperInShift(String address, LocalDate date){
        return shiftFacade.checkstorekeeperInShift(address, date);
    }
    public void createTransports(List<Delivery> deliveries)
    {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("What date would you like to make transports? (dd/MM/yyyy)");
        String input = scanner.nextLine();
        LocalDate transportDate = LocalDate.parse(input, formatter);

        transportFacade.letTheUserMatch(deliveries,getDayDrivers(2,transportDate),truckFacade.getAvailableTrucks());
        // TODO - insert driver to shift - AddDriverToShift(int driverID, int shiftID)
    }

    public void AddDriverToShift(int driverID, int shiftID){
        shiftFacade.AddDriverToShift(employeeFacade.getDriverById(driverID), shiftID);
    }

    public Driver getDriverById(int driverId)
    {
        return employeeFacade.getDriverById(driverId);
    }
}
