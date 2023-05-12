package BusinessLayer.InveontorySuppliers;

import java.util.HashMap;

import BusinessLayer.Inventory.BranchController;
import BusinessLayer.Inventory.Global;
import BusinessLayer.Inventory.ProductStatus;
import DataAccessLayer.DAOs.PeriodicReservationItemDAO;
import DataAccessLayer.DTOs.PeriodicReservationDTO;
import DataAccessLayer.DTOs.PeriodicReservationItemDTO;

public class PeriodicReservation {
    private int supplierId;
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
        this.supplierId = periodicReservationDTO.getSupplierId();
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

    public int getSupplierId() {
        return supplierId;
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

    public boolean changeAmount(int supplierId,int productCode, int amount, int minQuantity, int totalQuantity) throws Exception {
        if (!productsToAmounts.containsKey(productCode))
            throw new Exception("this product not exist");
        if (!productController.productExists(productCode))
            throw new Exception("this product doesn't exist on suppliers");
        PeriodicReservationItemDAO periodicReservationItemDAO = PeriodicReservationItemDAO.getInstance();
        PeriodicReservationItemDTO periodicReservationItemDTO = periodicReservationItemDAO.getById(supplierId,branchId,productCode);
        if (amount + totalQuantity < minQuantity)
            return false;
        periodicReservationItemDTO.updateAmount(amount);
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
        int totalAmount = amount + currentAmount;
        PeriodicReservationItemDAO periodicReservationItemDAO = PeriodicReservationItemDAO.getInstance();
        PeriodicReservationItemDTO periodicReservationItemDTO = periodicReservationItemDAO.getById(supplierId,branchId,productCode);
        periodicReservationItemDTO.updateAmount(totalAmount);
        productsToAmounts.put(productCode, totalAmount);
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
        int totalAmount = currentAmount - amount;
        PeriodicReservationItemDAO periodicReservationItemDAO = PeriodicReservationItemDAO.getInstance();
        PeriodicReservationItemDTO periodicReservationItemDTO = periodicReservationItemDAO.getById(supplierId,branchId,productCode);
        periodicReservationItemDTO.updateAmount(Math.max(0,totalAmount));
        productsToAmounts.put(productCode, Math.max(0,totalAmount));
        return true;
    }
}
