package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;



public class SpecificProductDTO {
    private String specificId; 
private ProductBranchDTO productBrnachDTO;
private BranchDTO branchDTO;
private String buyPrice;
private String sellPrice;
private String status;
private String flaw;
private String expDate;


public SpecificProductDTO(String specificId, ProductBranchDTO productBrnachDTO, BranchDsto branchDTO, String buyPrice,
        String sellPrice, String status, String flaw, String expDate) {
    this.specificId = specificId;
    this.productBrnachDTO = productBrnachDTO;
    this.branchDTO = branchDTO;
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
    nameToVal.put("productBrnachId", "" + productBrnachDTO.getProductBrnachId());
    nameToVal.put("branchDTO", "" + branchDTO.getBranchId());
    nameToVal.put("buyPrice", buyPrice);
    nameToVal.put("status", status);
    nameToVal.put("flaw", flaw);
    nameToVal.put("expDate", expDate);
    return nameToVal;
}
}
