package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class BranchDTO implements DTO {
//    private LPeriodicReservationDTO periodicReservationDTO;
    private int id;
    private String name;
    private int minAmount; //min amount to perform deficiency order

    public BranchDTO(int id, String name/*,PeriodicReservationDTO periodicReservationDTO*/,int minAmount){
        this.id = id;
        this.name = name;
        this.minAmount = minAmount;
//        this.periodicReservationDTO = periodicReservationDTO;
    }

    public String getName(){
        return name;
    }

    public int getMinAmount() {
        return minAmount;
    }

    //    public PeriodicReservationDTO getPeriodicReservationDTO() {
//        return periodicReservationDTO;
//    }
    public int getId() {
        return id;
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
