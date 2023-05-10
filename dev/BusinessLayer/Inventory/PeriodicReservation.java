package BusinessLayer.Inventory;

import java.util.HashMap;

import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.enums.Day;

public class PeriodicReservation {
    private HashMap<Integer, Integer> productsToAmounts; // maps between product's code to amount to order
    private Day day; // day for delivery;
    ProductController productController;

    public PeriodicReservation(Day day) {
        this.day = day;
        this.productsToAmounts = new HashMap<>();
        productController = ProductController.getInstance();
    }

    public PeriodicReservation() {
        this.day = Day.SUNDAY;
        this.productsToAmounts = new HashMap<>();
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Day getDay() {
        return day;
    }

    public HashMap<Integer, Integer> getProductsToAmounts() {
        return productsToAmounts;
    }

    public boolean addNewProduct(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (productsToAmounts.containsKey(productCode))
            throw new Exception("this product already defined- for update it choose updateProductPeriodReservation");
        if (!productController.exist(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        if (amount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount);
        return true;
    }

    public boolean changeAmount(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.exist(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        if (amount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount);
        return true;
    }

    public boolean addAmount(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.exist(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        int currentAmount = productsToAmounts.get(productCode);
        if (amount + currentAmount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount + currentAmount);
        return true;
    }

    public boolean reduceAmount(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.exist(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        int currentAmount = productsToAmounts.get(productCode);
        if (amount - currentAmount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount - currentAmount);
        return true;
    }
}
