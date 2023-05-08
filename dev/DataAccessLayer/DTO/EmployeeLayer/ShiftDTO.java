package DataAccessLayer.DTO.EmployeeLayer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import Misc.ShiftTime;

public class ShiftDTO {
    public int idShift;
    private Integer superBranch;
    private LocalDate date;
    private ShiftTime time;
    private int startHour;
    private int endHour; 
    private int duration;
    private boolean finishSettingShift;
    private HashMap<Integer, LinkedList<Integer>> constraints;
    private HashMap<Integer, Integer> numEmployeesForRole;
    private HashMap<Integer, Integer> finalShift;
    private HashMap<Integer, LinkedList<Integer>> cancellations;

	
	public ShiftDTO(int idShift, Integer superBranch, LocalDate date, ShiftTime time, int startHour, int endHour, 
                    int duration, boolean finishSettingShift, HashMap<Integer, LinkedList<Integer>> constraints,
                    HashMap<Integer, Integer> numEmployeesForRole, HashMap<Integer, Integer> finalShift, 
                    HashMap<Integer, LinkedList<Integer>> cancellations){
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
	}

    public int getId(){return idShift;}
	
    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\")",
		 this.idShift, this.superBranch, this.date, this.time, this.startHour, this.endHour, this.duration, this.finishSettingShift,
		 this.constraints, this.numEmployeesForRole, this.finalShift, this.cancellations);
    }

	public int getNumberOfConstraints(){return constraints.size();}
    public String getConstraint(int index) {
        return String.format("(\"%s\",%s)", this.idShift, constraints.get(index));
    }
    
	public int getNumEmployeesForRole(){return numEmployeesForRole.size();}
    public String getNumEmployeesForRole(int index) {
        return String.format("(\"%s\",%s)", this.idShift, numEmployeesForRole.get(index));
    }
    
	public int getNumberOfFinalShift(){return finalShift.size();}
    public String getFinalShift(int index) {
        return String.format("(\"%s\",%s)", this.idShift, finalShift.get(index));
    }
    
	public int getNumberOfCancellations(){return cancellations.size();}
    public String getCancellation(int index) {
        return String.format("(\"%s\",%s)", this.idShift, cancellations.get(index));
    }
}
