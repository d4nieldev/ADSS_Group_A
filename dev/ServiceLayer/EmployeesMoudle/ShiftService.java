package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.EmployeeController;
import BussinessLayer.EmployeesMoudle.ShiftController;

public class ShiftService {
    private EmployeeController employeeController;
    private ShiftController shiftController;

    public ShiftService(EmployeeController employeeController, ShiftController shiftController){
        this.employeeController = employeeController;
        this.shiftController = shiftController;
    }
}
