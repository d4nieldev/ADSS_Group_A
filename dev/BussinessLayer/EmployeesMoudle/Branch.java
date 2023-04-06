package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;

public class Branch {
    int branchId;
    String address;
    List<Employee> originEmployees;
    List<Employee> foreignEmployees;
    List<Employee> notAllowEmployees;

    public Branch(){
    }

    public Branch(int branchId, String address){
        this.branchId = branchId;
        this.address = address;
        this.originEmployees = new LinkedList<>();
        this.foreignEmployees = new LinkedList<>();
        this.notAllowEmployees = new LinkedList<>();
    }

    public void addNewEmployee(Employee employee){
        originEmployees.add(employee);
    }

    public void addForeignEmployee(Employee employee){
        foreignEmployees.add(employee);
    }

    //-------------------------------------Getters And Setters--------------------------------------------------------

    public int getBranchId(){
        return this.branchId;
    }
}
