package PersentationLayer.Suppliers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import ServiceLayer.Suppliers.SupplierService;

public class SupplierSystem {
    private static SupplierService ss = new SupplierService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter command (help for a guide): ");
        String[] commandTokens = scanner.nextLine().split(" ");
        switch (commandTokens[0]) {
            case "help": {
                help();
                break;
            }
            case "add": {
                switch (commandTokens[1]) {
                    case "supplier":
                        addSupplier();
                        break;
                    case "agreement":
                        addAgreement(commandTokens);
                        break;
                    default:
                        System.out.println("Unknown command");
                        break;
                }
            }
            case "delete":
                deleteSupplier(commandTokens);
                break;
            case "edit":
                editSupplier(commandTokens);
                break;
            case "get":
                getSupplierCard(commandTokens);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
        System.out.println("press any key to exit");
        scanner.nextLine();
    }

    private static void help() {
        String manual = "";
        manual += "===========================================================================================\n";
        manual += "This is the manual for how to use the suppliers system:\n";
        manual += "help = show the manual\n";
        manual += "add supplier = Adds a new supplier to the system. Enter the information that the system will ask you to about the supplier you want to add.\n";
        manual += "delete supplier [supplier_id] = Deletes an existing supplier from the system.\n";
        manual += "edit supplier [supplier_id] = After typing this command, you can edit the supplier information with the following commands: \n";
        manual += "                              update name [new_name]\n";
        manual += "                              update phone [new_phone]\n";
        manual += "                              update bank account [new_bankAccount]\n";
        manual += "                              update fields = The system will ask you to enter fields one by one until typing 'done'\n";
        manual += "                              update paymentCondition [new_paymentCondition]\n";
        manual += "                              update amount discount = The system will ask you to enter an amount and discount (decimal)\n";
        manual += "                                                       in this format \"[amount] [discount]\" until typing 'done' \n";
        manual += "                              add contact [contact_phone] [contact_name]\n";
        manual += "                              delete contact [contact_phone] [contact_name]\n";
        manual += "                              delete all contacts\n";
        manual += "add agreement [product_id] [supplier_id] = Adds a new product agreement with a supplier. The system will ask you about information needed to this action.\n";
        manual += "                                           If an agreement already exist, the system will update it to the new one.\n";
        manual += "get card [supplier_id] = Information about the supplier will be presented.\n";
        manual += "**All the commands will return an informative message about the command's success/failure.**\n";
        manual += "**The commands are not type sensitive!**\n";
        manual += "===========================================================================================";
        System.out.println(manual);
    }

