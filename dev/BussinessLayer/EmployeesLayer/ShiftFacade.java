package BussinessLayer.EmployeesLayer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

import DataAccessLayer.DAO.EmployeesLayer.ShiftsDAO;
import DataAccessLayer.DTO.EmployeeLayer.ShiftDTO;
import Misc.*;

public class ShiftFacade {
    private EmployeeFacade employeeFacade;
    private LinkedList<Shift> shifts;
    private static int shiftIdConuter = 0;
    private ShiftsDAO shiftsDAO;

    // constructor
    public ShiftFacade(EmployeeFacade employeeFacade){
        this.employeeFacade = employeeFacade;
        shifts = new LinkedList<Shift>();
        shiftsDAO = new ShiftsDAO();
        shiftIdConuter = shiftsDAO.getMaxShiftIdPlusOne();
    }

    public void addShift(Shift newShift){
        shifts.add(newShift);
        shiftIdConuter++;
        shiftsDAO.insert(newShift.toDTO());
    }
    
    // add cancelation to shift
    public void addCancelation(int shiftId, int employeeId ,int itemId, int itemCode){
        Shift shift = getShift(shiftId);
        Employee employee = employeeFacade.getEmployeeById(employeeId);
        employeeFacade.checkLoggedIn(employeeId);
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("BRANCHMANAGER").getId());
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId());
        shift.addCancelation(employee, itemCode, itemId);
        shiftsDAO.addCancellation(employeeId, itemId, itemCode);
    }

    public String printFinalShift(int employeeId, int idShift){
        employeeFacade.checkLoggedIn(employeeId);
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("HRMANAGER").getId());
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("BRANCHMANAGER").getId());
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("SHIFTMANAGER").getId());
        String strPrint = "";
        Shift shift = getShift(idShift);
        if(!shift.getIsFinishSettingShift()){throw new Error("The shift is not finished yet for printing.");}
        strPrint += shift.toString();
        strPrint += shift.printFinalShift();
        return strPrint;
    }
    
    public String printConstarintsShift(int employeeId, int idShift){
        employeeFacade.checkLoggedIn(employeeId);
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("HRMANAGER").getId());
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("BRANCHMANAGER").getId());
        String strPrint = "";
        Shift shift = getShift(idShift);
        if(shift.getIsFinishSettingShift()){throw new Error("The shift is finished setting.");}
        strPrint += shift.toString();
        strPrint += shift.printConstraints();
        return strPrint;
    }
    
    public void addConstraint(int shiftId, Employee employee, LinkedList<Integer> role) {
        getShift(shiftId).addConstraint(employee, role);
        shiftsDAO.addConstraint(employee.getId(), shiftId);
    }

    public void removeConstraint(int shiftId, Employee employee) {
        getShift(shiftId).removeConstraint(employee);
        shiftsDAO.removeConstraint(employee.getId(), shiftId);
    }

    public void checkAssignFinalShift(int managerID, Shift shift, HashMap<Employee, Integer> hrAssign){
        shift.checkAssignFinalShift(hrAssign);
        // if succedded - save the final shift
        shift.assignFinalShift(hrAssign);
        // save in Database
        shiftsDAO.addShiftFinal(managerID, shift.getID());
    }

    public String missingStaffToRole(int employeeId, int shiftId){
        employeeFacade.checkLoggedIn(employeeId);
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("HRMANAGER").getId());
        employeeFacade.getEmployeeById(employeeId).checkRoleInEmployee(employeeFacade.getRoleClassInstance().getRoleByName("BRANCHMANAGER").getId());
        return getShift(shiftId).missingStaffToRole().toString();
    }

    public boolean checkstorekeeperInShift(int shiftId, String address, LocalDate date){
        boolean res = false;
        Shift shift = getShift(shiftId);
        res = shift.isShiftContainStorekeeper(employeeFacade.getRoleClassInstance().getRoleByName("STOREKEEPER").getId()) 
                && shift.isShiftContainStorekeeper(employeeFacade.getRoleClassInstance().getRoleByName("STOREKEEPER").getId());
        return res;
    }
    
    public void AddDriverToShift(Driver driver, int shiftID){
        getShift(shiftID).addDriver(driver);
        shiftsDAO.addDriverInShift(driver.getId(), shiftID);
    }
    
