package PersentationLayer.Suppliers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import ServiceLayer.Suppliers.SupplierService;

public class SupplierSystem {
    private static SupplierService ss = new SupplierService();

    public static void help() {
        String manual = "";
        manual += "===========================================================================================\n";
        manual += "addSupplier = Adds a new supplier to the system. Enter the information that the system will ask you to about the supplier you want to add.\n";
        manual += "deleteSupplier [supplier_id] = Deletes an existing supplier from the system.\n";
        manual += "editSupplier [supplier_id] = After typing this command, you can edit the supplier information with the following commands: \n";
        manual += "                              updateName [new_name]\n";
        manual += "                              updatePhone [new_phone]\n";
        manual += "                              updateBankAccount [new_bankAccount]\n";
        manual += "                              updateFields = The system will ask you to enter fields one by one until typing 'done'\n";
        manual += "                              updatePaymentCondition [new_paymentCondition]\n";
        manual += "                              updateAmountDiscount = The system will ask you to enter an amount and discount (decimal)\n";
        manual += "                                                       in this format \"[amount] [discount]\" until typing 'done' \n";
        manual += "                              addContact [contact_phone] [contact_name]\n";
        manual += "                              deleteContact [contact_phone] [contact_name]\n";
        manual += "                              deleteAllContacts\n";
        manual += "addAgreement [product_id] [supplier_id] = Adds a new product agreement with a supplier. The system will ask you about information needed to this action.\n";
        manual += "                                           If an agreement already exist, the system will update it to the new one.\n";
        manual += "getCard [supplier_id] = Information about the supplier will be presented.\n";
        manual += "**All the commands will return an informative message about the command's success/failure.**\n";
        manual += "===========================================================================================";
        System.out.println(manual);
    }

