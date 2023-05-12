package BusinessLayer.InveontorySuppliers;

import java.util.HashMap;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.Global;
import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DTOs.PeriodicReservationDTO;

public class PeriodicReservation {
    private int id;
    private HashMap<Integer, Integer> productsToAmounts; // maps between product's code to amount to order
    private ProductStatus.Day day; // day for delivery;
    ProductController productController;

     private int branchId;

    // public PeriodicReservation(int supplierId,Day day) {
    // this.id = Global.getNewPeriodicId();
    // this.day = day;
    // this.productsToAmounts = new HashMap<>();
    // productController = ProductController.getInstance();
    // }
    public PeriodicReservation(PeriodicReservationDTO periodicReservationDTO) {
        this.id = Global.getNewPeriodicId();
        this.day = periodicReservationDTO.getDayOfOrder();
        this.branchId = periodicReservationDTO.getBranchId();
        Branch branch = BranchController.getInstance().getBranchById(getBranchId());
        this.productsToAmounts = periodicReservationDTO.getAllItemsMap();
    }

    public PeriodicReservation() {
        this.day = ProductStatus.Day.Sunday;
        this.productsToAmounts = new HashMap<>();
    }

    public void setDay( ProductStatus.Day day) {
        this.day = day;
    }

    public ProductStatus.Day getDay() {
        return day;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, Integer> getProductsToAmounts() {
        return productsToAmounts;
    }

    public int getBranchId() {
        return branchId;
    }

    public boolean addNewProduct(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (productsToAmounts.containsKey(productCode))
            throw new Exception("this product already defined- for update it choose updateProductPeriodReservation");
        if (!productController.productExists(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        if (amount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount);
        return true;
    }

    public boolean changeAmount(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.productExists(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        if (amount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount);
        return true;
    }

    public boolean addAmount(int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.productExists(productCode))
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
        if (!productController.productExists(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        int currentAmount = productsToAmounts.get(productCode);
        if (amount - currentAmount + totalQuantity < minQuantity)
            return false;
        productsToAmounts.put(productCode, amount - currentAmount);
        return true;
    }
}
