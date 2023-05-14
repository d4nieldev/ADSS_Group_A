package PersentationLayer.Suppliers;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ServiceLayer.Suppliers.ReservationService;

public class ReservationSystem {
    private static ReservationService rs = new ReservationService();

    public static void help() {
        String manual = "";
        manual += "===========================================================================================";
        manual += "makereservation [branch] = open the reservation menu.\n";
        manual += "    enter lines in the format of \"[supplier_id] [product_id] [amount]\"\n";
        manual += "    for changing amount of product type the line again with the updated amount\n";
        manual += "    for closing the reservation menu enter \"done\"\n";
        manual += "    for aborting the reservation enter \"abort\"\n";
        manual += "    after completing the reservation. You will get a response with the reservation id if was successful\n";
        manual += "cancelreservation [reservation_id] = cancel the reservation\n";
        manual += "readyreservation [reservation_id] = make the reservation ready\n";
        manual += "closereservation [reservation_id] = close the reservation\n";
        manual += "addPeriodicReservation [supplier_id] [branch_id] [week_day] = add a new periodic reservation for the supplier and branch\n";
        manual += "receipt [reservation_id] = show all items, amounts, and prices for this reservation\n";
        manual += "reservations [supplier_id] = show all reservations history with the supplier\n";
        manual += "===========================================================================================";
        System.out.println(manual);
    }

    public static void makereservation(String[] commandTokens, Scanner scanner) {
        if (commandTokens.length != 2) {
            System.out.println("makereservation command requires 2 arguments");
            return;
        }

        int destinationBranch = tryParseInt(commandTokens[2], Integer.MIN_VALUE);
        if (destinationBranch == Integer.MIN_VALUE) {
            System.out.println("destination branch id must be an integer");
            return;
        }

        makeManualReservation(scanner, destinationBranch);
    }

    private static void makeManualReservation(Scanner scanner, int destinationBranch) {
        Map<Integer, Map<Integer, Integer>> supplierToproductToAmount = new HashMap<>();
        String line;

        while (true) {
            line = scanner.nextLine();
            if (line.equals("done") || line.equals("abort"))
                break;

            String[] command = line.split(" ");
            if (command.length != 3) {
                System.out.println(
                        "The format of the command is \"[supplier_id] [product_id] [amount]\". Please try again.\n");
                continue;
            }
            int supplierId = tryParseInt(command[0], Integer.MIN_VALUE);
            if (supplierId <= 0) {
                System.out.println("supplier id must be a non negative integer. Please try again.\n");
                continue;
            }
            int productId = tryParseInt(command[1], Integer.MIN_VALUE);
            if (productId <= 0) {
                System.out.println("product id must be a non negative integer. Please try again.\n");
                continue;
            }
            int amount = tryParseInt(command[2], Integer.MIN_VALUE);
            if (amount <= 0) {
                System.out.println("amount must be a non negative integer. Please try again.\n");
                continue;
            }

            supplierToproductToAmount.computeIfAbsent(supplierId, k -> new HashMap<>()).put(productId, amount);
        }

        if (line.equals("done") && supplierToproductToAmount.size() > 0)
            System.out.println(rs.makeManualReservation(supplierToproductToAmount, destinationBranch));
        else
            System.out.println("aborted");

    }

    public static void cancelreservation(String[] commandTokens) {
        if (commandTokens.length != 2) {
            System.out.println("cancelreservation command requires 2 arguments");
            return;
        }

        int reservationId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (reservationId == Integer.MIN_VALUE) {
            System.out.println("reservation id must be an integer. Please try again.\n");
            return;
        }
        System.out.println(rs.cancelReservation(reservationId));
    }

    public static void readyreservation(String[] commandTokens) {
        if (commandTokens.length != 2) {
            System.out.println("readyreservation command requires 2 arguments");
            return;
        }
        int reservationId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (reservationId == Integer.MIN_VALUE) {
            System.out.println("reservation id must be an integer. Please try again.\n");
            return;
        }
        System.out.println(rs.makeReservationReady(reservationId));
    }

    public static void closereservation(String[] commandTokens) {
        if (commandTokens.length != 2) {
            System.out.println("closereservation command requires 2 arguments");
            return;
        }
        int reservationId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (reservationId == Integer.MIN_VALUE) {
            System.out.println("reservation id must be an integer. Please try again.\n");
            return;
        }
        System.out.println(rs.closeReservation(reservationId));
    }

    private static DayOfWeek intToDayOfWeek(int day) {
        switch (day) {
            case 1:
                return DayOfWeek.SUNDAY;
            case 2:
                return DayOfWeek.MONDAY;
            case 3:
                return DayOfWeek.TUESDAY;
            case 4:
                return DayOfWeek.WEDNESDAY;
            case 5:
                return DayOfWeek.THURSDAY;
            case 6:
                return DayOfWeek.FRIDAY;
            case 7:
                return DayOfWeek.SATURDAY;
            default:
                return null;
        }
    }

    public static void addPeriodicReservation(Scanner scanner, String[] commandTokens) {
        if (commandTokens.length != 4) {
            System.out.println("addPeriodicReservation command requires 4 arguments!");
            return;
        }

        int supplierId = tryParseInt(commandTokens[0], Integer.MIN_VALUE);
        if (supplierId == Integer.MIN_VALUE) {
            System.out.println("supplier id must be an integer. Please try again.\n");
            return;
        }
        int branchId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if (branchId == Integer.MIN_VALUE) {
            System.out.println("branch id must be an integer. Please try again.\n");
            return;
        }
        int day = tryParseInt(commandTokens[2], Integer.MIN_VALUE);
        DayOfWeek dayOfWeek = intToDayOfWeek(day);
        if (dayOfWeek == null) {
            System.out.println("day of week must be an integer between 1 and 7. Please try again.\n");
            return;
        }

        Map<Integer, Integer> productToAmount = new HashMap<>();

        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.equals("done") || line.equals("abort"))
                break;

            String[] command = line.split(" ");
            if (command.length != 2) {
                System.out.println("The format of the command is \"[product_id] [amount]\". Please try again.\n");
                continue;
            }
            int productId = tryParseInt(command[0], Integer.MIN_VALUE);
            if (productId <= 0) {
                System.out.println("supplier id must be a non negative integer. Please try again.\n");
                continue;
            }
            int amount = tryParseInt(command[1], Integer.MIN_VALUE);
            if (amount <= 0) {
                System.out.println("product id must be a non negative integer. Please try again.\n");
                continue;
            }

            productToAmount.put(productId, amount);
        }

        if (line.equals("done") && productToAmount.size() > 0)
            System.out.println(rs.addPeriodicReservation(supplierId, branchId, dayOfWeek, productToAmount));
        else
            System.out.println("aborted");
    }

    public static void receipt(String[] commandTokens) {
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

    public static void reservations(String[] commandTokens) {
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

    public static int tryParseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