//-------------------------------------Getters And Setters--------------------------------------------------------

    // throw Error if there is not shift with this ID
    public Shift getShift(int id){
        for (Shift shift : shifts) {
            if(shift.getID() == id){return shift;}
        }
        ShiftDTO shift = shiftsDAO.getShiftById(id);
        if (shift != null) {
            return createNewShiftFromShiftDTO(shift);
        }
        throw new Error("No such a shift in this system by the id " + id);
    }

    public int getShiftIdConuter(){return shiftIdConuter;}

//-------------------------------------Help Functions--------------------------------------------------------

    public HashMap<Employee, LinkedList<Integer>> convertIdsMapAndLinkedListToObjectMap(HashMap<Integer, LinkedList<Integer>> idMap) {
        HashMap<Employee, LinkedList<Integer>> res = new HashMap<Employee, LinkedList<Integer>>();
        for (Integer id : idMap.keySet()) {
            if(employeeFacade.isEmployeeExistsAndLoadEmployee(id)){
                Employee emp = employeeFacade.getEmployeeById(id);
                res.put(emp, idMap.get(id));
            }           
        } 
        return res;
    }

    public HashMap<Employee, Integer> convertIdsMapToObjectMap(HashMap<Integer, Integer> idMap) {
        HashMap<Employee, Integer> res = new HashMap<Employee, Integer>();
        for (Integer id : idMap.keySet()) {
            if(employeeFacade.isEmployeeExistsAndLoadEmployee(id)){
                Employee emp = employeeFacade.getEmployeeById(id);
                res.put(emp, idMap.get(id));
            }           
        } 
        return res;
    }

    public LinkedList<Driver> convertIdsListToDrivers(LinkedList<Integer> lstId) {
        LinkedList<Driver> res = new LinkedList<>();
        for (Integer id : lstId) {
            if(employeeFacade.isEmployeeExistsAndLoadEmployee(id)){
                Driver emp = (Driver) employeeFacade.getEmployeeById(id);
                res.add(emp);
            }           
        } 
        return res;
    }

    private Shift createNewShiftFromShiftDTO(ShiftDTO shiftDTO) {
        HashMap<Employee, LinkedList<Integer>> constraints = convertIdsMapAndLinkedListToObjectMap(shiftDTO.constraints);
        HashMap<Employee, Integer> finalShift = convertIdsMapToObjectMap(shiftDTO.finalShift);
        LinkedList<Driver> driversInShift = convertIdsListToDrivers(shiftDTO.driversInShift);
        Shift s = new Shift(shiftDTO, constraints, finalShift, driversInShift);
        shifts.add(s);
        return s;
    }

    // public Shift getShiftByAddressDateMorning(String address, LocalDate date){
    //     for (Shift shift : shifts) {
    //         if(shift.getSuperBranchAddress().equals(address) && shift.getDate().equals(date) && shift.getShiftTime().equals(ShiftTime.MORNING))
    //             return shift;
    //     }
    //     return null;
    // }

    // public Shift getShiftByAddressDateEvening(String address, LocalDate date){
    //     for (Shift shift : shifts) {
    //         if(shift.getSuperBranchAddress().equals(address) && shift.getDate().equals(date) && shift.getShiftTime().equals(ShiftTime.EVENING))
    //             return shift;
    //     }
    //     return null;
    // }

    public LinkedList<Shift> getShiftsByDate(LocalDate date){
        LinkedList<Shift> res = new LinkedList<>();
        for (Shift shift : shifts) {
            if(shift.getDate().equals(date)) {res.add(shift);}
        }
        return res;
    }

}
