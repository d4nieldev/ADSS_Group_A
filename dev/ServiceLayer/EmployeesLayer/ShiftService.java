package ServiceLayer.EmployeesLayer;

import BussinessLayer.EmployeesLayer.*;

public class ShiftService {
    private ShiftFacade shiftFacade;

    public ShiftService(ShiftFacade shiftFacade){
        this.shiftFacade = shiftFacade;
    }

    public void addCancelation(int shiftId, int employeeId ,int itemId, int itemCode){
        shiftFacade.addCancelation(shiftId, employeeId, itemId, itemCode);
    }

    public String printFinalShift(int employeeId, int idShift){
        return shiftFacade.printFinalShift(employeeId, idShift);
    }
    
    public String missingStaffToRole(int employeeId, int shiftId){
        return shiftFacade.missingStaffToRole(employeeId, shiftId);
    }
}
