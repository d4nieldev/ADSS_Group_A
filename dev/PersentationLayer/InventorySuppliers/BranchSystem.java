package PersentationLayer.InventorySuppliers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import ServiceLayer.InventorySuppliers.BranchService;

public class BranchSystem {
    private static BranchService branchService = new BranchService();

    public static void help() {
        System.out.println("addBranch = adds a new branch to the system");
        System.out.println("addProductBranch = adds a new product to an existing branch");
    }

    public static void addNewBranch(Scanner scanner) {
        int branchId = Integer.MIN_VALUE;
        do {
            System.out.print("Enter branch Id: ");
            branchId = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (branchId == Integer.MIN_VALUE);

        System.out.print("Enter branch name: ");
        String branchName = scanner.nextLine();

        int minAmount = Integer.MIN_VALUE;
        do {
            System.out.print("Enter branch min amount (for auto deficiency reservation): ");
            minAmount = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (minAmount == Integer.MIN_VALUE);

        System.out.println(branchService.addBranch(branchId, branchName, minAmount));
    }

    public static void addNewProductBranch(Scanner scanner) {
        int productId;
        do {
            System.out.print("Enter product Id: ");
            productId = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (productId == Integer.MIN_VALUE);

        int branchId;
        do {
            System.out.print("Enter branch Id: ");
            branchId = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (branchId == Integer.MIN_VALUE);

        String answer = null;
        do {
            System.out.print("Is this product on discount? (Y/N): ");
            answer = scanner.nextLine();
        } while (!answer.equals("Y") && !answer.equals("N"));

        LocalDate discountStartDate = null;
        LocalDate discountEndDate = null;
        boolean isDiscountPrecentage = false;
        double discountVal = -1;

        if (answer.equals("Y")) {
            do {
                System.out.print("Enter discount start date (YYYY-MM-DD): ");
                discountStartDate = tryParseLocalDate(scanner.nextLine(), null);
            } while (discountStartDate == null);

            do {
                System.out.print("Enter discount end date (YYYY-MM-DD): ");
                discountEndDate = tryParseLocalDate(scanner.nextLine(), null);
            } while (discountEndDate == null);

            do {
                System.out.print("Enter discount value (followed by % for precentage, and nothing for fixed): ");
                String input = scanner.nextLine();
                if (input.indexOf('%') != -1) {
                    isDiscountPrecentage = true;
                    discountVal = tryParseInt(input.substring(0, input.length() - 1), -1);
                } else {
                    isDiscountPrecentage = false;
                    discountVal = tryParseInt(input, -1);
                }
            } while (discountVal < 0);

        }

        double price;
        do {
            System.out.print("Enter price: ");
            price = tryParseDouble(scanner.nextLine(), Double.MIN_VALUE);
        } while (price == Double.MIN_VALUE);

        int minQuantity;
        do {
            System.out.print("Enter minimum quantity: ");
            minQuantity = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (price == Integer.MIN_VALUE);

        int idealQuantity;
        do {
            System.out.print("Enter ideal quantity: ");
            idealQuantity = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        } while (price == Integer.MIN_VALUE);

        System.out.println(
                branchService.addProductBranch(productId, branchId, discountStartDate, discountEndDate, discountVal,
                        isDiscountPrecentage, price, minQuantity, idealQuantity));
    }

    public static int tryParseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double tryParseDouble(String s, double defaultValue) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static LocalDate tryParseLocalDate(String s, LocalDate defaultValue) {
        try {
            return LocalDate.parse(s);
        } catch (DateTimeParseException e) {
            return defaultValue;
        }
    }
}
