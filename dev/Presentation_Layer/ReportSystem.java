package Presentation_Layer;
import Service_Layer.ReportService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportSystem {
    public  static Scanner scanner = new Scanner(System.in);
    public  static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void importInventoryReport(ReportService reportService){
        try {
            System.out.println("please enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
        reportService.importInventoryReport(branchId);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importInventoryReportByCategories(ReportService reportService){
        try {
            System.out.println("please enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            int chose = 0;
            List<Integer>lst = new ArrayList<>();
            while (chose != -1) {
                System.out.println("enter your desire category. -1 if done");
                chose = Integer.parseInt(reader.readLine());
                if (chose != -1)
                    lst.add(chose);
            }
            reportService.importInventoryReportByCategories(branchId,lst);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importExpiredAndFlawsReport(ReportService reportService) {
        try {
            System.out.println("please enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            reportService.importExpiredAndFlawsReport(branchId);
        } catch (Exception e) {
            System.out.println("Error occurred - please try again ");
        }
    }

        public static void importReportByReportId(ReportService reportService) {
            try {
                System.out.println("please enter ReportId");
                int ReportId = Integer.parseInt(reader.readLine());
                reportService.importReportByReportId(ReportId);
            }
            catch (Exception e){
                System.out.println("Error occurred - please try again ");
            }
}

    public static void importDeficientReport(ReportService reportService) {
        try {
            System.out.println("please enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            reportService.importDeficientReport(branchId);
        } catch (Exception e) {
            System.out.println("Error occurred - please try again ");
        }
    }

    public static void importProductReport(ReportService reportService) {
        try {
            System.out.println("please enter branchId");
            int branchId = Integer.parseInt(reader.readLine());
            System.out.println("please enter product code");
            int productCode = Integer.parseInt(reader.readLine());
            reportService.importProductReport(branchId,productCode);
        } catch (Exception e) {
            System.out.println("Error occurred - please try again ");
        }
    }


//    public static void importExpiredProductReport(ReportServiceNew reportService){
//        try{
//        reportService.importExpiredProductReport();
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }
//    public static void importShortageReport(ReportServiceNew reportService){
//        try{
//            reportService.importShortageReport();
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }

//    public static void importProductDiscountHistory(ReportServiceNew reportService){
//        try{
//            System.out.println("please enter branchId");
//            int branchId = Integer.parseInt(reader.readLine());
//            System.out.println("Enter product code");
//            int code = Integer.parseInt(reader.readLine());
//            reportService.importProductDiscountHistory(branchId,code);
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }
//    public static void importGeneralProductReport(ReportServiceNew reportService){
//        try {
//        System.out.println("Enter general product code");
//        int code = Integer.parseInt(reader.readLine());
//        reportService.importGeneralProductReport(code);
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }

//    public static void importGeneralProductReport(ReportServiceNew reportService){
//        try {
//        System.out.println("Enter general product code");
//        int code = Integer.parseInt(reader.readLine());
//        reportService.importGeneralProductReport(code);
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }

//    public static void importInventoryReportByCategories(ReportServiceNew reportService){
//        try {
//
//
//            List<Integer> lst = new ArrayList<>();
//            int chose = 0;
//            while (chose != -1) {
//                System.out.println("enter your desire category id  . -1 if done");
//                chose = Integer.parseInt(reader.readLine());
//
//                lst.add(chose);
//            }
//            reportService.importInventoryReportByCategories(lst);
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }

//    public static void  importFlawProductsReport(ReportServiceNew reportService){
//        try{
//        reportService.importFlawProductsReport();
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }
//
//    public static void importProductSellPriceReport(ReportServiceNew reportService){
//        try{
//        System.out.println("Enter general product code");
//        int code = Integer.parseInt(reader.readLine());
//        reportService.importProductSellPriceReport(code);
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }


//    public static void importFutureExpiredProductds(ReportServiceNew reportService) {
//        try{
//            System.out.println("please enter the last date you want to check in format - YYYY-MM-DD ");
//            String date = reader.readLine();
//            reportService.importFutureExpiredProduct(LocalDate.parse(date));
//        }
//        catch (Exception e){
//            System.out.println("Error occurred - please try again ");
//        }
//    }

}

