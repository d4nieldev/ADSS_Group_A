package PersentationLayer.CLI;

import java.util.Scanner;
import PersentationLayer.CLI.InventorySuppliers.BranchSystem;
import PersentationLayer.CLI.Suppliers.ReservationSystem;
import PersentationLayer.CLI.Suppliers.SupplierSystem;
import PersentationLayer.CLI.inventory.ProductSystem;
import PersentationLayer.CLI.inventory.ReportSystem;

public class CLI {
    public static void activate(String role) {
        Scanner scanner = new Scanner(System.in);
//            String[] commandTokens = scanner.nextLine().split(" ");
            if(role.equals("StoreManager")) {
                help(role);
                while (true) {
                    System.out.print("> ");
                    String[] commandTokens = scanner.nextLine().split(" ");
                    switch (commandTokens[0]) {
                        case "help":
                            help(role);
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
                        case "addPeriodicReservation":
                            ReservationSystem.addPeriodicReservation(scanner, commandTokens);
                            break;
                        case "updatePeriodicReservation":
                            ReservationSystem.updatePeriodicReservation(scanner, commandTokens);
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
                        case "addBranch":
                            BranchSystem.addNewBranch(scanner);
                            break;
                        case "addProductBranch":
                            BranchSystem.addNewProductBranch(scanner);
                            break;
                        case "1":
                            ProductSystem.addNewProduct();
                            break;
                        case "2":
                            ProductSystem.addNewCategory();
                            break;
                        case "3":
                            ProductSystem.sellProduct();
                            break;
                        case "4":
                            ProductSystem.setDiscountByCategories();
                            break;
                        case "5":
                            ProductSystem.setDiscountByProducts();
                            break;
                        case "6":
                            ProductSystem.reportFlawProduct();
                            break;

                        // Dealing with reports
                        // =========================s====================================
                        case "7":
                            ReportSystem.importInventoryReport();
                            break;
                        case "8":
                            ReportSystem.importExpiredAndFlawsReport();
                            break;
                        case "9":
                            ReportSystem.importDeficientReport();
                            break;
                        case "10":
                            ReportSystem.importInventoryReportByCategories();
                            break;
                        case "11":
                            ReportSystem.importReportByReportId();
                            break;
                        case "12":
                            ReportSystem.importProductReport();
                            break;
                        case "exit":
                            return;
                        default:
                            System.out.println("Unknown command - \"" + commandTokens[0] + "\"");
                            break;
                    }
                }
            }
            else if(role.equals("ManageStorage")) {
                help(role);
                while (true) {
                    System.out.print("> ");
                    String[] commandTokens = scanner.nextLine().split(" ");
                    switch (commandTokens[0]) {
                        case "help":
                            help(role);
                            break;
                        case "addBranch":
                            BranchSystem.addNewBranch(scanner);
                            break;
                        case "addProductBranch":
                            BranchSystem.addNewProductBranch(scanner);
                            break;
                        case "1":
                            ProductSystem.addNewProduct();
                            break;
                        case "2":
                            ProductSystem.addNewCategory();
                            break;
                        case "3":
                            ProductSystem.sellProduct();
                            break;
                        case "4":
                            ProductSystem.setDiscountByCategories();
                            break;
                        case "5":
                            ProductSystem.setDiscountByProducts();
                            break;
                        case "6":
                            ProductSystem.reportFlawProduct();
                            break;
                        case "exit":
                            return;
                        default:
                            System.out.println("Unknown command - \"" + commandTokens[0] + "\"");
                            break;
                    }

                }
            }
            else if (role.equals("SuppliersManager")) {
                help(role);
                while (true) {
                    System.out.print("> ");
                    String[] commandTokens = scanner.nextLine().split(" ");
                    switch (commandTokens[0]) {
                        case "help":
                            help(role);
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
                        case "addPeriodicReservation":
                            ReservationSystem.addPeriodicReservation(scanner, commandTokens);
                            break;
                        case "updatePeriodicReservation":
                            ReservationSystem.updatePeriodicReservation(scanner, commandTokens);
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
        }


    private static void help(String role) {
        System.out.println("help = show the manual");
        if(role.equals("StoreManager")) {
            System.out.println("========== Suppliers Menu ==========");
            ReservationSystem.help();
            SupplierSystem.help();
            System.out.println("========== Inventory menu ==========");
            BranchSystem.help();
            ProductSystem.getMenu();
            System.out.println("exit = exit the program");
            // Bdika
        }
        else if(role.equals("ManageStorage")){
            System.out.println("========== Inventory menu ==========");
            BranchSystem.help();
            ProductSystem.getKeeperMenu();
            System.out.println("exit = exit the program");
        } else if (role.equals("SuppliersManager")) {
            System.out.println("========== Suppliers Menu ==========");
            ReservationSystem.help();
            SupplierSystem.help();
            System.out.println("exit = exit the program");
        }
    }




}
