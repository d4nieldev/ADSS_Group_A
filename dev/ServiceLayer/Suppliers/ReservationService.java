package ServiceLayer.Suppliers;

import java.util.List;
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
        try {
            reservationController.cancelReservation(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String makeReservationReady(int reservationId) {
        try {
            reservationController.makeReservationReady(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String closeReservation(int reservationId) {
        try {
            reservationController.closeReservation(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String getReservationReceipt(int reservationId) {
        return null;
    }

    public String getSupplierReservations(int supplierId) {
        return null;
    }

    public Map<Integer, List<String>> getReadySupplierToAddresses() {
        return null;
    }
}
