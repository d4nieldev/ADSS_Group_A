package DataAccessLayer.DTO.EmployeeLayer;
import java.time.LocalDate;
import java.util.LinkedList;

import Misc.License;

public class DriverDTO {
	public int id;
	public String firstName;
	public String lastName;
	public String password;
	public int bankNum;
	public int bankBranch;
	public int bankAccount;
	public int salary;
	public int bonus;
	public LocalDate startDate;
	public String tempsEmployment;
	public int role;
	public boolean isLoggedIn;
	public int superBranch;
    public License driverLicense;
    public LinkedList<LocalDate> availableShiftDates;
    public LinkedList<LocalDate> workedDates;
	

	public DriverDTO(int id, String firstName, String lastName, String password, int bankNum, int bankBranch, int bankAccount, 
						int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, int role,
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
		this.role = role;
		this.isLoggedIn = isLoggedIn;
		this.superBranch = branch;
        this.driverLicense = driverLicense;
        this.availableShiftDates = availableShiftDates;
        this.workedDates = workedDates;
	}
	

	public String fieldsToString() {
        return String.format("(\"%d\",\"%s\",\"%s\",\"%s\",\"%d\",\"%d\",\"%d\",\"%d\",\"%d\",\"%s\",\"%s\",\"%b\",\"%d\",\"%s\")",
		this.id, this.firstName, this.lastName, this.password, this.bankNum, this.bankBranch, this.bankAccount, this.salary,
		 this.bonus, this.startDate.toString(), this.tempsEmployment, this.isLoggedIn, this.superBranch, 
		 this.driverLicense.toString());
    }

    // public String getRole() {
    //     return String.format("(\"%d\",%d)", this.id, roles.get(0));
    // }

	public int getNumberOfAvailableShiftDates(){return availableShiftDates.size();}
    public String getAvailableShiftDates(int index) {
        return String.format("(\"%d\",%s)", this.id, availableShiftDates.get(index).toString());
    }
	public int getNumberOfWorkedDates(){return workedDates.size();}
    public String getWorkedDates(int index) {
        return String.format("(\"%d\",%s)", this.id, workedDates.get(index).toString());
    }
	
}
