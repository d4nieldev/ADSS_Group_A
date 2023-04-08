package BussinessLayer.EmployeesMoudle;
import java.util.HashMap;
import java.util.LinkedList;

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

    public String printFinalShift(int employeeId, int idShift){
        employeeController.checkLoggedIn(employeeId);
        employeeController.checkIfEmployeeAllowed(employeeId, employeeController.getPrintFinalShiftListAccess());
        String strPrint = "";
        Shift shift = getShift(idShift);
        if(!shift.getIsFinishSettingShift()){throw new Error("The shift is not finished yet for printing.");}
        strPrint += shift.toString();
        strPrint += shift.printFinalShift();
        return strPrint;
    }
    
    public void addConstraint(int shiftId, Employee employee, LinkedList<String> role) {
        getShift(shiftId).addConstraint(employee, role);
    }

    public void checkAssignFinalShift(int shiftId, HashMap<Employee, String> hrAssign){
        getShift(shiftId).checkAssignFinalShift(hrAssign);
    }

    public String missingStaffToRole(int employeeId, int shiftId){
        employeeController.checkLoggedIn(employeeId);
        employeeController.checkIfEmployeeAllowed(employeeId, employeeController.getMissingStaffToRoleListAccess());
        return getShift(shiftId).missingStaffToRole().toString();
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
