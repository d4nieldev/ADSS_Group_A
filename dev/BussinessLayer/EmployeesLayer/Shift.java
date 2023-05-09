package BussinessLayer.EmployeesLayer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import org.w3c.dom.stylesheets.LinkStyle;

import DataAccessLayer.DTO.EmployeeLayer.ShiftDTO;
import Misc.*;


public class Shift{
    public int idShift;
    private Branch superBranch;
    private LocalDate date;
    private ShiftTime time;
    private int startHour;
    private int endHour; 
    private int duration;
    private boolean finishSettingShift;
    private HashMap<Employee, LinkedList<Integer>> constraints;
    private HashMap<Integer, Integer> numEmployeesForRole;
    private HashMap<Integer, Integer> helpMapForAssign;
    private HashMap<Employee, Integer> finalShift;
    private HashMap<Integer, LinkedList<Integer>> cancellations;
    private LinkedList<Driver> driversInShift;

    public Shift(int idShift, Branch superBranch, LocalDate date, int startHour, int endHour, ShiftTime time, HashMap<Integer, Integer> numEmployeesForRole){
        this.idShift = idShift;
        this.superBranch = superBranch;
        this.date = date;
        this.time = time;
        this.startHour = startHour;
        this.endHour = endHour;
        this.duration = endHour - startHour;
        finishSettingShift = false;
        this.constraints = new HashMap<Employee, LinkedList<Integer>>();
        this.numEmployeesForRole = numEmployeesForRole;
        this.helpMapForAssign = new HashMap<Integer, Integer>();
        this.finalShift = new HashMap<Employee, Integer>();
        cancellations = new HashMap<Integer, LinkedList<Integer>>();
        driversInShift = new LinkedList<>();
    }

    public void addCancelation(Employee employee, int itemCode, int itemID){
        if(cancellations.containsKey(itemCode)){(cancellations.get(itemCode)).add(itemID);}
        else{
            LinkedList<Integer> listItemsID = new LinkedList<>();
            listItemsID.add(itemID);
            cancellations.put(itemCode, listItemsID);
        }
    }

    public void addConstraint(Employee employee, LinkedList<Integer> role) {
        if(constraints.containsKey(employee)){
            throw new Error("The employee already sign to this sift in the constraints list.");
        }
        constraints.put(employee, role);
    }

    public void removeConstraint(Employee employee) {
        if(constraints.containsKey(employee)){
            throw new Error("The employee is not sign to this sift in the constraints list.");
        }
        constraints.remove(employee);
    }

    public void addDriver(Driver driver){driversInShift.add(driver);}

    public void addRole(Integer newRole, int numEmployees){
        if(numEmployeesForRole.containsKey(newRole)){
            throw new Error("This shift already contains this role. You can change the number of employees if you wish");
        }
        numEmployeesForRole.put(newRole, numEmployees);
    }
    
    // update number of employees in a cartain role in this shift
    public void updateNumToRole(Integer role, int updateNum){
        int counterCurr = 0;
        for (Employee employee : finalShift.keySet()) {
            if(finalShift.get(employee).equals(role)){
                counterCurr++;
            }
        }
        if(counterCurr > updateNum){
            throw new Error("The new number of employees for the role " + role.toString() + " have to be bigger than " + counterCurr + " because there are " + counterCurr + " emlpolyees in this role in this shift already.");
        }
        numEmployeesForRole.replace(role, updateNum);
    }

