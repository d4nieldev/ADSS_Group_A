package BussinessLayer.EmployeesMoudle;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.swing.LayoutStyle;

import Misc.License;
import Misc.Role;

class Employee{
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

	public Employee(){
		id = 12345;
		password = "12345";
		isLoggedIn = false;
		firstName = "Tal";
		lastName = "Koren";
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
	
	public void SetIsLoggedInToTrue(){
		isLoggedIn = true;
	}
	//Getters And Setters

	public String toString(){
		return "Employee Name: " + firstName + " " + lastName + " [id: " + id + ", bank number: " + bankNum  + ", salary" + salary + ", start date: " + startDate + ", roles: " + roles.toString() + "]";
	}
}