package ServiceLayer.EmployeesMoudle;

import java.util.HashMap;

import BussinessLayer.EmployeesMoudle.EmployeeController;
import BussinessLayer.EmployeesMoudle.ShiftController;

public class ShiftService {
    private EmployeeController employeeController;
    private ShiftController shiftController;

    public ShiftService(EmployeeController employeeController, ShiftController shiftController){
        this.employeeController = employeeController;
        this.shiftController = shiftController;
    }

    public void addCancelation(int shiftId, int employeeId ,int itemId, int itemCode){
        shiftController.addCancelation(shiftId, employeeId, itemId, itemCode);
    }

    public String printFinalShift(int employeeId, int idShift){
        return shiftController.printFinalShift(employeeId, idShift);
    }
    
    public String missingStaffToRole(int employeeId, int shiftId){
        return shiftController.missingStaffToRole(employeeId, shiftId);
    }
}
