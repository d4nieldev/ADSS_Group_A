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
    private String firstName;
	private String lastName;
	private int id;
	private String password;
	private int bankNum;
	private int bankBranch;
	private int bankAccount;
	private int salary;
	private int bonus;
	private LocalDate startDate;
	private License driverLicense;
	private List<Role> roles;
	private boolean isLoggedIn;
    public void addEmployee(){
        employeeController.addEmployee(String firstName, String lastName, int id, String password, int bankNum,
        int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, String role);
    }
}