    /**
     * Adds a new supplier to the system
     */
    public static void addSupplier(Scanner scanner) {
        System.out.println("Enter the following information about the supplier you want to add:");
        System.out.print("Enter supplier name: ");
        String name = scanner.nextLine();

        System.out.print("Enter supplier phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter supplier bank account: ");
        String bankAccount = scanner.nextLine();

        System.out.print("Enter supplier payment condition: ");
        String paymentCondition = scanner.nextLine();

        // add fields
        List<String> fields = makeFieldsList(scanner);

        // add amount-discount map
        TreeMap<Integer, Double> amountTodiscount = makeAmountDiscountPercentageMap(scanner);

        // add contacts list
        System.out.println("Enter contacts [phone] [name] pairs (enter 'done' to finish): ");
        List<String> phones = new ArrayList<>();
        List<String> names = new ArrayList<>();
        System.out.print("Enter phone-name pair: ");
        String input = scanner.nextLine();

        while (!input.equals("done")) {
            String[] PhoneName = input.split(" ");
            if (PhoneName.length != 2) {
                System.out.println("phone name must be in the format \"[phone] [name]\"!");
                continue;
            }

            phones.add(PhoneName[0]);
            names.add(PhoneName[1]);
            System.out.print("Enter phone-name pair: ");
            input = scanner.nextLine();
        }

        // now we choose the supplier type
        boolean enteredSupplierType = false;
        while (!enteredSupplierType) {
            System.out.println("Please choose the supplier type:");
            System.out.println("1 - FixedDaysSupplier, 2 - On Order Supplier,  3 - Self Pickup Supplier");
            int type = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
            if(type==Integer.MIN_VALUE){
                System.out.println("Wrong format, please try again");
                continue;
            }
            String msg;
            switch (type) {
                case 1: {
                    System.out.println("Please choose the supply days (No more than 7 days) and enter '0' to finish:");
                    System.out.println(
                            "1 - Sunday, 2 - Monday, 3 - Tuesday, 4 - Wednesday, 5 - Thursday, 6 - Friday, 7 - Saturday");
                    List<Integer> days = new ArrayList<Integer>();
                    Integer day;
                    while (true) {
                        System.out.print("Enter day: ");
                        day = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
                        if(day == Integer.MIN_VALUE){
                            System.out.println("Wrong format, please try again");
                            continue;
                        }
                        if (day < 1 || day > 7)
                            break;
                        if (!days.contains(day)) {
                            days.add(day);
                            if (days.size() == 7) {
                                System.out.println("All days were inserted");
                                break;
                            }
                        } else {
                            System.out.println("Day already exists");
                        }
                    }
                    msg = ss.addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                            amountTodiscount, names, phones, days);
                    System.out.println(msg);
                    enteredSupplierType = true;
                    break;
                }
                case 2: {
                    System.out.println("Enter maximum supply days: ");
                    int maxDays = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
                    if(maxDays == Integer.MIN_VALUE){
                        System.out.println("Wrong format, please try again");
                        continue;
                    }
                    msg = ss.addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                            amountTodiscount, names, phones, maxDays);
                    System.out.println(msg);
                    enteredSupplierType = true;
                    break;
                }
                case 3: {
                    System.out.println("Enter the supplier's address: ");
                    String address = scanner.nextLine();
                    msg = ss.addSelfPickupSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                            amountTodiscount, names, phones, address);
                    System.out.println(msg);
                    enteredSupplierType = true;
                    break;
                }
                default: {
                    System.out.println("Unknown supplier type. Try again");
                    enteredSupplierType = false;
                    break;
                }
            }
        }
    }

    /**
     * Makes a fields list
     * 
     * @return
     */
    private static List<String> makeFieldsList(Scanner scanner) {
        System.out.println("Enter supplier fields (enter 'done' to finish): ");
        System.out.print("Enter field: ");
        String field = scanner.nextLine();

        List<String> fields = new ArrayList<String>();
        while (!field.equals("done")) {
            if (fields.contains(field)) {
                System.out.println("Field already exists");
            } else {
                fields.add(field);
            }
            System.out.print("Enter field: ");
            field = scanner.nextLine();
        }
        return fields;
    }

    /**
     * Deletes an existing supplier from the system
     * 
     * @param commandTokens
     */
    public static void deleteSupplier(String[] commandTokens) {
        int supplierId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if(supplierId == Integer.MIN_VALUE){
            System.out.println("Wrong supplier id format, please try again");
            return;
        }
        String msg = ss.deleteSupplierBaseAgreement(supplierId);
        System.out.println(msg);
    }

    /**
     * Prints information about an existing supplier
     * 
     * @param commandTokens
     */
    public static void getSupplierCard(String[] commandTokens) {
        int supplierId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if(supplierId == Integer.MIN_VALUE){
            System.out.println("Wrong supplier id format, please try again");
            return;
        }
        String msg = ss.getSupplierCard(supplierId);
        System.out.println(msg);
    }

    /**
     * Edits an existing supplier
     * 
     * @param commandTokens
     */
    public static void editSupplier(String[] commandTokens, Scanner scanner) {
        if (commandTokens.length != 2) {
            System.out.println("Expected supplier id. Try again");
            return;
        }
        Integer supId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if(supId == Integer.MIN_VALUE){
            System.out.println("Wrong supplier id format, please try again");
            return;
        }

        System.out.println("Please enter the edit command:");
        String command = scanner.nextLine();
        String[] editCommandTokens = command.split(" ");
        
        String msg = "";
        switch (editCommandTokens[0]) {
            case "updateName":
                if (editCommandTokens.length != 2)
                    System.out.println("Expected name. Try again");
                else
                    msg = ss.setSupplierName(supId, editCommandTokens[1]);
                break;
            case "updatePhone":
                if (editCommandTokens.length != 2)
                    System.out.println("Expected phone. Try again");
                else
                    msg = ss.setSupplierPhone(supId, editCommandTokens[1]);
                break;
            case "updateBankAccount":
                if (editCommandTokens.length != 2)
                    System.out.println("Expected bank account. Try again");
                else
                    msg = ss.setSupplierBankAccount(supId, editCommandTokens[1]);
                break;
            case "updateFields":
                msg = ss.setSupplierFields(supId, makeFieldsList(scanner));
                break;
            case "updatePaymentCondition":
                if (editCommandTokens.length != 2)
                    System.out.println("Expected payment condition. Try again");
                else
                    msg = ss.setSupplierPaymentCondition(supId, editCommandTokens[1]);
                break;
            case "updateAmountDiscount":
                msg = ss.setSupplierAmountToDiscount(supId, makeAmountDiscountPercentageMap(scanner));
                break;
            case "deleteContact":
                if (editCommandTokens.length != 3)
                    System.out.println("Expected phone and name. Try again");
                else
                    msg = ss.deleteSupplierContact(supId, editCommandTokens[1], editCommandTokens[2]);
                break;
            case "deleteAllContacts":
                msg = ss.deleteAllSupplierContacts(supId);
                break;
            case "addContact":
                msg = ss.addSupplierContact(supId, editCommandTokens[1], editCommandTokens[2]);
                break;
            default:
                if (editCommandTokens.length != 3)
                    System.out.println("Expected phone and name. Try again");
                else
                    System.out.println("Unknown edit command. Try again");
                break;
        }
        System.out.println(msg);
    }

    /**
     * Adds a new agreement about a specific product with a specific supplier
     * (Updates the agreement if it already exists)
     * 
     * @param commandTokens
     */
    public static void addAgreement(String[] commandTokens, Scanner scanner) {
        if (commandTokens.length != 3) {
            System.out.println("addAgreement expects 3 arguments. Try again");
            return;
        }
        System.out.println("New agreement:");
        Integer productId = tryParseInt(commandTokens[1], Integer.MIN_VALUE);
        if(productId == Integer.MIN_VALUE){
            System.out.println("Wrong format, please try again");
            return;
        }
        Integer supplierId = tryParseInt(commandTokens[2], Integer.MIN_VALUE);
        if(supplierId == Integer.MIN_VALUE){
            System.out.println("Wrong format, please try again");
            return;
        }
        // int productSupplierId, int stockAmount,
        // TreeMap<Integer, Double> amountToPrice, String manufacturer
        System.out.println("Enter the product id in the supplier's system:");
        Integer productSupplierId = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        if(productSupplierId == Integer.MIN_VALUE){
            System.out.println("Wrong format, please try again");
            return;
        }

        System.out.println("Enter the stock amount that the supplier provides:");
        Integer stockAmount = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        if(stockAmount == Integer.MIN_VALUE){
            System.out.println("Wrong format, please try again");
            return;
        }


        System.out.println("Enter the product's base price:");
        Integer basePrice = tryParseInt(scanner.nextLine(), Integer.MIN_VALUE);
        if(basePrice == Integer.MIN_VALUE){
            System.out.println("Wrong format, please try again");
            return;
        }

        TreeMap<Integer, Double> amountDiscount = makeAmountDiscountPercentageMap(scanner);

        String msg = ss.addSupplierProductAgreement(supplierId, productId, productSupplierId, basePrice, stockAmount,
                amountDiscount);
        System.out.println(msg);

    }

    /**
     * Makes a map of the amount discount
     * 
     * @return
     */
    private static TreeMap<Integer, Double> makeAmountDiscountPercentageMap(Scanner scanner) {
        System.out.println(
                "Enter total amount to discount in format of [amount] [discount] pairs (enter 'done' to finish): ");
        System.out.println("**Notice that the discount must be a percentage (0-100)**");
        TreeMap<Integer, Double> amountTodiscountMap = new TreeMap<Integer, Double>();
        System.out.print("Enter amount discount pair: ");
        String input = scanner.nextLine();

        while (!input.equals("done")) {
            String[] AmountDiscount = input.split(" ");
            if (AmountDiscount.length != 2) {
                System.out.println("Invalid amount discount pair. Try again");
                continue;
            }
            Integer amount = tryParseInt(AmountDiscount[0], Integer.MIN_VALUE);
            if(amount == Integer.MIN_VALUE){
                System.out.println("Wrong format, please try again");
                continue;
            }
            Double discount = tryParseDouble(AmountDiscount[1], Double.MIN_VALUE);
            if(discount == Double.MIN_VALUE){
                System.out.println("Wrong format, please try again");
                continue;
            }
            if (amount < 0 || discount < 0) {
                System.out.println("Amount and discount cant be negative");
            }
            if (discount > 100) {
                System.out.println("Discount must be a percentage (no more than 100%)");
            }
            if (AmountDiscount.length > 1) {
                //TODO: maybe ask the user to provide the discount percantage in 0.XX format.
                amountTodiscountMap.put(amount, discount / 100);
            }
            System.out.print("Enter amount discount pair: ");
            input = scanner.nextLine();
        }
        return amountTodiscountMap;
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
}
