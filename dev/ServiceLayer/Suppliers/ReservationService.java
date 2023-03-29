package ServiceLayer.Suppliers;

import java.util.Map;

import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationService {
    private ReservationController reservationController;

    public String makeReservation(Map<Integer, Integer> productToAmount) {
        try {
            reservationController.splitReservation(productToAmount);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
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
