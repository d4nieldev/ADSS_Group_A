package ServiceLayer.Suppliers;

import java.util.Map;

import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.exceptions.SuppliersException;

public class ReservationService {
    private ReservationController reservationController;

    public ReservationService() {
        reservationController = ReservationController.getInstance();
    }

    public String makeAutoReservation(Map<Integer, Integer> productToAmount, String destinationBranch) {
        try {
            reservationController.makeAutoReservation(productToAmount, destinationBranch);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }

    public String makeManualReservation(Map<Integer, Map<Integer, Integer>> supplierToproductToAmount,
            String destinationBranch) {
        try {
            reservationController.makeManualReservation(supplierToproductToAmount, destinationBranch);
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
        return reservationController.getSupplierReservations(supplierId).toString();
    }

    public String getReadySupplierToAddresses() {
        // TODO: convert to json or something
        return reservationController.getReadySupplierToBranches().toString();
    }

    public String addProduct(int id, String name, String manufacturer) {
        try {
            Product product = new Product(id, name, manufacturer);
            ProductController.getInstance().addProduct(product);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        }
    }
}
