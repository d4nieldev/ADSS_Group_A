package DataAccessLayer.DTO.EmployeeLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import Misc.ShiftTime;

public class ShiftDTO {
    public int idShift;
    public Integer superBranch;
    public LocalDate date;
    public ShiftTime time;
    public int startHour;
    public int endHour; 
    public int duration;
    public boolean finishSettingShift;
    public HashMap<Integer, LinkedList<Integer>> constraints;
    public HashMap<Integer, Integer> numEmployeesForRole;
    public HashMap<Integer, Integer> finalShift;
    public HashMap<Integer, LinkedList<Integer>> cancellations;
    public LinkedList<Integer> driversInShift;

	
	public ShiftDTO(int idShift, Integer superBranch, LocalDate date, ShiftTime time, int startHour, int endHour, 
                    int duration, boolean finishSettingShift, HashMap<Integer, LinkedList<Integer>> constraints,
                    HashMap<Integer, Integer> numEmployeesForRole, HashMap<Integer, Integer> finalShift, 
                    HashMap<Integer, LinkedList<Integer>> cancellations, LinkedList<Integer> driversInShift){
		this.idShift = idShift;
		this.superBranch = superBranch;
        this.date = date;
        this.time = time;
        this.startHour = startHour;
        this.endHour = endHour;
        this.duration = duration;
        this.finishSettingShift = finishSettingShift;
        this.constraints = constraints;
        this.numEmployeesForRole = numEmployeesForRole;
        this.finalShift = finalShift;
        this.cancellations = cancellations;
        this.driversInShift = driversInShift;
	}

    public int getId(){return idShift;}
	
    public String fieldsToString() {
        return String.format("(\"%d\",\"%d\",\"%s\",\"%s\",\"%d\",\"%d\",\"%d\",\"%b\")",
		 this.idShift, this.superBranch, this.date, this.time, this.startHour, this.endHour, this.duration, this.finishSettingShift);
    }

	public int getNumberOfConstraints(){return constraints.size();}
	public int getNumberOfEmployeeRolesConstraint(int employeeID){return constraints.get(employeeID).size();}
    public Set<Integer> getListConstraintsKeys(){return constraints.keySet();}
    public String getConstraint(int employeeID, int index) {
        return String.format("(\"%d\",\"%d\",\"%d\")", this.idShift, employeeID, constraints.get(employeeID).get(index));
    }
    
	public int getNumEmployeesForRole(){return numEmployeesForRole.size();}
    public Set<Integer> getListNumEmployeesForRoleKeys(){return numEmployeesForRole.keySet();}
    public String getNumEmployeesForRole(int roleID) {
        return String.format("(\"%d\",\"%d\",\"%d\")", this.idShift, roleID, numEmployeesForRole.get(roleID));
    }
    
	public int getNumberOfFinalShift(){return finalShift.size();}
    public Set<Integer> getListFinalShiftKeys(){return finalShift.keySet();}
    public String getFinalShift(int employeeID) {
        return String.format("(\"%d\",\"%d\",\"%d\")", this.idShift, employeeID, finalShift.get(employeeID));
    }
    
	public int getNumberOfCancellations(){return cancellations.size();}
	public int getNumberOfProductCodeCancellations(int productCode){return cancellations.get(productCode).size();}
    public Set<Integer> getListCancellationsKeys(){return cancellations.keySet();}
    public String getCancellation(int productCode, int index) {
        return String.format("(\"%d\",\"%d\",\"%d\")", this.idShift, productCode, cancellations.get(productCode).get(index));
    }
    
	public int getNumberOfDriversInShift(){return driversInShift.size();}
    public String getDriverInShift(int index) {
        return String.format("(\"%d\",\"%d\")", this.idShift, driversInShift.get(index));
    }
}
