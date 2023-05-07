package Business_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportController1 {

    List<Report1> inventoryReport1;
    List<Report1> deficiencyReport1;
    ProductController productController ;
    CategoryController1 categoryController1;



    public ReportController1(ProductController productController)
    {
        this.productController = productController;
        this.inventoryReport1 = new ArrayList<>();
        this.deficiencyReport1 = new ArrayList<>();
        this.categoryController1 = CategoryController1.getInstance();
    }


public void importInventoryReport() {
    System.out.println("===============================================");
    System.out.println("          Inventory Report");
    System.out.println("===============================================");
    System.out.printf("%-5s%-20s%-10s%-15s%-15s%-15s%-20s%-15s%n", "NO.", "Product Name", "Code", "Price", "Total Qty", "Min Qty", "Manufacturer", "Category");

    List<GeneralProduct> allGeneralProducts = productController.getAllGeneralProducts();
    int index = 1;
    for (GeneralProduct gp : allGeneralProducts) {
        System.out.printf("%-5d%-20s%-10d%-15.2f%-15d%-15d%-20s%-15s%n", index, gp.getName(), gp.getCode(), gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName().toString());

        index++;
    }

    System.out.println("===============================================");
}
public void importFlawReport() {
    // Print header
    System.out.println("====================================================");
    System.out.println("                     FLOW REPORT                     ");
    System.out.println("====================================================");

    // Print column headers
    System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", "NO.", "NAME", "CODE", "ID", "MANUFACTURER" ,"CATEGORY", "FLOW DESCRIPTION");

    // Get all flow and expired products
    HashMap<GeneralProduct,Integer> allFlowProducts = productController.getAllFlawProducts();
    HashMap<GeneralProduct,Integer> allExpiredProducts = productController.getAllExpiredProducts();
    int index = 0;

    // Iterate through each general product and print its flow products
    for (GeneralProduct gp : allFlowProducts.keySet()) {
        HashMap<Integer, String> flowProducts = gp.getAllFlowProducts();
        for (int id : flowProducts.keySet()) {
            System.out.format("%-10s%-20s%-15s%-15s%-25s%-25s%-25s%n", index++, gp.getName(), gp.getCode(), id, gp.getManufacturer(), gp.getCategory().getName(), flowProducts.get(id));
        }
    }

    // Iterate through all expired products and print the same details, with "EXPIRED" as the flow description
    for (GeneralProduct gp : allExpiredProducts.keySet()) {
        List<Integer> expiredProducts = gp.getAllExpiredProducts();
        for (int id : expiredProducts) {
            System.out.format("%-10d%-20s%-15d%-15s%-25s%-25s%-25s%n", index++, gp.getName(), gp.getCode(), id, gp.getManufacturer(), gp.getCategory().getName(), "!!!EXPIRED!!!");
        }
    }

    // Print footer
    System.out.println("====================================================");
}

    public void importFutureExpiredProducts(LocalDate dateToBeExpired){

        System.out.println("===============================================");
        System.out.println("             Future Expired Report");
        System.out.println("===============================================");


        System.out.format("%-10s%-10s%-10s%-20s%-20s%-20s%n", "NO.", "name", "code", "manufacturer" ,"category","All ids");
        List<Supply> futureExpired = productController.getAllFutureExpiredProducts(dateToBeExpired);
        int index = 0;
        for(Supply sp : futureExpired){
            GeneralProduct gp = sp.getGeneralProduct();
            System.out.format("%-10d%-10s%-10d%-20s%-20s%-20s%n", index++, gp.getName(), gp.getCode(),gp.getManufacturer(),gp.getCategory().getName(),sp.getIds().toString());

        }
        System.out.println("====================================================");
    }


    /**
     *
     * @param categoriesIds
     */
    public void importInventoryReportByCategoryId(List<Integer> categoriesIds)
    {
        System.out.println("===============================================");
        System.out.println("                Inventory Report");
        System.out.println("===============================================");

        System.out.format("%-10s%-20s%-10s%-20s%-20s%-20s%-20s%-20s%n", "NO.", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category");
        List<Category1> categories = categoryController1.getCategoriesByIds(categoriesIds);
        List<GeneralProduct> allGeneralProducts = productController.getAllProductByCategories(categories);
        int index = 0;
        for (GeneralProduct gp : allGeneralProducts)
        {
            System.out.format("%-10d%-20s%-10d%-20f%-20d%-20d%-20s%-20s%n", index++, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName());
        }
        System.out.println("====================================================");
    }


    public void importSpecificProductReport(int code, int id) {
        System.out.println("===============================================");
        System.out.println("            Specific Product Report");
        System.out.println("===============================================");
        System.out.println("------------------Specific Product Report------------------");
        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "name", "code", "price",  "manufacturer" ,"category","Location");

        GeneralProduct gp = productController.getGeneralProductByCode(code);
        boolean onShelf = gp.getOnShelf().contains(id);
        if(onShelf){
            System.out.format("%-10s%-10d%-10b%-10f%-10s%-10f%-10d%-10d%n", gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getManufacturer(), gp.getCategory().getName(),"Shop");
        }
        else{
//            System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "name", "code", "price",  "manufacturer" ,"category","Location");
            System.out.format("%-10s%-10d%-10b%-10f%-10s%-10f%-10d%-10d%n", gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getManufacturer(), gp.getCategory().getName(),"Storage");
        }

        System.out.println("====================================================");

    }
    public void importGeneralProductReport(int code){
        System.out.println("===============================================");
        System.out.println("            General Product Report");
        System.out.println("===============================================");


        GeneralProduct gp = productController.getGeneralProductByCode(code);
        System.out.format("%-10s%-10s%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category","shop amount","storage amount");
        System.out.format("%-10s%-10d%-10f%-20d%-20d%-20s%-20s%-20d%-20d%n",  gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName(),gp.getShop_quantity(),gp.getStorage_quantity());

        System.out.println("====================================================");
    }

    public void importExpiredProductReport()
    {
        System.out.println("===============================================");
        System.out.println("            Expired Products Report");
        System.out.println("===============================================");

        System.out.format("%-10s%-15s%-10s%-20s%-20s%-20s%-15s%n", "NO.", "name", "code", "id", "manufacturer" ,"category","Expire Date");
        productController.findExpiredProducts();
        HashMap<GeneralProduct,Integer> allExpiredProducts = productController.getAllExpiredProducts();
        int index = 0;
        for(GeneralProduct gp : allExpiredProducts.keySet())
        {
            List<Integer> expiredProducts = gp.getAllExpiredProducts();
            for(int id: expiredProducts)
            {
                Supply sp = productController.getSupplyByCodeId(gp.getCode(),id);
                System.out.format("%-10d%-15s%-10d%-20s%-20s%-20s%-15s%n", index++, gp.getName(), gp.getCode(),id,gp.getManufacturer(),gp.getCategory().getName(),sp.getExpiredDate());
            }

        }
        System.out.println("====================================================");
    }

    public void importShortageReport() {
        System.out.println("===============================================");
        System.out.println("           Shortage Products Report");
        System.out.println("===============================================");

        System.out.format("%-10s%-10s%-10s%-15s%-20s%-20s%-20s%-20s%n", "NO.", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category","");

        List<GeneralProduct> shortageProducts = productController.getShortageProducts();
        int index = 0;
        for(GeneralProduct gp : shortageProducts){
            System.out.format("%-10d%-10s%-10d%-15f%-20d%-20d%-20s%-20s%n", index++, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName());
        }
        System.out.println("====================================================");
    }

    public void getProductDiscountHistoryReport(int code){
        System.out.println("===============================================");
        System.out.println("        product discount history Report");
        System.out.println("===============================================");
        System.out.println("------------------product discount history Report------------------");
        System.out.format("%-10s%-10s%-10s%-20s%-25s%-20s%-20s%n", "NO.","Name", "Code", "Price","Discount percentage", "Start date","End date");
        int index = 0;
        List<Discount1> lst = productController.getProductDiscountHistory(code);
        GeneralProduct gp = productController.getGeneralProductByCode(code);
        for(Discount1 discount1 : lst){
            double price = gp.getPrice()* (1 - discount1.getDiscount_percentage()/100);
            System.out.format("%-10d%-10s%-10d%-20f%-25f%-20s%-20s%n", index, gp.getName(), code, price, discount1.getDiscount_percentage(), discount1.getStart_date(), discount1.getEnd_date());
            index++;
        }

        System.out.println("====================================================");
    }

    public void importProductSellPriceReport(int code){
        System.out.println("===============================================");
        System.out.println("        product sell-price Report");
        System.out.println("===============================================");

        System.out.format("%-10s%-10s%-10s%-20s%-20s%n", "NO.","name", "code", "buy price","sell price");
        int index = 0;
        HashMap<Integer,Double> sellPrice  = productController.getSellPrice(code);
        GeneralProduct gp = productController.getGeneralProductByCode(code);
        for(int id : sellPrice.keySet()){

            System.out.format("%-10d%-10s%-10d%-20f%-20f%n", index, gp.getName(), code, productController.getBuyPrice(code,id),sellPrice.get(id));
            index++;
        }

        System.out.println("====================================================");
    }
}
