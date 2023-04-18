package BussinessLayer.EmployeesLayer;
import java.util.LinkedList;

public class Branch {
    private int branchId;
    private String address;
    private LinkedList<Employee> originEmployees;
    private LinkedList<Employee> foreignEmployees;
    private LinkedList<Employee> notAllowEmployees;
    private LinkedList<Shift> shifts;

    public Branch(int branchId, String address){
        this.branchId = branchId;
        this.address = address;
        this.originEmployees = new LinkedList<>();
        this.foreignEmployees = new LinkedList<>();
        this.notAllowEmployees = new LinkedList<>();
        this.shifts = new LinkedList<>();
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

    public void removeEmployeeFromSystem(Employee employee){
        if(originEmployees.contains(employee)){originEmployees.remove(employee);}
        if(foreignEmployees.contains(employee)){foreignEmployees.remove(employee);}
        if(notAllowEmployees.contains(employee)){notAllowEmployees.remove(employee);}
    }

    public void addShift(Shift shift){
        shifts.add(shift);
    }

    public void checkShiftInBranch(Shift shift){
        if(!shifts.contains(shift)) {throw new Error("Found error: This shift is not in this branch.");}
    }
    
    public void checkEmployeeInBranch(Employee employee){
        if(!originEmployees.contains(employee) && !foreignEmployees.contains(employee)) {
            throw new Error("Found error: This employee is not assign to work in this branch.");
        }
        if(notAllowEmployees.contains(employee)) {
            throw new Error("Found error: This employee is not allowed to work in this branch.");
        }
    }

    //-------------------------------------Getters And Setters--------------------------------------------------------

    public int getBranchId(){ return this.branchId;}

    public LinkedList<Shift> getAllShifts(){return shifts;}
}
