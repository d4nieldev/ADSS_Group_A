package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class BranchDTO implements DTO {

    private int id;
    private String name;

    public BranchDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getBranchId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("id", "" + id);
        nameToVal.put("name", ""+ name);
        return nameToVal;
    }
    
}
