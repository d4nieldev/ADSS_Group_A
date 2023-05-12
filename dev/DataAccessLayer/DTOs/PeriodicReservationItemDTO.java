package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class PeriodicReservationItemDTO implements DTO {
    private int supplierId;
    private int branchId;
    private int productId;
    private int amount;

    public PeriodicReservationItemDTO(int supplierId, int branchId, int productId,int amount) {
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.productId = productId;
        this.amount = amount;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("productId", "" + productId);
        nameToVal.put("amount", "" + amount);
        return nameToVal;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }
}
