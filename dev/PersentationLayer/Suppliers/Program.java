package PersentationLayer.Suppliers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.annotation.processing.SupportedOptions;

import ServiceLayer.Suppliers.ReservationService;

public class Program {
    private static ReservationService rs = new ReservationService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command (help for a guide): ");
        String[] commandTokens = scanner.nextLine().split(" ");
        switch (commandTokens[0]) {
            case "help":
                help();
            case "makereservation":
                makereservation(scanner);
            case "receipt":
                receipt(commandTokens);
            case "reservations":
                reservations(commandTokens);
            case "ready":
                ready();
            default:
                System.out.println("Unknown command");
        }
    }

    private static void help() {
        String manual = "";
        manual += "===========================================================================================";
        manual += "This is the manual for how to use the suppliers system:\n";
        manual += "help = show the manual\n";
        manual += "makereservation = open the reservation menu. enter lines in the format of \"[product_id] [amount]\"\n";
        manual += "                  for changing amount of product type the line again with the updated amount\n";
        manual += "                  for closing the reservation menu enter \"done\"\n";
        manual += "                  for aborting the reservation enter \"abort\"\n";
        manual += "                  after completing the reservation. You will get a response with the reservation id if was successful\n";
        manual += "receipt [reservation_id] = show all items, amounts, and prices for this reservation\n";
        manual += "reservations [supplier_id] = show all reservations history with the supplier\n";
        manual += "ready = for each supplier, show the destinations of the reservations that are ready\n";
        manual += "===========================================================================================";
        System.out.println(manual);
    }

    private static void makereservation(Scanner scanner) {
        String line = scanner.nextLine();
        Map<Integer, Integer> productToAmount = new HashMap<>();

        while (!line.equals("done") && !line.equals("abort")) {
            String[] command = line.split(" ");
            if (command.length != 2) {
                System.out.println("The format of the command is \"[product_id] [amount]\". Please try again.\n");
                continue;
            }
            int productId = tryParseInt(command[0], Integer.MIN_VALUE);
            if (productId == Integer.MIN_VALUE) {
                System.out.println("product id must be an integer. Please try again.\n");
                continue;
            } else if (productId <= 0) {
                System.out.println("product id must be greater than 0. Please try again.\n");
                continue;
            }
            int amount = tryParseInt(command[1], Integer.MIN_VALUE);
            if (amount == Integer.MIN_VALUE) {
                System.out.println("amount must be an integer. Please try again.\n");
                continue;
            } else if (amount <= 0) {
                System.out.println("amount must be greater than 0. Please try again.\n");
                continue;
            }

            productToAmount.put(productId, amount);
            line = scanner.nextLine();
        }

        if (line.equals("done"))
            System.out.println(rs.makeReservation(productToAmount));
    }

    private static void receipt(String[] commandTokens) {
        if (commandTokens.length != 2) {
            System.out.println("receipt command must have 2 arguments. Please try again.\n");
            return;
        }
        int reservationId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (reservationId == Integer.MIN_VALUE) {
            System.out.println("reservation id must be an integer. Please try again.\n");
            return;
        }
        System.out.println(rs.getReservationReceipt(reservationId));
    }

    private static void reservations(String[] commandTokens) {
        if (commandTokens.length != 2) {
            System.out.println("reservations command must have 2 arguments. Please try again.\n");
            return;
        }
        int supplierId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (supplierId == Integer.MIN_VALUE) {
            System.out.println("supplier id must be an integer. Please try again.\n");
            return;
        }
        System.out.println(rs.getSupplierReservations(supplierId));
    }

    private static void ready() {
        System.out.println(rs.getReadySupplierToAddresses());
    }

    private static int tryParseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
