package ServiceLayer.Suppliers;

import java.sql.SQLException;
import java.util.Map;

import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DTOs.ProductDTO;

public class ReservationService {
    private ReservationController reservationController;

    public ReservationService() {
        reservationController = ReservationController.getInstance();
    }

    // requirement dropped
    // public String makeAutoReservation(Map<Integer, Integer> productToAmount,
    // String destinationBranch) {
    // try {
    // reservationController.makeDeficiencyReservation(productToAmount,
    // destinationBranch);
    // return "Success";
    // } catch (SuppliersException e) {
    // return e.getMessage();
    // }
    // }

    public String makeManualReservation(Map<Integer, Map<Integer, Integer>> supplierToproductToAmount,
            int destinationBranch) {
        try {
            reservationController.makeManualReservation(supplierToproductToAmount, destinationBranch);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String cancelReservation(int reservationId) {
        try {
            reservationController.cancelReservation(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String makeReservationReady(int reservationId) {
        try {
            reservationController.makeReservationReady(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String closeReservation(int reservationId) {
        try {
            reservationController.closeReservation(reservationId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String getReservationReceipt(int reservationId) {
        try {
            // TODO: switch to json or something
            return Reservation.reservationsToString(reservationController.getReservationReceipt(reservationId));
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String getSupplierReservations(int supplierId) {
        // TODO: switch to json or something
        try {
            return reservationController.getSupplierReservations(supplierId).toString();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    // TODO: do we need this
    // public String getReadySupplierToAddresses() {
    // // TODO: convert to json or something
    // return reservationController.getReadySupplierToBranches().toString();
    // }

    public String addProduct(int id, String name, String manufacturer, int categoryId) {
        try {
            ProductController.getInstance().addProduct(id, name, manufacturer, categoryId);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

}
