package ServiceLayer.Suppliers;

import java.util.Map;

import BusinessLayer.Suppliers.ReservationController;

public class ReservationService {
    private ReservationController reservationController;

    public String makeReservation(Map<Integer, Integer> productToAmount) {
        return reservationController.makeReservation(productToAmount);
    }

    public String cancelReservation(int reservationId) {
        return null;
    }

    public String changeReservationStatus(String status) {
        return null;
    }

    public String getReservationReceipt(int reservationId) {
        return null;
    }

    public String getSupplierReservations(int supplierId) {
        return null;
    }
}
