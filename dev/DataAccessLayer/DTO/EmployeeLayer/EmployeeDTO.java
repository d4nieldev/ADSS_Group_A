package DataAccessLayer.DTO.EmployeeLayer;

import java.time.LocalDate;
import java.util.LinkedList;

import BussinessLayer.EmployeesLayer.Shift;
import Misc.*;

public class EmployeeDTO {
	public String firstName;
	public String lastName;
	public int id;
	public String password;
	public int bankNum;
	public int bankBranch;
	public int bankAccount;
	public int salary;
	public int bonus;
	public LocalDate startDate;
	public License driverLicense;
	public LinkedList<String> roles;
	public boolean isLoggedIn;
	public LinkedList<Shift> historyShift;
	public int superBranch;
	public LinkedList<Integer> branchs;
	public String status;

	public EmployeeDTO(String firstName, String lastName, int id, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branch, String status){
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.password = password;
		this.bankNum = bankNum;
		this.bankBranch = bankBranch;
		this.bankAccount = bankAccount;
		this.salary = salary;
		this.bonus = bonus;
		this.startDate = startDate;
		this.driverLicense = driverLicense;
		roles = new LinkedList<>();
		roles.add(role);
		isLoggedIn = false;
		historyShift = new LinkedList<>();
		superBranch = branch;
		branchs = new LinkedList<>();
		this.status = status;
	}

    public int getId(){return id;}
}
