package BusinessLayer.InveontorySuppliers;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;

public class PeriodicReservation {
    private int supplierId;
    private Map<Integer, Integer> productsToAmounts; // maps between product's code to amount to order
    private DayOfWeek day; // day for delivery;
    ProductController productController;
    private PeriodicReservationDTO dto;

    private int branchId;

    // public PeriodicReservation(int supplierId,Day day) {
    // this.id = Global.getNewPeriodicId();
    // this.day = day;
    // this.productsToAmounts = new HashMap<>();
    // productController = ProductController.getInstance();
    // }
    public PeriodicReservation(PeriodicReservationDTO periodicReservationDTO) {
        this.supplierId = periodicReservationDTO.getSupplierId();
        this.day = periodicReservationDTO.getDayOfOrder();
        this.branchId = periodicReservationDTO.getBranchId();
        this.productsToAmounts = periodicReservationDTO.getAllItemsMap();
        this.dto = periodicReservationDTO;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
        this.dto.setDayOfOrder(day);
    }

    public DayOfWeek getDay() {
        return day;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public Map<Integer, Integer> getProductsToAmounts() {
        return productsToAmounts;
    }

    public int getBranchId() {
        return branchId;
    }

    public PeriodicReservationDTO getDTO() {
        return this.dto;
    }

    public void setProductsToAmounts(Map<Integer, Integer> productsToAmounts) {
        this.productsToAmounts = productsToAmounts;
        List<PeriodicReservationItemDTO> items = new ArrayList<>();
        for (int productId : productsToAmounts.keySet()) {
            int amount = productsToAmounts.get(productId);
            PeriodicReservationItemDTO itemDTO = new PeriodicReservationItemDTO(supplierId, branchId, productId,
                    amount);
            items.add(itemDTO);
        }
        dto.setAllItems(items);
    }
}
