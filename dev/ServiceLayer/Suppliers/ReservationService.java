package ServiceLayer.Suppliers;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.Map;

import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.InveontorySuppliers.Reservation;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.exceptions.SuppliersException;

public class ReservationService {
    private ReservationController reservationController;

    private ReservationService() {
        reservationController = ReservationController.getInstance();
    }

    public static ReservationService create() {
        try {
            ReservationService service = new ReservationService();
            service.reservationController.init();
            return service;
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing reservation service");
        }
    }

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

    public String addPeriodicReservation(int supplierId, int branchId, DayOfWeek day,
            Map<Integer, Integer> productToAmount) {
        try {
            reservationController.addPeriodicReservation(branchId, supplierId, day, productToAmount);
            return "Success";
        } catch (SuppliersException e) {
            return e.getMessage();
        } catch (SQLException e) {
            return "DATABASE ERROR: " + e.getMessage();
        }
    }

    public String updatePeriodicReservation(int supplierId, int branchId, DayOfWeek day,
            Map<Integer, Integer> productToAmount) {
        try {
            reservationController.updatePeriodicReservation(branchId, supplierId, day, productToAmount);
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
