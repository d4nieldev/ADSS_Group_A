package BussinessLayer.EmployeesLayer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import DataAccessLayer.DAO.EmployeesLayer.BranchesDAO;
import Misc.*;

public class BranchFacade {
    private EmployeeFacade employeeFacade;
    private ShiftFacade shiftFacade;
    private LinkedList<Branch> branchs;
    private static int branchIdConuter = 0;
    private BranchesDAO branchesDAO;

    public BranchFacade(EmployeeFacade employeeFacade, ShiftFacade shiftFacade){
        this.employeeFacade = employeeFacade;
        this.shiftFacade = shiftFacade;
        branchs = new LinkedList<>();
        branchs.add(new Branch(0, "BGU", Location.SOUTH));
        branchesDAO = new BranchesDAO();
    }
    
    public void addBranch(int managerId, String address, Location location) {
        employeeFacade.checkHrManager(managerId);
        Branch newBranch = new Branch(branchIdConuter, address, location);
        branchs.add(newBranch);
        branchIdConuter++;
        branchesDAO.insert(newBranch.toDTO());
    }

    public void addNewEmployee(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, String role, 
    int branchId){
        // only HR manager
        employeeFacade.addEmployee(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary,
        InitializeBonus, startDate, tempsEmployment, role, branchId);
        Branch branch = getBranchById(branchId);
        Employee employee = employeeFacade.getEmployeeById(id);
        branch.addNewEmployee(employee);
        branchesDAO.addOriginEmployee(id, branchId);
    }

    public void addNewDriver(int managerId, String firstName, String lastName, int id, String password, int bankNum,
    int bankBranch, int bankAccount, int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, License driverLicense){
        // only HR manager
        employeeFacade.addDriver(managerId, firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary,
        InitializeBonus, startDate, tempsEmployment, driverLicense);
        Branch branch = getBranchById(0);
        Employee employee = employeeFacade.getEmployeeById(id);
        branch.addNewEmployee(employee);
    }
    
    public void addForeignEmployee(int managerId, int idEmployee, int branchId){
        employeeFacade.checkHrManager(managerId);  // only HR manager
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        Branch branch = getBranchById(branchId);
        employee.addBranch(branchId); // check not exists already here
        branch.addForeignEmployee(employee);
        branchesDAO.addForeignEmployee(idEmployee, branchId);
    }

    public void addNotAllowEmployees(int managerId, int idEmployee, int branchId){
        employeeFacade.checkHrManager(managerId);  // only HR manager
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        Branch branch = getBranchById(branchId);
        employee.removeBranch(branchId); // check not removed already here
        branch.addNotAllowEmployees(employee);
        branchesDAO.addNotAllowEmployee(idEmployee, branchId);
    }

    // delete/remove employee from the system.
    public void deleteEmployee(int managerId, int id){
        employeeFacade.checkHrManager(managerId);  // only HR manager
        Employee employeeToRemove = employeeFacade.getEmployeeById(id);
        for (int branchId : employeeToRemove.getAllBranches()) {
            getBranchById(branchId).removeEmployeeFromSystem(employeeToRemove, branchesDAO);
        }
        employeeFacade.deleteEmployee(id);
    }

    public void addShift(int managerId, int branchId, LocalDate date, int startHour, int endHour, ShiftTime time, HashMap<Integer, Integer> numEmployeesForRole){
        employeeFacade.checkHrManager(managerId);  // only HR manager
        int shiftID = shiftFacade.getShiftIdConuter();
        Branch branch = getBranchById(branchId);
        // check there is shift manager  in each shift
        if(!numEmployeesForRole.keySet().contains(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId())
            ||numEmployeesForRole.get(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId()) < 1 )
            throw new Error("You have to role at least one SHIFTMANAGER for each shift.");
        Shift newShift = new Shift(shiftID, branch, date, startHour, endHour, time, numEmployeesForRole);
        shiftFacade.addShift(newShift);
        branch.addShift(newShift);
    }
    
