package PersentationLayer.inventory;

import BusinessLayer.Inventory.*;
import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Product;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.SupplierController;
import DataAccessLayer.DAOs.CategoryDAO;
import DataAccessLayer.DAOs.ProductBranchDAO;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.CategoryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ProductDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class applyData {
    private static ProductController productController = ProductController.getInstance();
    private static CategoryController categoryController =CategoryController.getInstance();
    private static BranchController branchController = BranchController.getInstance();

    /**
     * Creates the environment for the tests.
     * 2 products and 2 suppliers. Both produce both products.
     *
     * for supplier 0 product 0:
     * stockAmount = 100, basePrice = 100.0
     * when buying 50 - 10% discount
     * when buying 70 - 30% discount
     *
     * for supplier 0 product 1:
     * stockAmount = 50, basePrice = 200.0
     * when buying 10 - 10% discount
     *
     * for supplier 1 product 0:
     * stockAmount = 200, basePrice = 90.0
     * when buying 70 - 10% discount
     *
     * for supplier 1 product 1:
     * stockAmount = 30, basePrice = 100.0
     *
     * @throws Exception
     */
    public static void setup() throws Exception {
        try {
            createProducts();
            initial();
            createSupplier0();
            createSupplier1();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }
    }
    private static void initial() throws Exception {

//        addSuppliers();
        branchController.addBranch(1,"branch1",0);
        Branch branch = branchController.getBranchById(1);

        categoryController.addNewCategory("Tnuva"); // id -
//        Category tnuva = categoryController.getCategoryById(0);
//        categoryController.addNewCategory("Milks drinks",tnuva); // id -1
//        Category milkDrinks =categoryController.getCategoryById(1);
//        CategoryDTO categoryDTO0 = CategoryDAO.getInstance().getById(0);
//        CategoryDTO categoryDTO1 = CategoryDAO.getInstance().getById(1);
//
//        ProductDTO p1DTO = new ProductDTO(1,"Milk","Tnuva",categoryDTO1);
//        ProductDTO p2DTO = new ProductDTO(2,"Koteg","Tnuva",categoryDTO0);
////        Product p1 = new Product(p1DTO);
////        Product p2 = new Product(p2DTO);
//        productController.addProduct(p1DTO); // milk
//        productController.addProduct(p2DTO); // koteg
//
//
//
//        SpecificProductDTO specificProductDTO1 = new SpecificProductDTO(1,0,1,15, LocalDate.now().plusDays(2)); //Flow
//        SpecificProductDTO specificProductDTO2 = new SpecificProductDTO(2,0,1,15, LocalDate.now().plusDays(2));
//        SpecificProductDTO specificProductDTO3 = new SpecificProductDTO(3,0,1,15, LocalDate.now().minusDays(2));
//
//        HashMap<Integer,SpecificProductDTO> hashMilk = new HashMap<>();
//        hashMilk.put(specificProductDTO1.getSpecificId(),specificProductDTO1);
//        hashMilk.put(specificProductDTO2.getSpecificId(),specificProductDTO2);
//        hashMilk.put(specificProductDTO3.getSpecificId(),specificProductDTO3);
//        ProductBranchDTO productBranchDTO1 = new ProductBranchDTO(p1DTO,1,20,3,100,hashMilk); //milk
//
//
//        SpecificProductDTO specificProductDTO11 = new SpecificProductDTO(4,1,1,5, LocalDate.now().plusDays(2));
//        SpecificProductDTO specificProductDTO22 = new SpecificProductDTO(5,1,1,5, LocalDate.now().plusDays(2));
//        SpecificProductDTO specificProductDTO33 = new SpecificProductDTO(6,1,1,5, LocalDate.now().minusDays(2));
//
//        HashMap<Integer,SpecificProductDTO> hashKoteg = new HashMap<>();
//
//        hashKoteg.put(specificProductDTO11.getSpecificId(),specificProductDTO11);
//        hashKoteg.put(specificProductDTO22.getSpecificId(),specificProductDTO22);
//        hashKoteg.put(specificProductDTO33.getSpecificId(),specificProductDTO33);
//        ProductBranchDTO productBranchDTO2 = new ProductBranchDTO(p2DTO,1,5,5,100,hashKoteg);
//
//        ProductBranch productBranchMilk = new ProductBranch(productBranchDTO1);
//        ProductBranch productBranchKoteg = new ProductBranch(productBranchDTO2);
//        branch.addNewProductBranch(productBranchDTO1);
//        branch.addNewProductBranch(productBranchDTO2);
        ProductBranch productBranch01 =branchController.addNewProductBranch(0,0,null,20,5,50);
        ProductBranch productBranch02 =branchController.addNewProductBranch(1,0,null,20,5,50);
//        productBranch01.receiveSupply(1,15,LocalDate.now().plusMonths(1),1);
        branchController.receiveSupply(0,5,15,LocalDate.now().plusDays(2),0);
        branchController.receiveSupply(0,5,15,LocalDate.now().minusDays(2),0);

//        ProductBranch productBranchMilk = branchController.addNewProductBranch(1,productBranchDTO1);
//        ProductBranch productBranchKoteg = branchController.addNewProductBranch(1,productBranchDTO2);

//        branch.reportFlawProduct(1,1,"FlowProduct!!!");
    }


    private static void createSupplier0() throws Exception {
        String name = "Supplier 0";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = createList("Health", "Food");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        List<String> contactNames = createList("John Doe", "Jane Doe");
        List<String> contactPhones = createList("1524973302", "1678420619");
        Integer maxSupplyDays = 1;

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);

        int supplierId = 0;
        int productShopId = 0;
        int productSupplierId = 123;
        int stockAmount = 100;
        double basePrice = 100.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(50, "0.1%");
        amountToDiscount.put(70, "0.3%");

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

        productShopId = 1;
        productSupplierId = 142;
        stockAmount = 50;
        basePrice = 200.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(10, "0.1%");

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

    }
    private static void createSupplier1() throws Exception {
        String name = "Supplier 1";
        String phone = "1234567890";
        String bankAccount = "1234567890";
        List<String> fields = createList("Health", "Food");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        List<String> contactNames = createList("John Doe", "Jane Doe");
        List<String> contactPhones = createList("1524973302", "1678420619");
        List<Integer> days = createList((getDay() + 3) % 7); // make sure this supplier is slower

        SupplierController.getInstance().addFixedDaysSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, days);

        int supplierId = 1;
        int productShopId = 0;
        int productSupplierId = 148;
        int stockAmount = 200;
        double basePrice = 90.0;
        amountToDiscount = new TreeMap<>();
        amountToDiscount.put(70, "0.1%");

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);

        productShopId = 1;
        productSupplierId = 2;
        stockAmount = 30;
        basePrice = 100;
        amountToDiscount = new TreeMap<>();

        SupplierController.getInstance().addSupplierProductAgreement(supplierId, productShopId, productSupplierId,
                stockAmount, basePrice, amountToDiscount);
    }
    private static void createProducts() throws SQLException {
        BranchController.getInstance().addBranch(0, "Ashkelon", 0);
        CategoryController.getInstance().addNewCategory("Cat1");
        Category category = categoryController.getCategoryById(0);
        CategoryController.getInstance().addNewCategory("Cat2",category);
        ProductController.getInstance().addProduct(0, "Product 0", "Manufacturer 0", 0);
        ProductController.getInstance().addProduct(1, "Product 1", "Manufacturer 1", 1);
        ProductDTO p1 = ProductsDAO.getInstance().getById(0);
        ProductDTO p2 = ProductsDAO.getInstance().getById(1);

//        SpecificProduct specificProduct1 = new SpecificProduct(Global.getNewSpecificId(),15,LocalDate.now().plusDays(2),0);
//        SpecificProduct specificProduct2 = new SpecificProduct(Global.getNewSpecificId(),15,LocalDate.now().plusDays(2),0);
//        SpecificProduct specificProduct3 = new SpecificProduct(Global.getNewSpecificId(),15,LocalDate.now().minusDays(2),0);
//
//        SpecificProductDTO specificProductDTO2 = new SpecificProductDTO(Global.getNewSpecificId(),0,0,15, LocalDate.now().plusDays(2));
//        SpecificProductDTO specificProductDTO3 = new SpecificProductDTO(Global.getNewSpecificId(),0,0,15, LocalDate.now().minusDays(2));

        HashMap<Integer,SpecificProductDTO> hashMilk = new HashMap<>();
//        hashMilk.put(specificProductDTO1.getSpecificId(),specificProductDTO1);
//        hashMilk.put(specificProductDTO2.getSpecificId(),specificProductDTO2);
//        hashMilk.put(specificProductDTO3.getSpecificId(),specificProductDTO3);
//        ProductBranchDTO productBranchDTO1 = new ProductBranchDTO(p1,0,20,10,50,hashMilk);
//        BranchController.getInstance().addNewProductBranch(0,productBranchDTO1);
        // TODO: how we add product branch??
    }

    public void addSuppliers() throws SQLException {
        String name = "FastAndBest ";
        String phone = "0507164509";
        String bankAccount = "12-128-148258";
        List<String> fields = createList("Tech", "Cleaning");
        String paymentCondition = "net 30 EOM";
        TreeMap<Integer, String> amountToDiscount = new TreeMap<>();
        amountToDiscount.put(100, "0.025%");
        amountToDiscount.put(500, "0.03%");

        List<String> contactNames = createList("Dana Grinberg", "Roni Katz");
        List<String> contactPhones = createList("0525948325", "0535669897");
        int maxSupplyDays = 4;

        SupplierController sc = SupplierController.getInstance();
        sc.addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }

    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    private static int getDay() {
        switch (LocalDate.now().getDayOfWeek()) {
            case SUNDAY:
                return 1;
            case MONDAY:
                return 2;
            case TUESDAY:
                return 3;
            case WEDNESDAY:
                return 4;
            case THURSDAY:
                return 5;
            case FRIDAY:
                return 6;
            case SATURDAY:
                return 7;
            default:
                return 0;
        }
    }
}
