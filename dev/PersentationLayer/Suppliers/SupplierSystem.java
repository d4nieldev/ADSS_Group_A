package PersentationLayer.Suppliers;

import java.util.ArrayList;
import java.util.List;
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
            case "help":
                help();
                break;
            case "addSupplier":
                addSupplier();
                break;
            case "addAgreement":
                addAgreement(commandTokens);
                break;
            case "deleteSupplier":
                deleteSupplier(commandTokens);
                break;
            case "editSupplier":
                editSupplier(commandTokens);
                break;
            case "getCard":
                getSupplierCard(commandTokens);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
        System.out.println("press any key to exit");
        scanner.nextLine();
    }

    public static void help() {
        String manual = "";
        manual += "===========================================================================================\n";
        manual += "This is the manual for how to use the suppliers system:\n";
        manual += "help = show the manual\n";
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
    public static void addSupplier() {
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
        List<String> fields = makeFieldsList();

        // add amount-discount map
        TreeMap<Integer, Double> amountTodiscount = makeAmountDiscountPercentageMap();

        // add contacts list
        System.out.println("Enter contacts [phone] [name] pairs (enter 'done' to finish): ");
        List<String> phones = new ArrayList<>();
        List<String> names = new ArrayList<>();
        System.out.print("Enter phone-name pair: ");
        String input = scanner.nextLine();

        while (!input.equals("done")) {
            String[] PhoneName = input.split(" ");
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
            int type = scanner.nextInt();

            String msg;
            switch (type) {
                case 1: {
                    System.out.println("Please choose the supply days (No more than 7 days) and enter '0' to finish:");
                    System.out.println(
                            "1 - Sunday, 2 - Monday, 3 - Tuesday, 4 - Wednesday, 5 - Thursday, 6 - Friday, 7 - Saturday");
                    List<Integer> days = new ArrayList<Integer>();

                    Integer day = 0;
                    do {
                        System.out.print("Enter day: ");
                        day = scanner.nextInt();
                    } while (day < 1 || day > 7);
                    days.add(day);

                    while ((1 <= day && day <= 7) && days.size() < 7) {
                        if (!days.contains(day)) {
                            days.add(day);
                            if (days.size() == 7) {
                                System.out.println("All days were inserted");
                                continue;
                            }
                        } else {
                            System.out.println("Day already exists");
                            continue;
                        }
                        System.out.print("Enter day: ");
                        day = scanner.nextInt();
                    }
                    msg = ss.addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                            amountTodiscount, names, phones, days);
                    System.out.println(msg);
                    enteredSupplierType = true;
                    break;
                }
                case 2: {
                    System.out.print("Enter maximum supply days: ");
                    int maxDays = scanner.nextInt();
                    msg = ss.addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields, paymentCondition,
                            amountTodiscount, names, phones, maxDays);
                    System.out.println(msg);
                    enteredSupplierType = true;
                    break;
                }
                case 3: {
                    System.out.print("Enter the supplier's address: ");
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
    private static List<String> makeFieldsList() {
        System.out.println("Enter supplier fields (enter 'done' to finish): ");
        String field = scanner.nextLine();

        List<String> fields = new ArrayList<String>();
        while (!field.equals("done")) {
            if (fields.contains(field)) {
                System.out.println("Field already exists");
                continue;
            }
            fields.add(field);
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
        String msg = ss.deleteSupplierBaseAgreement(Integer.parseInt(commandTokens[1]));
        System.out.println(msg);
    }

    /**
     * Prints information about an existing supplier
     * 
     * @param commandTokens
     */
    public static void getSupplierCard(String[] commandTokens) {
        String msg = ss.getSupplierCard(Integer.parseInt(commandTokens[1]));
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
        String[] editCommandTokens = command.split(" ");
        Integer supId = Integer.parseInt(commandTokens[1]);
        String msg = "";
        switch (editCommandTokens[0]) {
            case "updateName":
                msg = ss.setSupplierName(supId, editCommandTokens[1]);
                break;
            case "updatePhone":
                msg = ss.setSupplierPhone(supId, editCommandTokens[1]);
                break;
            case "updateBankAccount":
                msg = ss.setSupplierBankAccount(supId, editCommandTokens[1]);
                break;
            case "updateFields":
                msg = ss.setSupplierFields(supId, makeFieldsList());
                break;
            case "updatePaymentCondition":
                msg = ss.setSupplierPaymentCondition(supId, editCommandTokens[1]);
                break;
            case "updateAmountDiscount":
                msg = ss.setSupplierAmountToDiscount(supId, makeAmountDiscountPercentageMap());
                break;
            case "deleteContact":
                msg = ss.deleteSupplierContact(supId, editCommandTokens[1], editCommandTokens[2]);
                break;
            case "deleteAllContacts":
                msg = ss.deleteAllSupplierContacts(supId);
                break;
            case "addContact":
                msg = ss.addSupplierContact(supId, editCommandTokens[1], editCommandTokens[2]);
                break;
            default:
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

        TreeMap<Integer, Double> amountDiscount = makeAmountDiscountPercentageMap();

        String msg = ss.addSupplierProductAgreement(supplierId, productId, productSupplierId, basePrice, stockAmount,
                amountDiscount, manufacturer);
        System.out.println(msg);

    }

    /**
     * Makes a map of the amount discount
     * 
     * @return
     */
    private static TreeMap<Integer, Double> makeAmountDiscountPercentageMap() {
        System.out.println(
                "Enter total amount to discount in format of [amount] [discount] pairs (enter 'done' to finish): ");
        System.out.println("**Notice that the discount must be a percentage (0-100)**");
        TreeMap<Integer, Double> amountTodiscountMap = new TreeMap<Integer, Double>();
        System.out.print("Enter amount discount pair: ");
        String input = scanner.nextLine();

        while (!input.equals("done")) {
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
                // TODO: maybe ask the user to provide the discount percantage in 0.XX format.
                amountTodiscountMap.put(amount, discount / 100);
            }

            System.out.print("Enter amount discount pair: ");
            input = scanner.nextLine();
        }
        return amountTodiscountMap;
    }
}