    // HR manager done checking the employees in the shift in the controller
    // System check if any role is missing and notify the HR manager
    // set the final shift
    public void checkAssignFinalShift(HashMap<Employee, Integer> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            Integer currRole = hrAssign.get(currAssignEmployee);
            if(helpMapForAssign.get(currRole) == null){
                boolean foundRole = false;
                for (Integer checkRole : numEmployeesForRole.keySet()) {
                    if(checkRole.equals(currRole)){
                        foundRole = true;
                        break;
                    }
                }
                if(!foundRole){
                    throw new Error("The role " + currRole.toString() + " is not needed in this shift. Please add this role or try again without this role.");
                }
                helpMapForAssign.put(currRole, 1);
            }
            if(helpMapForAssign.get(currRole) == numEmployeesForRole.get(currRole)){
                throw new Error("The role " + currRole.toString() + " is full (" + numEmployeesForRole.get(currRole) + " employees already) for this shift. Change the number of employees needed or remove some srom this shift.");
            }
            helpMapForAssign.replace(currRole, helpMapForAssign.get(currRole), helpMapForAssign.get(currRole).intValue() + 1);
        }
    }
    
    // set the final shift
    public void assignFinalShift(HashMap<Employee, Integer> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            finalShift.put(currAssignEmployee, hrAssign.get(currAssignEmployee));
            currAssignEmployee.addShift(this);
        }
        finishSettingShift = true;
    }

    public HashMap<Integer, Integer> missingStaffToRole(){
        HashMap<Integer, Integer> missingStaff = new HashMap<>();
        for ( Integer roleNeddedByHR : numEmployeesForRole.keySet()) {
            if(numEmployeesForRole.get(roleNeddedByHR).intValue() != helpMapForAssign.get(roleNeddedByHR).intValue()){
                missingStaff.put(roleNeddedByHR, numEmployeesForRole.get(roleNeddedByHR).intValue()-helpMapForAssign.get(roleNeddedByHR).intValue());
            }
        }
        return missingStaff;
    }

    public boolean isShiftContainStorekeeper(int roleId){
        if(!finishSettingShift) {return false;}
        for (Employee employeeInShift : finalShift.keySet()) {
            if(finalShift.get(employeeInShift) == (roleId)) {return true;}
        }
        return false;
    }

    public String printFinalShift(){
        return "Final Shift: " + finalShift.toString();
    }

    public String printConstraints(){
        return "Constraints: " + constraints.toString();
    }
    
    public String printCancelations(){
        return "Cancelations: " + cancellations.toString();
    }
    
    public String printDriversInShift(){
        return "Drivers In Shift: " + driversInShift.toString();
    }
    
    public String toString(){
		return "Shift ID: " + idShift + " , Super Branch Id: " + superBranch.getBranchId() + " [date: " + date + ", time: " + time.toString() + 
        ", start hour: " + startHour + ", end hour: " + endHour  + ", duration: " + duration + "]";
    }
    
    public ShiftDTO toDTO() {
        HashMap<Integer, LinkedList<Integer>> constraintsToDTO = new HashMap<>();
        for (Employee employeeConstraints : constraints.keySet()) {
            constraintsToDTO.putIfAbsent(employeeConstraints.getId(), constraints.get(employeeConstraints));
        }
        HashMap<Integer, Integer> finalShiftToDTO = new HashMap<>();
        for (Employee employeeFinalShift : constraints.keySet()) {
            finalShiftToDTO.putIfAbsent(employeeFinalShift.getId(), finalShift.get(employeeFinalShift));
        }
        LinkedList<Integer> driversInShiftToDTO = new LinkedList<>();
        for (Driver driver : driversInShift) {
            driversInShiftToDTO.add(driver.getId());
        }

        return new ShiftDTO(this.idShift, this.superBranch.getBranchId(), this.date, this.time, this.startHour, this.endHour,
		this.duration, this.finishSettingShift, constraintsToDTO, this.numEmployeesForRole, finalShiftToDTO,
		this.cancellations, driversInShiftToDTO);
    }
    
//-------------------------------------Getters And Setters--------------------------------------------------------
    public int getID(){return idShift;}
    public int getSuperBranchId(){return superBranch.getBranchId();}
    public String getSuperBranchAddress(){return superBranch.getBranchAddress();}
    public String getLocation(){return superBranch.getBranchLocation().toString(); }
    public LocalDate getDate(){return date;}
    public ShiftTime getShiftTime(){return time;}
    public void setStartHour(int newStartHour){
        if(this.endHour <= newStartHour){throw new Error("The Start hour have to be before the end hour.");}
    this.startHour = newStartHour;}
    public void setEndHour(int newEndHour){
        if(this.startHour >= newEndHour){throw new Error("The end hour have to be after the start hour.");}
    this.endHour = newEndHour;}
    public int getDuration(){return duration;}
    public boolean getIsFinishSettingShift(){return finishSettingShift;}
    public HashMap<Employee, LinkedList<Integer>> getConstraints(){return constraints;}
    public HashMap<Employee, Integer> getFinalShift(){return finalShift;}
    public HashMap<Integer, LinkedList<Integer>> getCancellations(){return cancellations;}
    public LinkedList<Driver> getDriversInShift(){return driversInShift;}
}