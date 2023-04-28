package BussinessLayer.EmployeesLayer;

import java.time.LocalDate;
import java.util.LinkedList;

import Misc.*;

public class Driver extends Employee {

    private License driverLicense;
    //private boolean isAvailable;
    private LinkedList<LocalDate> availableShiftDates;
    private LinkedList<LocalDate> workedDates;

    public Driver(String firstName, String lastName, int id, String password, int bankNum, int bankBranch, int bankAccount, 
	int salary, int InitializeBonus, LocalDate startDate, String tempsEmployment, License licence) {
        super(firstName, lastName, id, password, bankNum, bankBranch, bankAccount, salary, InitializeBonus, startDate, tempsEmployment,
        Role.getRoleByName("DRIVER").getId(), null);
        this.driverLicense = driverLicense;
        availableShiftDates = new LinkedList<>();
        workedDates = new LinkedList<>();
    }
    
    public void AddConstraintDriver(LocalDate date){
        if(availableShiftDates.contains(date)){
            throw new Error("This driver is allready asked to work on this day.");
        }
        if(date.compareTo(LocalDate.now()) <= 0){
            throw new Error("Can not submit a shift to a past date or today.");
        }
        availableShiftDates.add(date);
    }

    public void RemoveConstraintDriver(LocalDate date){
        if(!availableShiftDates.contains(date)){
            throw new Error("This driver did not asked to work on this day.");
        }
        if(date.compareTo(LocalDate.now()) <= 0){
            throw new Error("Can not change constraint to a shift on a past date or today.");
        }
        availableShiftDates.remove(date);
    }

    public void addDateWorked(LocalDate date){
        workedDates.add(date);
    }


    /**
     * return true if license of driver match to model truck
     *
     * @return
     */
    public boolean hasLicenseFor(String model) {
        return this.getDriverLicense().equals(model);
    }

//-------------------------------------Getters And Setters--------------------------------------------------------
	public License getDriverLicense(){return driverLicense;}
	public void setDriverLicense(License driverLicense){this.driverLicense = driverLicense;}
    public LinkedList<LocalDate> getAvailableShiftDates(){return availableShiftDates;}
    public LinkedList<LocalDate> getWorkedDates(){return workedDates;}
}

