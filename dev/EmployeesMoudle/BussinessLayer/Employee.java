import java.sql.Time;
import java.util.Date;
import java.util.List;

import Misc.License;
import Misc.Role;

class Employee{
	private String name;
	private int id;
	private String username;
	private String password;
	private int bankNum;
	private int bankBranch;
	private int bankAccount;
	private int salary;
	private Date startDate;
	private License driverLicense;
	private List<Role> roles;
}