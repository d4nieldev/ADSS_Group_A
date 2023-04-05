package PersentationLayer.Suppliers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ServiceLayer.Suppliers.ReservationService;
import PersentationLayer.Suppliers.ReservationSystem;
import PersentationLayer.Suppliers.SupplierSystem;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command (help for a guide): ");
        String[] commandTokens = scanner.nextLine().split(" ");
        switch (commandTokens[0]) {
            case "help":
                help();
                break;
            case "makereservation":
                ReservationSystem.makereservation(scanner);
                break;
            case "receipt":
                ReservationSystem.receipt(commandTokens);
                break;
            case "reservations":
                ReservationSystem.reservations(commandTokens);
                break;
            case "ready":
                ReservationSystem.ready();
                break;
            case "addSupplier":
                SupplierSystem.addSupplier();
                break;
            case "addAgreement":
                SupplierSystem.addAgreement(commandTokens);
                break;
            case "deleteSupplier":
                SupplierSystem.deleteSupplier(commandTokens);
                break;
            case "editSupplier":
                SupplierSystem.editSupplier(commandTokens);
                break;
            case "getCard":
                SupplierSystem.getSupplierCard(commandTokens);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }

    private static void help() {
        ReservationSystem.help();
        SupplierSystem.help();
    }

}
