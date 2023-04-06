package Business_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportController {

    List<Report> inventoryReport;
    List<Report> deficiencyReport;
    ProductController productController ;
    CategoryController categoryController;



    public ReportController(ProductController productController)
    {
        this.productController = productController;
        this.inventoryReport = new ArrayList<>();
        this.deficiencyReport = new ArrayList<>();
        this.categoryController = CategoryController.getInstance();
    }


    public void importInventoryReport ()
    {
        System.out.println("------------------Inventory Report------------------");
        System.out.format("%-10s%-15s%-10s%-20s%-20s%-20s%-15s%-15s%n", "NO.", "name", "code", "price", "total_quantity", "min_quantity", "manufacturer" , "category");

        List<GeneralProduct> allGeneralProducts = productController.getAllGeneralProducts();
        int index = 0;
        for (GeneralProduct gp : allGeneralProducts)
        {
//            System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "NO.", "name", "code", "price", "total_quantity", "min_quantity", "manufacturer" , "category");
//            System.out.format("%-10s%-10d%-10b%-10f%-10s%-10f%-10d%-10d%n", index, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName().toString());
            System.out.format("%-10d%-15s%-10d%-20f%-20d%-20d%-15s%-15s%n", index, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName().toString());

            index ++;
        }
        System.out.println("----------------------------------------------------");


    }


    public void importFlawReport()
    {
        System.out.println("------------------Flow Report------------------");
        System.out.format("%-10s%-10s%-10s%-20s%-20s%-20s%-20s%n", "NO.", "name", "code", "id", "manufacturer" ,"category","Flow description");

        HashMap<GeneralProduct,Integer> allFlowProducts = productController.getAllFlawProducts();
        HashMap<GeneralProduct,Integer> allExpiredProducts = productController.getAllExpiredProducts();
        int index = 0;
        //iterate on each general product all its flow products and print their details
        for(GeneralProduct gp : allFlowProducts.keySet())
        {
            HashMap<Integer,String> flowProducts = gp.getAllFlowProducts();
            for(int id: flowProducts.keySet())
            {
                System.out.format("%-10d%-10s%-10d%-20d%-20s%-20s%-20s%n", index++, gp.getName(), gp.getCode(),id,gp.getManufacturer(),gp.getCategory().getName(),flowProducts.get(id));
            }

        }
        //iterate on all expired product anf print the same details-on flow description it will be : "EXPIRED"
        for(GeneralProduct gp : allExpiredProducts.keySet())
        {
            List<Integer> expiredProducts = gp.getAllExpiredProducts();
            for(int id: expiredProducts)
            {

                System.out.format("%-10s%-10d%-10b%-10f%-10s%-10f%-10d%-10d%n", index++, gp.getName(), gp.getCode(),id,gp.getManufacturer(),gp.getCategory().getName(),"!!EXPIRED!!");
            }

        }

        System.out.println("----------------------------------------------------");

    }

    public void importFutureExpiredProducts(LocalDate dateToBeExpired){

        System.out.println("------------------Future Expired Report------------------");

        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "NO.", "name", "code", "manufacturer" ,"category","All ids");
        List<Supply> futureExpired = productController.getAllFutureExpiredProducts(dateToBeExpired);
        int index = 0;
        for(Supply sp : futureExpired){
            GeneralProduct gp = sp.getGeneralProduct();
            System.out.format("%-10s%-10d%-10b%-10f%-10s%-10f%-10d%-10d%n", index++, gp.getName(), gp.getCode(),gp.getManufacturer(),gp.getCategory().getName(),sp.getIds());

        }
    }

    public void importInventoryReportByCategoried(List<Integer> categoriesIds)
    {
        System.out.println("------------------Inventory Report------------------");
        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "NO.", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category");
        List<Category> categories = categoryController.getCategoriesByIds(categoriesIds);
        List<GeneralProduct> allGeneralProducts = productController.getAllProductByCategories(categories);
        int index = 0;
        for (GeneralProduct gp : allGeneralProducts)
        {
            System.out.format("%-10d%-10s%-10d%-10f%-10d%-10d%-10s%-10s%n", index++, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName());
        }
        System.out.println("----------------------------------------------------");
    }


    public void importSpecificProductReport(int code, int id) {
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

        System.out.println("----------------------------------------------------");

    }
    public void importGeneralProductReport(int code){

        System.out.println("------------------General Product Report------------------");

        GeneralProduct gp = productController.getGeneralProductByCode(code);
        System.out.format("%-10s%-10s%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category","shop amount","storage amount");
        System.out.format("%-10s%-10d%-10f%-20d%-20d%-20s%-20s%-20d%-20d%n",  gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName(),gp.getShop_quantity(),gp.getStorage_quantity());

    }

    public void importExpiredProductReport()
    {
        System.out.println("------------------Expired Products Report------------------");
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
    }

    public void importShortageReport() {
        System.out.println("------------------Shortage Products Report------------------");
        System.out.format("%-10s%-10s%-10s%-10s%-10s%-10s%-10s%-10s%n", "NO.", "name", "code", "price", "total_quantity","min_quantity", "manufacturer" ,"category","");

        List<GeneralProduct> shortageProducts = productController.getShortageProducts();
        int index = 0;
        for(GeneralProduct gp : shortageProducts){
            System.out.format("%-10d%-10d%-10b%-10f%-10s%-10f%-10d%-10d%-10s%n", index++, gp.getName(), gp.getCode(),gp.getCurrentPrice(), gp.getTotal_quantity(), gp.getMin_quantity(), gp.getManufacturer(), gp.getCategory().getName(),"|");
        }
        System.out.println("----------------------------------------------------");
    }

    public void getProductDiscountHistory(int code){
        System.out.println("------------------product discount history Report------------------");
        System.out.format("%-10s%-10s%-10s%-20s%-20s%-20s%-20s%n", "NO.","name", "code", "price","discount percentage", "start date","end date");
        int index = 0;
        List<Discount> lst = productController.getProductDiscountHistory(code);
        GeneralProduct gp = productController.getGeneralProductByCode(code);
        for(Discount discount : lst){
            double price = gp.getPrice()* (1 - discount.getDiscount_percentage()/100);
            System.out.format("%-10d%-10s%-10d%-20f%-20f%-20s%-20s%n", index, gp.getName(), code, price, discount.getDiscount_percentage(), discount.getStart_date(), discount.getEnd_date());
            index++;
        }

        System.out.println("----------------------------------------------------");
    }
}
