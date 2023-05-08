package DataAccessLayer.DTO.EmployeeLayer;
import java.time.LocalDate;
import java.util.LinkedList;

import Misc.License;

public class DriverDTO {
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
	public String tempsEmployment;
	public LinkedList<Integer> roles;
	public boolean isLoggedIn;
	public int superBranch;
    public License driverLicense;
    public LinkedList<LocalDate> availableShiftDates;
    public LinkedList<LocalDate> workedDates;
	

	public DriverDTO(int id, String firstName, String lastName, String password, int bankNum, int bankBranch, int bankAccount, 
						int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, LinkedList<Integer> roles,
	 					Boolean isLoggedIn, Integer branch, License driverLicense, LinkedList<LocalDate> availableShiftDates,
                         LinkedList<LocalDate> workedDates){
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
		this.roles = roles;
		this.isLoggedIn = isLoggedIn;
		this.superBranch = branch;
        this.driverLicense = driverLicense;
        this.availableShiftDates = availableShiftDates;
         this.workedDates = workedDates;
	}
	

	public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\")",
		 this.firstName, this.lastName, this.id, this.password, this.bankNum, this.bankBranch, this.bankAccount, this.salary,
		 this.bonus, this.startDate.toString(), this.tempsEmployment, this.isLoggedIn, this.superBranch, 
		 this.driverLicense.toString(), this.availableShiftDates, this.workedDates);
    }

    public String getRole() {
        return String.format("(\"%s\",%s)", this.id, roles.get(0));
    }

	public int getNumberOfAvailableShiftDates(){return availableShiftDates.size();}
    public String getAvailableShiftDates(int index) {
        return String.format("(\"%s\",%s)", this.id, availableShiftDates.get(index));
    }
	public int getNumberOfWorkedDates(){return workedDates.size();}
    public String getWorkedDates(int index) {
        return String.format("(\"%s\",%s)", this.id, workedDates.get(index));
    }
	
}
