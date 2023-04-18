package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.*;

public class ShiftService {
    private ShiftController shiftController;

    public ShiftService(ShiftController shiftController){
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
