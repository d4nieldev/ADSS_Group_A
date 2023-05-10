package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ProductBranchDTO implements DTO {

    private ProductDTO productDTO;
    private int branchId;
    private double price;
    private int minQuantity;

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("productId", "" + productDTO.getId());
        nameToVal.put("branchId", ""+ branchId);
        nameToVal.put("price", ""+ price);
        nameToVal.put("minQuantity", ""+ minQuantity);
        return nameToVal;
    }
    
}
