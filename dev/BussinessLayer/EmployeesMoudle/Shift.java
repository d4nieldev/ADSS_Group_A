package BussinessLayer.EmployeesMoudle;
import java.util.Map;

import javax.swing.text.AbstractDocument.BranchElement;

import java.time.LocalDate;
import java.util.HashMap;

import Misc.*;


public class Shift{
    public int idShift;
    private int superBranchNumer;
    private LocalDate date;
    private ShiftTime time;
    private boolean finishSettingShift;
    private HashMap<Employee, Role> constraints;
    private HashMap<Role, Integer> numEmployeesForRole;
    private HashMap<Role, Integer> helpMapForAssign;
    private HashMap<Employee, Role> finalShift;
    // private List<int> cancellations;

    public Shift(int idShift, int superBranchNumer, LocalDate date, ShiftTime time){
        this.idShift = idShift;
        this.superBranchNumer = superBranchNumer;
        this.date = date;
        this.time = time;
        this.constraints = new HashMap<Employee, Role>();
        this.numEmployeesForRole = new HashMap<Role, Integer>();
        this.helpMapForAssign = new HashMap<Role, Integer>();
        this.finalShift = new HashMap<Employee, Role>();
        finishSettingShift = false;
    }

    public Map<Employee, Role> getAllConstraints(){
        return constraints;
    }
    public void addConstraint(Employee employee, Role role) {
        if(finishSettingShift){
            throw new Error("The shift is close to add constraint. Already have employees.");
        }
        constraints.put(employee, role);
    }

    public void addRole(Role newRole, int numEmployees){
        if(numEmployeesForRole.containsKey(newRole)){
            throw new Error("This shift already contains this role. You can change the number of employees if you wish");
        }
        numEmployeesForRole.put(newRole, numEmployees);
    }
    
    // update number of employees in a cartain role in this shift
    public void updateNumToRole(Role role, int updateNum){
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
    public void checkAssignFinalShift(HashMap<Employee, Role> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            Role currRole = hrAssign.get(currAssignEmployee);
            if(helpMapForAssign.get(currRole) == null){
                boolean foundRole = false;
                for (Role checkRole : numEmployeesForRole.keySet()) {
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
    public void assignFinalShift(HashMap<Employee, Role> hrAssign){
        for (Employee currAssignEmployee : hrAssign.keySet()) {
            finalShift.put(currAssignEmployee, hrAssign.get(currAssignEmployee));
        }
        finishSettingShift = true;
    }

    public HashMap<Employee, Role> getFinalShift(){
        return finalShift;
    }

    public int getID(){return idShift;}

}