package BussinessLayer.EmployeesMoudle;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.LayoutStyle;

import Misc.License;
import Misc.Role;

public class Employee{
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

	public Employee(String firstName, String lastName, int id, String password, int bankNum,
	int bankBranch, int bankAccount, int salary, int bonus, LocalDate startDate, License driverLicense, Role role){
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
	}

	//Getters And Setters
	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getPassword(){
		return password;
	}
	
	public boolean getIsLoggedIn(){
		return isLoggedIn;
	}

	public List<Role> getRoles(){
		return roles;
	}
	
	public void SetIsLoggedInToTrue(){
		isLoggedIn = true;
	}

	public void SetIsLoggedInToFalse(){
		isLoggedIn = false;
	}
	//Getters And Setters

	public String toString(){
		return "Employee Name: " + firstName + " " + lastName + " [id: " + id + ", bank number: " + bankNum  + ", salary" + salary + ", start date: " + startDate + ", roles: " + roles.toString() + "]";
	}
}