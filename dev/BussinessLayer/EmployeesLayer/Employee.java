package BussinessLayer.EmployeesLayer;
import java.time.LocalDate;
import java.util.LinkedList;

import DataAccessLayer.DTO.EmployeeLayer.EmployeeDTO;

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
	private String tempsEmployment;
	private LinkedList<Integer> roles;
	private boolean isLoggedIn;
	private LinkedList<Shift> historyShift;
	private int superBranch;
	private LinkedList<Integer> branchs;

	public Employee(String firstName, String lastName, int id, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, Integer role, Integer branch){
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.password = password;
		this.bankNum = bankNum;
		this.bankBranch = bankBranch;
		this.bankAccount = bankAccount;
		this.salary = salary;
		this.bonus = InitializeBonus;
		this.startDate = startDate;
		this.tempsEmployment = tempsEmployment;
		roles = new LinkedList<>();
		roles.add(role);
		isLoggedIn = false;
		historyShift = new LinkedList<>();
		superBranch = branch;
		branchs = new LinkedList<>();
		branchs.add(branch);
	}

	// constructor from database
    public Employee(EmployeeDTO DTO, LinkedList<Shift> historyShift) {
        this.firstName = DTO.firstName;
        this.lastName = DTO.lastName;
        this.id = DTO.id;
		this.password = DTO.password;
		this.bankNum = DTO.bankNum;
		this.bankBranch = DTO.bankBranch;
		this.bankAccount = DTO.bankAccount;
		this.salary = DTO.salary;
		this.bonus = DTO.bonus;
		this.startDate = DTO.startDate;
		this.tempsEmployment = DTO.tempsEmployment;
		this.roles = DTO.roles;
		this.isLoggedIn = DTO.isLoggedIn;
		this.historyShift = historyShift;
		this.superBranch = DTO.superBranch;
		this.branchs = DTO.branchs;
	}

	// add role if not exsist to employee
	public void addRole(Integer roleToAdd){
		if(roles.contains(roleToAdd)){
			throw new Error("This employee already have this role.");
		}
		roles.add(roleToAdd);
	}

	// remove role if exsist to employee
	public void removeRole(Integer roleToRemove){
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

	public void removeBranch(int branchId){
		if(branchs.contains(branchId)) {branchs.remove(branchId);}
		if(superBranch == branchId){
			throw new Error("An employee cannot be banded from his origin branch. Please change origin branch before.");
		}
	}

	public boolean checkInBranch(int branchId){
		if(branchs.contains(branchId) || superBranch == branchId){return true;}
		return false;
	}

	// check if the employee have a shift in some date
	// return false if the employee is avalible in this date = does not have a shift on that day
	public boolean checkShiftInDate(LocalDate date){
		for (Shift shift : historyShift) {
			if(shift.getDate().equals(date)){
				return true;
			}
		}
		return false;
	}

	public void checkRoleInEmployee(Integer role) {
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

    public EmployeeDTO toDTO() {
        return new EmployeeDTO(this.id, this.firstName, this.lastName, this.password, this.bankNum, this.bankBranch,
		this.bankAccount, this.salary, this.bonus, this.startDate, this.tempsEmployment,
		this.roles, this.isLoggedIn, this.superBranch, this.branchs);
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
	public void setBonus(int newBonus){this.bonus = newBonus;}
	public LocalDate getStartDate(){return startDate;}
	public void setStartDate(LocalDate date){this.startDate = date;}
	public String getTempsEmployment(){return tempsEmployment;}
	public void setTempsEmployment(String tempsEmployment){this.tempsEmployment = tempsEmployment;}
	public LinkedList<Integer> getRoles(){return roles;}
	public boolean getIsLoggedIn(){return isLoggedIn;}
	public void SetIsLoggedInToTrue(){isLoggedIn = true;}
	public void SetIsLoggedInToFalse(){isLoggedIn = false;}
	public LinkedList<Shift> getHistoryShift(){return historyShift;}
	public int getSuperBranch(){return superBranch;}
	public LinkedList<Integer> getAllBranches(){return branchs;}

}