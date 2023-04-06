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
	private LinkedList<String> roles;
	private boolean isLoggedIn;
	private LinkedList<Shift> historyShift;
	private int superBranch;
	private LinkedList<Integer> branchs;

	public Employee(String firstName, String lastName, int id, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int bonus, LocalDate startDate, License driverLicense, String role, int branch){
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
	}

	// add role if not exsist to employee
	public void addRole(String roleToAdd){
		if(roles.contains(roleToAdd)){
			throw new Error("This employee already have this role.");
		}
		roles.remove(roleToAdd);
	}

	// remove role if exsist to employee
	public void removeRole(String roleToRemove){
		if(!roles.contains(roleToRemove)){
			throw new Error("This employee does not have this role. Can not be removed.");
		}
		roles.remove(roleToRemove);
	}

	// add shift to history shift in employee
	public void addShift(Shift shift){
		historyShift.push(shift);
	}
	
	public void addBranch(int branchId){
		if(branchs.contains(branchId) || superBranch == branchId){
			throw new Error("This employee is already able to work in branch number " + branchId);
		}
		branchs.add(branchId);
	}

	public boolean checkInBranch(int branchId){
		if(branchs.contains(branchId) || superBranch == branchId){return true;}
		return false;
	}

	// check if the employee have a shift in some date
	// return dalse if the employee is avalible in this date = does not have a shift on that day
	public boolean checkShiftInDate(LocalDate date){
		for (Shift shift : historyShift) {
			if(shift.getDate().equals(date)){
				return true;
			}
		}
		return false;
	}

	public void checkRoleInEmployee(String role) {
		if(!roles.contains(role)){
			throw new Error("The employee " + getId() + " does not have the role " + role);
		}
	}

	// calculate the salary for month
	public int sumSalaryToMonth(int month, int year){
		int countHours = 0;
		for (Shift shift : historyShift) {
			if(shift.getDate().getDayOfMonth() == month && shift.getDate().getYear() == year){
				countHours += shift.getDuration();
			}
		}
		return countHours * salary;
	}

	public void resetBonus(){bonus = 0;}

	public String toString(){
		return "Employee Name: " + firstName + " " + lastName + " [id: " + id + ", bank number: " + bankNum  + ", salary: " + salary 
		+ ", start date: " + startDate + ", roles: " + roles.toString() + "]";
	}

//-------------------------------------Getters And Setters--------------------------------------------------------
	public int getId(){return id;}
	public String getFirstName(){return firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}
	public String getLastName(){return lastName;}
	public void setLastName(String lastName){this.lastName = lastName;}
	public String getPassword(){return password;}
	public void setPassword(String password){this.password = password;}
	public int getBankNum(){return bankNum;}
	public void setBankNum(int bankNum){ this.bankNum = bankNum;}
	public int getBankBranch(){return bankBranch;}
	public void setBankBranch(int bankBranch){this.bankBranch = bankBranch;}
	public int getBankAccount(){return bankAccount;}
	public void setBankAccount(int bankAccount){this.bankAccount = bankAccount;}
	public int getSalary(){return salary;}
	public void setSalary(int salary){this.salary = salary;}
	public int getBonus(){return bonus;}
	public void setBonus(int bonus){this.bonus = bonus;}
	public LocalDate getStartDate(){return startDate;}
	public void setStartDate(LocalDate date){this.startDate = date;}
	public License getDriverLicense(){return driverLicense;}
	public void setDriverLicense(License driverLicense){this.driverLicense = driverLicense;}
	public LinkedList<String> getRoles(){return roles;}
	public boolean getIsLoggedIn(){return isLoggedIn;}
	public void SetIsLoggedInToTrue(){isLoggedIn = true;}
	public void SetIsLoggedInToFalse(){isLoggedIn = false;}
	public LinkedList<Shift> getHistoryShift(){return historyShift;}
	public int getSuperBranch(){return superBranch;}
	public LinkedList<Integer> getAllBranches(){return branchs;}

}