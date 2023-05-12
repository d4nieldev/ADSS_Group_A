package DataAccessLayer.DTOs;

import BusinessLayer.Inventory.ProductStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicReservationDTO implements DTO{
    private int supplierId;
    private int branchId;
    private ProductStatus.Day dayOfOrder;
    private List<PeriodicReservationItemDTO> allItems;



    public PeriodicReservationDTO(int supplierId, int branchId, ProductStatus.Day dayOfOrder, List<PeriodicReservationItemDTO> allItems) {
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

    public List<PeriodicReservationItemDTO> getAllItems() {
        return allItems;
    }
    public HashMap<Integer,Integer> getAllItemsMap(){
        HashMap<Integer,Integer> result = new HashMap<>();
        for (PeriodicReservationItemDTO periodicReservationItemDTO : allItems){
            int productId = periodicReservationItemDTO.getProductId();
            int amount = periodicReservationItemDTO.getAmount();
            result.put(productId,amount);
        }
        return result;
    }
    public void addProductAndAmount(PeriodicReservationItemDTO periodicReservationItemDTO){
        this.allItems.add(periodicReservationItemDTO);
    }

    public void updateItem(PeriodicReservationItemDTO periodicReservationItemDTO){
        for (PeriodicReservationItemDTO pri : allItems){
            if(pri.getBranchId() == periodicReservationItemDTO.getBranchId() && pri.getSupplierId() == periodicReservationItemDTO.getSupplierId()
                    && pri.getProductId() == periodicReservationItemDTO.getProductId())
                pri = periodicReservationItemDTO;
        }
    }
}
