package DataAccessLayer.EmployeesMoudle.DTO;

import java.time.LocalDate;
import java.util.LinkedList;

import BussinessLayer.EmployeesMoudle.Shift;
import Misc.*;

public class EmployeeDTO {
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
	private LinkedList<String> roles;
	private boolean isLoggedIn;
	private LinkedList<Shift> historyShift;
	private int superBranch;
	private LinkedList<Integer> branchs;
	private String status;

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
