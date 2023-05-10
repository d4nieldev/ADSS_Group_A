package DataAccessLayer.DTOs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import BusinessLayer.Inventory.ProductStatus;



public class SpecificProductDTO implements DTO{
private int specificId; 
private int generalId;
private int branchId;
private double buyPrice;
private double sellPrice;
private  ProductStatus.status status;
private String flaw;
private LocalDate expDate;


public SpecificProductDTO(int specificId, int generalId, int branchId, double buyPrice, double sellPrice,  ProductStatus.status status, String flaw, LocalDate expDate) {
   
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
    nameToVal.put("status", status.toString());
    nameToVal.put("flaw", flaw);
    nameToVal.put("expDate", expDate.toString());
    return nameToVal;
}

public int getSpecificId() {
    return specificId;
}

public int getGeneralId() {
    return generalId;
}

public int getBranchId() {
    return branchId;
}

public double getBuyPrice() {
    return buyPrice;
}

public double getSellPrice() {
    return sellPrice;
}

public ProductStatus.status getStatus() {
    return status;
}

public String getFlaw() {
    return flaw;
}

public LocalDate getExpDate() {
    return expDate;
}




}
