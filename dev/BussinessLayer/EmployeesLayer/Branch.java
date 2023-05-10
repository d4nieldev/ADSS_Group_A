package BussinessLayer.EmployeesLayer;
import java.util.LinkedList;

import DataAccessLayer.DAO.EmployeesLayer.BranchesDAO;
import DataAccessLayer.DTO.EmployeeLayer.BranchDTO;
import Misc.*;

public class Branch {
    private int branchId;
    private String address;
    private Location location;
    private LinkedList<Employee> originEmployees;
    private LinkedList<Employee> foreignEmployees;
    private LinkedList<Employee> notAllowEmployees;

    public Branch(int branchId, String address, Location location){
        this.branchId = branchId;
        this.address = address;
        this.location = location;
        this.originEmployees = new LinkedList<>();
        this.foreignEmployees = new LinkedList<>();
        this.notAllowEmployees = new LinkedList<>();
    }

    public Branch(BranchDTO branchDTO, LinkedList<Employee> originEmployees, LinkedList<Employee> foreignEmployees, LinkedList<Employee> notAlloEmployees) {
        this.branchId = branchDTO.branchId;
        this.address = branchDTO.address;
        this.location = branchDTO.location;
        this.originEmployees = originEmployees;
        this.foreignEmployees = foreignEmployees;
        this.notAllowEmployees = notAlloEmployees;
    }

    public void addNewEmployee(Employee employee){
        originEmployees.add(employee);
    }

    public void addForeignEmployee(Employee employee){
        foreignEmployees.add(employee);
    }
    
    public void addNotAllowEmployees(Employee employee){
        notAllowEmployees.add(employee);
    }

    public void removeEmployeeFromSystem(Employee employee, BranchesDAO branchesDAO){
        if(originEmployees.contains(employee))
        {originEmployees.remove(employee); branchesDAO.removeOriginEmployee(employee.getId(), branchId);}
        if(foreignEmployees.contains(employee))
        {foreignEmployees.remove(employee); branchesDAO.removeForeignEmployee(employee.getId(), branchId);}
        if(notAllowEmployees.contains(employee))
        {notAllowEmployees.remove(employee); branchesDAO.removeNotAllowEmployee(employee.getId(), branchId);}
    }
    
    public void checkEmployeeInBranch(Employee employee){
        if(!originEmployees.contains(employee) && !foreignEmployees.contains(employee)) {
            throw new Error("Found error: This employee is not assign to work in this branch.");
        }
        if(notAllowEmployees.contains(employee)) {
            throw new Error("Found error: This employee is not allowed to work in this branch.");
        }
    }

    public BranchDTO toDTO() {
        LinkedList<Integer> originEmployeesToDTO = new LinkedList<>();
        for (Employee employee : originEmployees) {
            originEmployeesToDTO.add(employee.getId());
        }
        LinkedList<Integer> foreignEmployeesToDTO = new LinkedList<>();
        for (Employee employee : foreignEmployees) {
            foreignEmployeesToDTO.add(employee.getId());
        }
        LinkedList<Integer> notAllowEmployeesToDTO = new LinkedList<>();
        for (Employee employee : notAllowEmployees) {
            notAllowEmployeesToDTO.add(employee.getId());
        }

        return new BranchDTO(this.branchId, this.address, this.location.toString(), originEmployeesToDTO,
         foreignEmployeesToDTO, notAllowEmployeesToDTO);
    }

    public String ToString(){
        return "Branch ID: " + branchId + "\n" +
        "Branch Address: " + address + "\n" +
        "Branch Location: " + location.toString() + "\n" +
        "Origin Employees: " + originEmployees.toString() + "\n" +
        "Foreign Employees: " + foreignEmployees.toString() + "\n" +
        "Not Allow Employees: " + notAllowEmployees.toString() + "\n";
    }

    //-------------------------------------Getters And Setters--------------------------------------------------------

    public int getBranchId(){ return this.branchId; }
    public String getBranchAddress(){ return this.address; }
    public Location getBranchLocation(){ return this.location; }
}
