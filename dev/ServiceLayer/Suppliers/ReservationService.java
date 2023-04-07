package ServiceLayer.Suppliers;

import java.util.Map;

import BusinessLayer.Suppliers.Reservation;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationService {
    private ReservationController reservationController;

    public ReservationService() {
        reservationController = ReservationController.getInstance();
    }

    public String makeReservation(Map<Integer, Integer> productToAmount, String destinationBranch) {
        try {
            reservationController.makeReservation(productToAmount, destinationBranch);
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
            return Reservation.reservationsToString(reservationController.getReservationReceipt(reservationId));
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String getSupplierReservations(int supplierId) {
        // TODO: switch to json or something
        return Reservation.reservationsToString(reservationController.getSupplierReservations(supplierId));
    }

    public String getReadySupplierToAddresses() {
        // TODO: convert to json or something
        return reservationController.getReadySupplierToAddresses().toString();
    }
}
