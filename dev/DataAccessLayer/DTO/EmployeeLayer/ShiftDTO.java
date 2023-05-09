package DataAccessLayer.DTO.EmployeeLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

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
    public String getConstraint(int index) {
        return String.format("(\"%d\",%s)", this.idShift, constraints.get(index));
    }
    
	public int getNumEmployeesForRole(){return numEmployeesForRole.size();}
    public String getNumEmployeesForRole(int index) {
        return String.format("(\"%d\",%s)", this.idShift, numEmployeesForRole.get(index));
    }
    
	public int getNumberOfFinalShift(){return finalShift.size();}
    public String getFinalShift(int index) {
        return String.format("(\"%d\",%s)", this.idShift, finalShift.get(index));
    }
    
	public int getNumberOfCancellations(){return cancellations.size();}
    public String getCancellation(int index) {
        return String.format("(\"%d\",%s)", this.idShift, cancellations.get(index));
    }
    
	public int getNumberOfDriversInShift(){return driversInShift.size();}
    public String getDriverInShift(int index) {
        return String.format("(\"%d\",%s)", this.idShift, driversInShift.get(index));
    }
}
