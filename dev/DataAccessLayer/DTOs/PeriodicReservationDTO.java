package DataAccessLayer.DTOs;

public class PeriodicReservationDTO {
    private int supplierId;
    private int branchId;
    private String dayOfOrder;
    
    public PeriodicReservationDTO(int supplierId, int branchId, String dayOfOrder) {
        this.supplierId = supplierId;
        this.branchId = branchId;
        this.dayOfOrder = dayOfOrder;
    }
    
}
