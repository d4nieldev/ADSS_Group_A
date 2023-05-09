package DataAccessLayer.DTO.EmployeeLayer;

import java.util.LinkedList;

import Misc.*;

public class BranchDTO {
    private int branchId;
    private String address;
    private Location location;
    private LinkedList<Integer> originEmployees;
    private LinkedList<Integer> foreignEmployees;
    private LinkedList<Integer> notAllowEmployees;
    private LinkedList<Integer> shifts;

    public BranchDTO(int branchId, String address, String location, LinkedList<Integer> originEmployees,
    LinkedList<Integer> foreignEmployees, LinkedList<Integer> notAllowEmployees, LinkedList<Integer> shifts){
        this.branchId = branchId;
        this.address = address;
        this.location = Location.valueOf(location.toUpperCase());
        this.originEmployees = originEmployees;
        this.foreignEmployees = foreignEmployees;
        this. notAllowEmployees = notAllowEmployees;
        this.shifts = shifts;
    }
    
    public String fieldsToString() {
        return String.format("(\"%d\",\"%s\",\"%s\")",
		 this.branchId, this.address, this.location);
    }
    
	public int getNumberOfOriginEmployees(){return originEmployees.size();}
    public String getOriginEmployee(int index) {
        return String.format("(\"%d\",\"%d\",\"%s\")", this.branchId, originEmployees.get(index), "ORIGIN");
    }

	public int getNumberOfForeignEmployees(){return foreignEmployees.size();}
    public String getForeignEmployee(int index) {
        return String.format("(\"%d\",\"%d\",\"%s\")", this.branchId, foreignEmployees.get(index), "FOREIGN");
    }

	public int getNumberOfnotAllowEmployees(){return notAllowEmployees.size();}
    public String getnotAllowEmployee(int index) {
        return String.format("(\"%d\",\"%d\",\"%s\")", this.branchId, notAllowEmployees.get(index), "NOTALLOW");
    }

	public int getNumberOfShifts(){return shifts.size();}
    public String getShift(int index) {
        return String.format("(\"%d\",\"%d\")", this.branchId, shifts.get(index));
    }

}
