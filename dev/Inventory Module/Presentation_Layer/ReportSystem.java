package Presentation_Layer;

import Service_Layer.ReportService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportSystem {
    public  static Scanner scanner = new Scanner(System.in);
    public  static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void importInventoryReport(ReportService reportService){
        try {
        reportService.importInventoryReport();
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importExpiredProductReport(ReportService reportService){
        try{
        reportService.importExpiredProductReport();
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importShortageReport(ReportService reportService){
        try{
            reportService.importShortageReport();
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }

    public static void importProductDiscountHistory(ReportService reportService){
        try{
        System.out.println("Enter general product code");
        int code = Integer.parseInt(reader.readLine());
        reportService.importProductDiscountHistory(code);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importGeneralProductReport(ReportService reportService){
        try {
        System.out.println("Enter general product code");
        int code = Integer.parseInt(reader.readLine());
        reportService.importGeneralProductReport(code);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
    public static void importInventoryReportByCategories(ReportService reportService){
        try {


            List<Integer> lst = new ArrayList<>();
            int chose = 0;
            while (chose != -1) {
                System.out.println("enter your desire category id  . -1 if done");
                chose = Integer.parseInt(reader.readLine());

                lst.add(chose);
            }
            reportService.importInventoryReportByCategories(lst);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }

    public static void  importFlawProductsReport(ReportService reportService){
        try{
        reportService.importFlawProductsReport();
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }

    public static void importProductSellPriceReport(ReportService reportService){
        try{
        System.out.println("Enter general product code");
        int code = Integer.parseInt(reader.readLine());
        reportService.importProductSellPriceReport(code);
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }


    public static void importFutureExpiredProductds(ReportService reportService) {
        try{
            System.out.println("please enter the last date you want to check in format - YYYY-MM-DD ");
            String date = reader.readLine();
            reportService.importFutureExpiredProduct(LocalDate.parse(date));
        }
        catch (Exception e){
            System.out.println("Error occurred - please try again ");
        }
    }
}