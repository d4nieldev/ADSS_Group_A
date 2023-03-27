package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.EmployeeController;

public class EmployeeService {
    private EmployeeController employeeController;

    public void EmployeeController(){
        employeeController = new EmployeeController();
        employeeController.addEmployee();
    }

    public void logIn(int id, String password) {
        employeeController.logIn(id, password);
    }

    
}
