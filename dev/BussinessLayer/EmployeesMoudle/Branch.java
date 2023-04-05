package BussinessLayer.EmployeesMoudle;

import java.util.LinkedList;
import java.util.List;

public class Branch {
    int branchId;
    List<Employee> employees;
    List<Employee> notAllowEmployees;

    public Branch(){
    }

    public Branch(int branchId){
        this.branchId = branchId;
        this.employees = new LinkedList<>();
        this.notAllowEmployees = new LinkedList<>();
    }

    public void addEmployee(Employee employee){
        employees.add(employee);
    }



    //-------------------------------------Getters And Setters--------------------------------------------------------

    public int getBranchId(){
        return this.branchId;
    }
}
