package PersentationLayer.inventory;

import DataAccessLayer.Repository;

import java.util.Scanner;

public class Program1 {

    public static void main(String[] args) throws Exception {
        ProductSystem.getStart();
        applyData.setup();


        Scanner scanner = new Scanner(System.in);

        int choose = scanner.nextInt();
        while (choose != -1) {
            //Dealing with products
            //=============================================================
            switch (choose) {
                case 1:
                    ProductSystem.addNewProduct();
                    break;
                case 2:
                    ProductSystem.addNewCategory();
                    break;
                case 3:
                    ProductSystem.sellProduct();
                    break;
                case 4:
                    ProductSystem.setDiscountByCategories();
                    break;
                case 5:
                    ProductSystem.setDiscountByProducts();
                    break;
                case 6:
                    ProductSystem.reportFlawProduct();
                    break;

                //Dealing with reports
                //=========================s====================================
                case 7:
                    ReportSystem.importInventoryReport();
                    break;
                case 8:
                    ReportSystem.importExpiredAndFlawsReport();
                    break;
                case 9:
                    ReportSystem.importDeficientReport();
                    break;
                case 10:
                    ReportSystem.importInventoryReportByCategories();
                    break;
                case 11:
                    ReportSystem.importReportByReportId();
                    break;
                case 12:
                    ReportSystem.importProductReport();
                    break;
                case 13:
                    ProductSystem.addPeriodicReservation();
                case 14:
                    //TODO:Change periodic Reservation

                case 0:
                    ProductSystem.getMenu();
                    break;
                case -1:
                    Repository.getInstance().DELETE_ALL_DATA();
                    System.exit(-1);
                default:
                    System.out.println("Invalid input");
                    break;

                // case 3:
                // ProductsSystem.reciveSupply(productService);
                // break;
                // case 9:
                //// ReportSystem.importProductDiscountHistory(reportService);
                // break;
                // case 11:
                // ReportSystem.importFlawProductsReport(reportService);
                // break;
                // case 12:
                // ReportSystem.importInventoryReportByCategories(reportService);
                // break;
                // case 13:
                // ReportSystem.importProductSellPriceReport(reportService);
                // break;
                // case 15:
                // ReportSystem.importFutureExpiredProductds(reportService);
                // break;
                // case 17:
                // ReportSystem.importInventoryReportByCategories(reportService);
                // break;

            }
            System.out.println("please choose next action, 0 for menu");
            choose = scanner.nextInt();
        }
        // test
    }
}
