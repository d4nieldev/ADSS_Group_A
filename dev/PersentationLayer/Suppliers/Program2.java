package PersentationLayer.Suppliers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.Suppliers.SupplierController;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.Repository;

public class Program2 {
    private static <T> List<T> createList(T... args) {
        List<T> lst = new ArrayList<>();
        for (T item : args) {
            lst.add(item);
        }
        return lst;
    }

    public static void setup() {
        try {
            // ProductController.getInstance().init();
            Repository r =Repository.getInstance();
            SupplierController.getInstance().init();
            createSupplier0();
            createProduct();
            //createProduct();
        } catch (Exception e) {
            System.out.println("Could not setup environment");
            e.printStackTrace();
        }
    }

    public static void createProduct() throws SuppliersException, SQLException {
        CategoryController.getInstance().addNewCategory("Diary");
        ProductController.getInstance().addProduct(203, "Tara Milk 1.5L", "Tara", 0);
    }

    public static void createSupplier0() throws SuppliersException, SQLException {
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

        SupplierController.getInstance().addOnOrderSupplierBaseAgreement(name, phone, bankAccount, fields,
                paymentCondition, amountToDiscount, contactNames, contactPhones, maxSupplyDays);
    }
    
    public static void main(String[] args) {
        setup();
        SupplierController sc = SupplierController.getInstance();
        try{
            sc.deleteSupplier(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
