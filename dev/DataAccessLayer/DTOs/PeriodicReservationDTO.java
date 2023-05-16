package DataAccessLayer.DTOs;

import BusinessLayer.Inventory.ProductStatus;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicReservationDTO implements DTO {
    private int supplierId;
    private int branchId;
    private DayOfWeek dayOfOrder;
    private List<PeriodicReservationItemDTO> allItems;

    public PeriodicReservationDTO(int supplierId, int branchId, DayOfWeek dayOfOrder,
            List<PeriodicReservationItemDTO> allItems) {
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.dayOfOrder = dayOfOrder;
        this.allItems = allItems;
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("supplierId", "" + supplierId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("dayOfOrder", "" +dayOfOrder.getValue());
        return nameToVal;
    }


    public int getSupplierId() {
        return supplierId;
    }

    public int getBranchId() {
        return branchId;
    }

    public DayOfWeek getDayOfOrder() {
        return dayOfOrder;
    }

    public List<PeriodicReservationItemDTO> getAllItems() {
        return allItems;
    }

    public HashMap<Integer, Integer> getAllItemsMap() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (PeriodicReservationItemDTO periodicReservationItemDTO : allItems) {
            int productId = periodicReservationItemDTO.getProductId();
            int amount = periodicReservationItemDTO.getAmount();
            result.put(productId, amount);
        }
        return result;
    }

    public void setDayOfOrder(DayOfWeek dayOfOrder) {
        this.dayOfOrder = dayOfOrder;
    }

    public void setAllItems(List<PeriodicReservationItemDTO> allItems) {
        this.allItems = allItems;
    }
}
