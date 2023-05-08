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
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\")",
		 this.branchId, this.address, this.location, this.originEmployees, this.foreignEmployees, this.notAllowEmployees,
		 this.shifts);
    }

}
