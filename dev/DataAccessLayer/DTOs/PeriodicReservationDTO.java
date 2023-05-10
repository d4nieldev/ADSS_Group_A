package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class PeriodicReservationDTO implements DTO{
    private int supplierId;
    private int branchId;
    private String dayOfOrder;

    public PeriodicReservationDTO(int supplierId, int branchId, String dayOfOrder) {
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.dayOfOrder = dayOfOrder;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("dayOfOrder", dayOfOrder);
        return nameToVal;
    }
    
}