    // add constaint to shift
    public void addConstraint(int idBranch, int idEmployee, int idShift){
        // check list is not finishSettingShift
        Branch branch = getBranchById(idBranch);
        Shift shift = shiftFacade.getShift(idShift);
        branch.checkShiftInBranch(shift);
        if(shift.getIsFinishSettingShift()) {
            throw new Error("Cannot add constraints. This shift was approved by the manager.");
        }
        // check employee in branch
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        branch.checkEmployeeInBranch(employee);
        // check employee and role is not already in the constraints list in this shift
        // if not - add the  constraint to the shift
        shiftFacade.addConstraint(idShift, employee, employee.getRoles());
    }
    
    // remove constaint to shift
    public void removeConstraint(int idBranch, int idEmployee, int idShift){
        // check list is not finishSettingShift
        Branch branch = getBranchById(idBranch);
        Shift shift = shiftFacade.getShift(idShift);
        branch.checkShiftInBranch(shift);
        if(shift.getIsFinishSettingShift()) {
            throw new Error("Cannot remove constraints. This shift was approved by the manager.");
        }
        // check employee in branch
        Employee employee = employeeFacade.getEmployeeById(idEmployee);
        branch.checkEmployeeInBranch(employee);
        // if not - remove the  constraint to the shift
        shiftFacade.removeConstraint(idShift, employee);
    }

    // aprove function for the HR manager to a final shift
    public void approveFinalShift(int managerID, int shiftID, int branchID, HashMap<Integer, Integer> hrAssigns){
        employeeFacade.checkHrManager(managerID);
        Branch branch = getBranchById(branchID);
        Shift shift = shiftFacade.getShift(shiftID);
        branch.checkShiftInBranch(shift);
        HashMap<Employee, Integer> hashMapEmployees = new HashMap<>();
        // new HashMap from Integer and roles to Employees and roles
        for (Integer employeeId : hrAssigns.keySet()) {
            hashMapEmployees.put(employeeFacade.getEmployeeById(employeeId), hrAssigns.get(employeeId));
        }
        // check: have to be at least one SHIFTMANAGER in the shift
        checkShiftManagerExist(hashMapEmployees);
        for (Employee employee : hashMapEmployees.keySet()) {
            // check: exist branch for all employees
            branch.checkEmployeeInBranch(employee);
            // check: no employee have a shift on the same day
            employeeFacade.checkShiftInDate(employee.getId(), shift.getDate());
            // check: all the role are existing in the employees that needed
            employeeFacade.checkRoleInEmployee(employee.getId(), hrAssigns.get(employee.getId()));
        }
        // check: no over employees then needed AND save the final shift
        shiftFacade.checkAssignFinalShift(managerID, shift, hashMapEmployees);
    }

    // function for printing all the shift that an employee can apply to work on that day, according to branches
    public String printAvailableShiftForEmployee(int employeeId, LocalDate date){
        String res = "";
        employeeFacade.checkEmployee(employeeId);
        employeeFacade.checkLoggedIn(employeeId);
        LinkedList<Integer> branchesEmployee = employeeFacade.getEmployeeById(employeeId).getAllBranches();
        LinkedList<Shift> shiftsOnDate = shiftFacade.getShiftsByDate(date);
        for (Integer branchId : branchesEmployee) {
            for (Shift shiftOnDate : shiftsOnDate) {
                if(shiftOnDate.getSuperBranchId() == branchId && !shiftOnDate.getIsFinishSettingShift()) 
                    {res += shiftOnDate.toString() + "\n";}
            }
        }
        return res;
    }

    public String printAllBranches(int managerId){
        String res = "";
        employeeFacade.checkEmployee(managerId);
        employeeFacade.checkLoggedIn(managerId);
        for (Branch branch : branchs) {
            res += branch.toString() + "\n";
        }
        return res;
    }

    //-------------------------------------Help Functions--------------------------------------------------------
    private Branch getBranchById(int branchId){
        for (Branch branch : branchs) {
            if (branch.getBranchId() == branchId)
                return branch;
        }
        throw new Error("The branch id " + branchId + "is not in the system. Please try again");
    }

    private void checkShiftManagerExist( HashMap<Employee, Integer> hashMapEmployees){
        boolean foundManager = false;
        for (Employee employee : hashMapEmployees.keySet()) {
            if(hashMapEmployees.get(employee).equals(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId())){
                foundManager = true;
                break;
            }
        }
        if(!foundManager){throw new Error("A shift have to contain at least one Shift Manager");}
    }
}
