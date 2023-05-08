package PersentationLayer.Suppliers;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String[] commandTokens = scanner.nextLine().split(" ");
            switch (commandTokens[0]) {
                case "help":
                    help();
                    break;
                case "makereservation":
                    ReservationSystem.makereservation(commandTokens, scanner);
                    break;
                case "cancelreservation":
                    ReservationSystem.cancelreservation(commandTokens);
                    break;
                case "readyreservation":
                    ReservationSystem.readyreservation(commandTokens);
                    break;
                case "closereservation":
                    ReservationSystem.closereservation(commandTokens);
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
                case "addProduct":
                    ReservationSystem.addProduct(commandTokens);
                    break;
                case "addSupplier":
                    SupplierSystem.addSupplier(scanner);
                    break;
                case "addAgreement":
                    SupplierSystem.addAgreement(commandTokens, scanner);
                    break;
                case "deleteSupplier":
                    SupplierSystem.deleteSupplier(commandTokens);
                    break;
                case "editSupplier":
                    SupplierSystem.editSupplier(commandTokens, scanner);
                    break;
                case "getCard":
                    SupplierSystem.getSupplierCard(commandTokens);
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Unknown command - \"" + commandTokens[0] + "\"");
                    break;
            }
        }
    }

    private static void help() {
        System.out.println("This is the manual for how to use the system:");
        System.out.println("help = show the manual");
        ReservationSystem.help();
        SupplierSystem.help();
        System.out.println("exit = exit the program");
        // Bdika
    }

}
