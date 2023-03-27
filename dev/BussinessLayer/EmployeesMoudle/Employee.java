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
	private String username;
	private String password;
	private int bankNum;
	private int bankBranch;
	private int bankAccount;
	private int salary;
	private int bonus;
	private LocalDate startDate;
	private License driverLicense;
	private List<Role> roles;

	public Employee(){}

	public String toString(){
		return "Employee Name: " + firstName + " " + lastName + " [id: " + id + ", bank number: " + bankNum  + ", salary" + salary + ", start date: " + startDate + ", roles: " + roles.toString() + "]";
	}
}