    /**
     * Adds a new supplier to the system
     */
    public static void addSupplier() {
        System.out.println("Enter the information the following information about the supplier you want to add:");
        System.out.print("Enter supplier name: ");
        String name = scanner.nextLine();

        System.out.print("Enter supplier phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter supplier bank account: ");
        String bankAccount = scanner.nextLine();

        System.out.print("Enter supplier payment condition: ");
        String paymentCondition = scanner.nextLine();

        // add fields
        List<String> fields = makeFieldsList();

        // add amount-discount map
        Map<Integer, Double> amountTodiscount = makeAmountDiscountMap();

        // add contacts list
        System.out.print("Enter contacts [phone] [name] pairs (enter 'done' to finish): ");
        List<String> phones = new ArrayList<>();
        List<String> names = new ArrayList<>();
        String input;
        do {
            System.out.print("Enter phone-name pair: ");
            input = scanner.nextLine();
            String[] PhoneName = input.split(" ");
            phones.add(PhoneName[0]);
            names.add(PhoneName[1]);
        } while (!input.equals("done"));

        // now we choose the supplier type
        System.out.println("Please choose the supplier type:");
        System.out.println("1 - FixedDaysSupplier, 2 - On Order Supplier,  3 - Self Pickup Supplier");
        int type = scanner.nextInt();

        String msg;
        switch (type) {
            case 1: {
                System.out.println("Please choose the supply days (No more than 7 days) and enter '0' to finish:");
                System.out.println(
                        "1 - Sunday, 2 - Monday, 3 - Tuesday, 4 - Wednesday, 5 - Thursday, 6 - Friday, 7 - Saturday");
                List<Integer> days = new ArrayList<Integer>();
                Integer day;
                do {
                    System.out.print("Enter day: ");
                    day = scanner.nextInt();
                    if (!days.contains(day)) {
                        days.add(day);
                        if (days.size() == 7) {
                            System.out.println("All days were inserted");
                        }
                    } else {
                        System.out.println("Day already exists");
                        continue;
                    }
                } while (!day.equals(0) && days.size() < 7);
                msg = ss.addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                        amountTodiscount, names, phones, days);
                System.out.println(msg);
                break;
            }
            case 2: {
                System.out.println("Enter maximum supply days: ");
                int maxDays = scanner.nextInt();
                msg = ss.addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                        amountTodiscount, names, phones, maxDays);
                System.out.println(msg);
                break;
            }
            case 3: {
                System.out.println("Enter the supplier's address: ");
                String address = scanner.nextLine();
                msg = ss.addSelfPickupSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                        amountTodiscount, names, phones, address);
                System.out.println(msg);
                break;
            }
            default: {
                // TODO: maybe we should make a while loop until supplier type is determined
                System.out.println("Unknown supplier type. Try again");
                break;
            }
        }
    }

    /**
     * Makes a fields list
     * 
     * @return
     */
    private static List<String> makeFieldsList() {
        System.out.print("Enter supplier fields (enter 'done' to finish): ");
        String field;
        List<String> fields = new ArrayList<String>();
        do {
            System.out.print("Enter field: ");
            field = scanner.nextLine();
        } while (!field.equals("done"));
        return fields;
    }

    /**
     * Makes a map of the amount discount
     * 
     * @return
     */
    private static Map<Integer, Double> makeAmountDiscountMap() {
        System.out.print("Enter [amount] [discount] pairs (enter 'done' to finish): ");
        Map<Integer, Double> amountTodiscountMap = new HashMap<Integer, Double>();
        String input;
        do {
            System.out.print("Enter amount discount pair: ");
            input = scanner.nextLine();
            String[] AmountDiscount = input.split(" ");
            Integer amount = Integer.parseInt(AmountDiscount[0]);
            Double discount = Double.parseDouble(AmountDiscount[1]);
            if (amount < 0 || discount < 0) {
                System.out.println("Amount and discount cant be negative");
                continue;
            }
            if (AmountDiscount.length > 1) {
                amountTodiscountMap.put(amount, discount);
            }
        } while (!input.equals("done"));
        return amountTodiscountMap;
    }

    /**
     * Deletes an existing supplier from the system
     * 
     * @param commandTokens
     */
    public static void deleteSupplier(String[] commandTokens) {
        String msg = ss.deleteSupplierBaseAgreement(Integer.parseInt(commandTokens[2]));
        System.out.println(msg);
    }

    /**
     * Prints information about an existing supplier
     * 
     * @param commandTokens
     */
    public static void getSupplierCard(String[] commandTokens) {
        String msg = ss.getSupplierCard(Integer.parseInt(commandTokens[2]));
        System.out.println(msg);
    }

    /**
     * Edits an existing supplier
     * 
     * @param commandTokens
     */
    public static void editSupplier(String[] commandTokens) {
        System.out.println("Please enter the edit command:");
        String command = scanner.nextLine();
        String[] editCommandTokens = command.split("");
        Integer supId = Integer.parseInt(commandTokens[2]);
        String msg = "";
        switch (editCommandTokens[0]) {
            case "update": {
                msg = updateSupplierDetails(supId, editCommandTokens[1], editCommandTokens[2]);
                break;
            }
            case "delete": {
                if (editCommandTokens[1].equals("all")) {
                    msg = ss.deleteAllSupplierContacts(supId);
                } else {
                    msg = ss.deleteSupplierContact(supId, editCommandTokens[2], editCommandTokens[3]);
                }
                break;
            }
            case "add": {
                msg = ss.addSupplierContact(supId, editCommandTokens[2], editCommandTokens[3]);
                break;
            }
        }
        System.out.println(msg);
    }

    private static String updateSupplierDetails(int supId, String command, String arg) {
        switch (command) {
            case "name": {
                return ss.setSupplierName(supId, arg);
            }
            case "phone": {
                return ss.setSupplierPhone(supId, arg);
            }
            case "bank": {
                return ss.setSupplierBankAccount(supId, arg);
            }
            case "fields": {
                return ss.setSupplierFields(supId, makeFieldsList());
            }
            case "paymentCondition": {
                return ss.setSupplierPaymentCondition(supId, arg);
            }
            case "amount": {
                return ss.setSupplierAmountToDiscount(supId, makeAmountDiscountMap());
            }
            default: {
                return "Unknown command. Try again";
            }
        }
    }

    /**
     * Adds a new agreement about a specific product with a specific supplier
     * (Updates the agreement if it already exists)
     * 
     * @param commandTokens
     */
    public static void addAgreement(String[] commandTokens) {
        System.out.println("New agreement:");
        Integer productId = Integer.parseInt(commandTokens[2]);
        Integer supplierId = Integer.parseInt(commandTokens[3]);
        // int productSupplierId, int stockAmount,
        // TreeMap<Integer, Double> amountToPrice, String manufacturer
        System.out.println("Enter the product id in the supplier's system:");
        Integer productSupplierId = scanner.nextInt();

        System.out.println("Enter the stock amount that the supplier provides:");
        Integer stockAmount = scanner.nextInt();

        System.out.println("Enter the product's manufacturer:");
        String manufacturer = scanner.nextLine();

        System.out.println("Enter the product's base price:");
        Integer basePrice = scanner.nextInt();

        TreeMap<Integer, Double> amountDiscount = makeAmountDiscountPercentMap();

        String msg = ss.addSupplierProductAgreement(supplierId, productId, productSupplierId, stockAmount, basePrice,
                amountDiscount, manufacturer);
        System.out.println(msg);

    }

    /**
     * Makes a map of the amount discount
     * 
     * @return
     */
    private static TreeMap<Integer, Double> makeAmountDiscountPercentMap() {
        System.out.print("Enter [amount] [discount] pairs (enter 'done' to finish): ");
        System.out.print("**Notice that the discount is a percentage of the total price per unit**");
        TreeMap<Integer, Double> amountTodiscountMap = new TreeMap<Integer, Double>();
        String input;
        do {
            System.out.print("Enter amount discount pair: ");
            input = scanner.nextLine();
            String[] AmountDiscount = input.split(" ");
            Integer amount = Integer.parseInt(AmountDiscount[0]);
            Double discount = Double.parseDouble(AmountDiscount[1]);
            if (amount < 0 || discount < 0) {
                System.out.println("Amount and discount cant be negative");
                continue;
            }
            if (discount > 100) {
                System.out.println("Discount must be a percentage (no more than 100%)");
                continue;
            }
            if (AmountDiscount.length > 1) {
                amountTodiscountMap.put(amount, discount);
            }
        } while (!input.equals("done"));
        return amountTodiscountMap;
    }
}
