package ServiceLayer.EmployeesMoudle;

import BussinessLayer.EmployeesMoudle.EmployeeController;

public class EmployeeService {
    private EmployeeController employeeController;

    public EmployeeService(){
        employeeController = new EmployeeController();
        employeeController.addEmployee();
    }

    public void logIn(int id, String password){
        employeeController.logIn(id, password);
    }

    public void logOut(int id){
        employeeController.logOut(id);
    }
}
