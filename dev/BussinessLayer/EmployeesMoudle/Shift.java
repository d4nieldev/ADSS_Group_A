package BussinessLayer.EmployeesMoudle;
import java.util.Map;

import javax.swing.text.AbstractDocument.BranchElement;

import java.lang.management.ThreadInfo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

import Misc.*;


public class Shift{
    public int idShift;
    private int superBranchNumer;
    private LocalDate date;
    private ShiftTime time;
    private int startHour;
    private int endHour; 
    private int duration;
    private boolean finishSettingShift;
    private HashMap<Employee, String> constraints;
    private HashMap<String, Integer> numEmployeesForRole;
    private HashMap<String, Integer> helpMapForAssign;
    private HashMap<Employee, String> finalShift;
    private Dictionary<Integer, Integer> cancellations;

    public Shift(int idShift, int superBranchNumer, LocalDate date, int startHour, int endHour, ShiftTime time){
        this.idShift = idShift;
        this.superBranchNumer = superBranchNumer;
        this.date = date;
        this.time = time;
        this.startHour = startHour;
        this.endHour = endHour;
        this.duration = endHour - startHour;
        finishSettingShift = false;
        this.constraints = new HashMap<Employee, String>();
        this.numEmployeesForRole = new HashMap<String, Integer>();
        this.helpMapForAssign = new HashMap<String, Integer>();
        this.finalShift = new HashMap<Employee, String>();
        cancellations = new Hashtable<>();
    }

    public void addCancelation(Employee employee, int itemCode, int itemID){
        if(employee.getRoles().contains(Role.getRole("BRANCHMANAGER")) || finalShift.get(employee).equals(Role.getRole("SHIFTMANAGER"))){
            cancellations.put(itemCode, itemID);
        }
        throw new Error("This employee can not cancel an item. Only the shift manager or the super branch manager.");
    }

    public Map<Employee, String> getAllConstraints(){
        return constraints;
    }
    public void addConstraint(Employee employee, String role) {
        if(finishSettingShift){
            throw new Error("The shift is close to add constraint. Already have employees.");
        }
        constraints.put(employee, role);
    }

    public void addRole(String newRole, int numEmployees){
        if(numEmployeesForRole.containsKey(newRole)){
            throw new Error("This shift already contains this role. You can change the number of employees if you wish");
        }
        numEmployeesForRole.put(newRole, numEmployees);
    }
    
    // update number of employees in a cartain role in this shift
    public void updateNumToRole(String role, int updateNum){
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
    public void checkAssignFinalShift(HashMap<Employee, String> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            String currRole = hrAssign.get(currAssignEmployee);
            if(helpMapForAssign.get(currRole) == null){
                boolean foundRole = false;
                for (String checkRole : numEmployeesForRole.keySet()) {
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
    public void assignFinalShift(HashMap<Employee, String> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            finalShift.put(currAssignEmployee, hrAssign.get(currAssignEmployee));
            currAssignEmployee.addShift(this);
        }
        finishSettingShift = true;
    }

    public HashMap<String, Integer> missingStaffToRole(){
        HashMap<String, Integer> missingStaff = new HashMap<>();
        for ( String roleNeddedByHR : numEmployeesForRole.keySet()) {
            if(numEmployeesForRole.get(roleNeddedByHR).intValue() != helpMapForAssign.get(roleNeddedByHR).intValue()){
                missingStaff.put(roleNeddedByHR, numEmployeesForRole.get(roleNeddedByHR).intValue()-helpMapForAssign.get(roleNeddedByHR).intValue());
            }
        }
        return missingStaff;
    }

    public String printFinalShift(){
        return "Final Shift: " + finalShift.toString();
    }
    
    public String toString(){
		return "Shift ID: " + idShift + " , Super Branch Numer: " + superBranchNumer + " [date: " + date + ", time: " + time.toString() + 
        ", start hour: " + startHour + ", end hour: " + endHour  + ", duration: " + duration + "]";
    }
    
//-------------------------------------Getters And Setters--------------------------------------------------------
    public int getID(){return idShift;}
    public int getSuperBranhNumber(){return superBranchNumer;}
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
    public HashMap<Employee, String> getFinalShift(){return finalShift;}
    public Dictionary<Integer, Integer> getCancellations(){return cancellations;}
}