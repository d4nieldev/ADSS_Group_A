package ServiceLayer.Suppliers;

import java.util.Map;

import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationService {
    private ReservationController reservationController;

    public ReservationService() {
        reservationController = ReservationController.getInstance();
    }

    public String makeReservation(Map<Integer, Integer> productToAmount) {
        try {
            reservationController.makeReservation(productToAmount);
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
        try {
            // TODO: switch to json or something
            return reservationController.getReservationReceipt(reservationId).toString();
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String getSupplierReservations(int supplierId) {
        try {
            // TODO: switch to json or something
            return reservationController.getSupplierReservations(supplierId).toString();
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String getReadySupplierToAddresses() {
        // TODO: convert to json or something
        return reservationController.getReadySupplierToAddresses().toString();
    }
}
