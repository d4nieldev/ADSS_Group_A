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

    public void approveFinalShift(int managerID, HashMap<Integer, String> hrAssigns){
        shiftController.approveFinalShift(managerID, hrAssigns);
    }
}
