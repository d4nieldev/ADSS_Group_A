package DataAccessLayer.DTO.EmployeeLayer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import BussinessLayer.EmployeesLayer.Shift;

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
	public Date startDate;
	public String tempsEmployment;
	public LinkedList<String> roles;
	public boolean isLoggedIn;
	public LinkedList<Shift> historyShift;
	public int superBranch;
	public LinkedList<Integer> branchs;

	
	public EmployeeDTO(int id, String firstName, String lastName, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int InitializeBonus, Date startDate, String tempsEmployment, Boolean isLoggedIn, Integer branch){
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
		roles = new LinkedList<String>();
		this.isLoggedIn = isLoggedIn;
		historyShift = new LinkedList<>();
		superBranch = branch;
		branchs = new LinkedList<>();
	}

    public int getId(){return id;}
	
    public String fieldsToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\")",
		 this.firstName, this.lastName, this.id, this.password, this.bankNum, this.bankBranch, this.bankAccount, this.salary,
		 this.bonus, formatter.format(this.startDate), this.tempsEmployment, this.isLoggedIn, this.superBranch);
    }

	public int getNumberOfRoles(){return roles.size();}
	
    public String getRole(int index) {
        return String.format("(\"%s\",%s)", this.id, roles.get(index));
    }
}
