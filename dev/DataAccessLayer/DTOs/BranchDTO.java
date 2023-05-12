package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchDTO implements DTO {
//    private LPeriodicReservationDTO periodicReservationDTO;
    private int id;
    private String name;
    private int minAmount; //min amount to perform deficiency order
    private List<PeriodicReservationDTO> periodicReservationDTO;

    public BranchDTO(int id, String name,int minAmount,List<PeriodicReservationDTO> periodicReservationDTO){
        this.id = id;
        this.name = name;
        this.minAmount = minAmount;
        this.periodicReservationDTO = periodicReservationDTO;
    }

    public int getId() {
        return id;
    }
    public String getName(){
        return name;
    }

    public int getMinAmount() {
        return minAmount;
    }
    public List<PeriodicReservationDTO> getAllPeriodicReservationDTO() {
        return periodicReservationDTO;
    }
    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", ""+ name);
        nameToVal.put("minAmount", ""+ minAmount);
        return nameToVal;
    }






    
}
