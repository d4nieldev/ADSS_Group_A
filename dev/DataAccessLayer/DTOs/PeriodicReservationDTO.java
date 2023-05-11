package DataAccessLayer.DTOs;

import BusinessLayer.Inventory.ProductStatus;

import java.util.HashMap;
import java.util.Map;

public class PeriodicReservationDTO implements DTO{
    private int supplierId;
    private int branchId;
    private ProductStatus.Day dayOfOrder;

    public PeriodicReservationDTO(int supplierId, int branchId, ProductStatus.Day dayOfOrder) {
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.dayOfOrder = dayOfOrder;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("dayOfOrder", dayOfOrder.toString());
        return nameToVal;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getBranchId() {
        return branchId;
    }

    public ProductStatus.Day getDayOfOrder() {
        return dayOfOrder;
    }
}
