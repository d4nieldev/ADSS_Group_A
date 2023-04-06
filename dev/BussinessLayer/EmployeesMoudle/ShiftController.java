package BussinessLayer.EmployeesMoudle;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import Misc.Role;
import Misc.ShiftTime;

public class ShiftController {
    private EmployeeController employeeController;
    private LinkedList<Shift> shifts;
    private static int shiftIdConuter = 0;

    // constructor
    public ShiftController(EmployeeController employeeController){
        this.employeeController = employeeController;
        shifts = new LinkedList<Shift>();
    }

    public void addShift(Shift newShift){
        shifts.add(newShift);
        shiftIdConuter++;
    }
    
    // add cancelation to shift
    public void addCancelation(int shiftId, int employeeId ,int itemId, int itemCode){
        Shift shift = getShift(shiftId);
        Employee employee = employeeController.getEmployeeById(employeeId);
        employeeController.checkLoggedIn(employeeId);
        employeeController.checkIfEmployeeAllowed(employeeId, employeeController.getAddCancelationListAccess());
        shift.addCancelation(employee, itemCode, itemId);
    }

    // aprove function for the HR manager to a final shift
    public void approveFinalShift(int managerID, HashMap<Integer, String> hrAssigns){
        // new HashMap from Integer and roles to Employees and roles
        // check: exist super branch for all users
        // check: no employee have a shift on the same day
        // check: all the role are existing in the employees that needed
        // check: no over employees then needed
        // needs to send a message if some role are missing people, and how mach -> function: missingStaffToRole
    }

    public String printFinalShift(int idShift){
        String strPrint = "";
        Shift shift = getShift(idShift);
        if(!shift.getIsFinishSettingShift()){throw new Error("The shift is not finished yet for printing.");}
        strPrint += shift.toString();
        strPrint += shift.printFinalShift();
        return strPrint;
    }

//-------------------------------------Getters And Setters--------------------------------------------------------

    // throw Error if there is not shift with this ID
    public Shift getShift(int id){
        for (Shift shift : shifts) {
            if(shift.getID() == id){return shift;}
        }
        throw new Error("No such a shift in this system by the id " + id);
    }

    public int getShiftIdConuter(){return shiftIdConuter;}

//-------------------------------------Help Functions--------------------------------------------------------

}
