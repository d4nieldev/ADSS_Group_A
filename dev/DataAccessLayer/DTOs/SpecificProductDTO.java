package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;



public class SpecificProductDTO implements DTO{
private int specificId; 
private int generalId;
private int branchId;
private double buyPrice;
private double sellPrice;
private String status;
private String flaw;
private String expDate;


public SpecificProductDTO(int specificId, int generalId, int branchId, double buyPrice, double sellPrice, String status, String flaw, String expDate) {
    this.specificId = specificId;
    this.generalId = generalId;
    this.branchId = branchId;
    this.buyPrice = buyPrice;
    this.sellPrice = sellPrice;
    this.status = status;
    this.flaw = flaw;
    this.expDate = expDate;
}

@Override
public Map<String, String> getNameToVal() {
    Map<String, String> nameToVal = new HashMap<>();
    nameToVal.put("specificId", "" + specificId);
    nameToVal.put("generalId", "" + generalId);
    nameToVal.put("branchDTO", "" + branchId);
    nameToVal.put("buyPrice", "" + buyPrice);
    nameToVal.put("sellPrice", "" + sellPrice);
    nameToVal.put("status", status);
    nameToVal.put("flaw", flaw);
    nameToVal.put("expDate", expDate);
    return nameToVal;
}
}
