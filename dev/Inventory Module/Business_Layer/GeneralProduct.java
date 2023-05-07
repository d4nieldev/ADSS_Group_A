package Business_Layer;

import java.time.LocalDate;
import java.util.*;

public class GeneralProduct {
    private String name; // The name of the product
    private int code; // The unique code of the product
    private double price; // The price of the product
    private String manufacturer; // The manufacturer of the product
    private int min_quantity; // The minimum quantity that should be kept in the storage
    private int total_quantity; // The total quantity of the product
    private int shop_quantity; // The quantity of the product on the shop shelf
    private int storage_quantity; // The quantity of the product in storage
    private Category1 category1; // The category that the product belongs to
    private boolean onDiscount; // A boolean indicating if the product is currently on discount
    private Discount1 discount1; // The discount object that the product is currently on
    private List<Supply> productSupply; // A list of all the GeneralProduct's supplies
    private List<Integer> onShelf; // A list of all the product's IDs that are on the shop's shelf
    private List<Integer> onStorage; // A list of all the product's IDs that are in storage
    private HashMap<Integer, String> allFlowProducts; // A HashMap containing the IDs and the descriptions of all the product's flow
    private List<Integer> allExpiredProducts; // A list of all the product's IDs that have expired
    public enum Location {STORAGE, SHOP}; // An enum indicating the location of the product
    private HashMap<Integer, Double> idsSellPrice; // A HashMap containing the IDs and the sell prices of all the product's supplies
    private int currentId; // The current ID of the product (used for generating new supply IDs)



    public GeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, Category1 category1) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = 0;
        this.shop_quantity = 0;
        this.storage_quantity = 0;
        this.category1 = category1;
        this.onDiscount = false;
        discount1 = null;
        this.productSupply = new ArrayList<>();
        this.onShelf = new ArrayList<>();
        this.onStorage = new ArrayList<>();
        this.idsSellPrice = new HashMap<>();
        this.allExpiredProducts = new ArrayList<>();
        this.allFlowProducts = new HashMap<>();
        this.currentId = 0;
    }

    public GeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, Category1 category1, int total_quantity) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = total_quantity;
        this.shop_quantity = 0;
        this.storage_quantity = total_quantity;
        this.category1 = category1;
        this.onDiscount = false;
        discount1 = null;
        this.productSupply = new ArrayList<>();
        this.onShelf = new ArrayList<>();
        this.onStorage = new ArrayList<>();
        this.idsSellPrice = new HashMap<>();
        this.allExpiredProducts = new ArrayList<>();
        this.allFlowProducts = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public List<Supply> getProductSupply() {
        return productSupply;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public double getPrice() {
        return price;
    }
    public double getCurrentPrice() {
//        if(onDiscount)
        double result = price;
        if(discount1 != null){
            if(discount1.getStart_date().isBefore(LocalDate.now().plusDays(1)) && discount1.getEnd_date().isAfter(LocalDate.now().minusDays(1)));
            result = price * (1 - this.discount1.getDiscount_percentage()/100);
        }


            return result;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getMin_quantity() {
        return min_quantity;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public int getShop_quantity() {
        return shop_quantity;
    }

    public int getStorage_quantity() {
        return storage_quantity;
    }

    public Category1 getCategory() {
        return category1;
    }

    public boolean isOnDiscount() {
        return onDiscount;
    }

    public Discount1 getDiscount() {
        return discount1;
    }

    public List<Integer> getOnShelf() {
        return onShelf;
    }

    public List<Integer> getOnStorage() {
        return onStorage;
    }
    public boolean isOnShortage() {
        return total_quantity < min_quantity;
    }

    public List<Integer> getAllExpiredProducts() {
        return allExpiredProducts;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setDiscount(Discount1 discount1) {

        this.discount1 = discount1;
    }

    public void setOnDiscount(boolean onDiscount) {
        this.onDiscount = onDiscount;
    }

    public void addSellPrice(int id,double price)
    {
        this.idsSellPrice.put(id,price);
    }

    public int getCurrentId() {
        return currentId;
    }

    public HashMap<Integer, String> getAllFlowProducts() {
        return allFlowProducts;
    }

    /**
     * remove a specific item from shop or storage
     * @param id
     * @param location
     * @return
     */
    public boolean removeItem(int id, Enum.Location location) {
        boolean res = false; // Initialize a boolean variable to track whether the item was successfully removed

        // Check if the total quantity of items is 0 or negative, in which case it's not possible to remove an item
        if (total_quantity == 0 || total_quantity < 0)
            res = false;
        else {
            // If the item is in the shop location
            if (location.equals(Enum.Location.SHOP)) {
                // Check if the item is on the shelf
                if (onShelf.contains(id)) {
                    // Remove the item from the shelf, decrement the shop quantity and the total quantity, and set res to true
                    deleteElement(onShelf, id);
                    shop_quantity--;
                    total_quantity--;
                    res = true;
                }
            }
            // If the item is in the storage location
            else if (location.equals(Enum.Location.STORAGE)) {
                // Check if the item is in storage
                if (onStorage.contains(id)) {
                    // Remove the item from storage, decrement the storage quantity and the total quantity, and set res to true
                    deleteElement(onStorage, id);
                    storage_quantity--;
                    total_quantity--;
                    res = true;
                }
            }
        }
        return res; // Return the result of the removal operation
    }

    private void deleteElement(List<Integer> lst,int id){
        int i = 0;
        while (i < lst.size()){
            if(lst.get(i).equals(id))
                lst.remove(i);
            i++;
        }
    }

    public void removeFromShop(int amount)
    {
        this.shop_quantity -= amount;
        this.total_quantity -= amount;
    }
    public void removeFromStorage(int amount)
    {
        this.storage_quantity-= amount;
        this.total_quantity -= amount;
    }

    public void addFlowProduct(int id, String description)
    {
        allFlowProducts.put(id,description);

    }
    public void addExpiredProducts(List<Integer> ids) {
        for(Integer id : ids)
        {
            this.allExpiredProducts.add(id);
        }
    }
    public HashMap<Integer, Double> getIdsSellPrice() {
        return idsSellPrice;
    }
    public void addToShelf(int id) {
        this.onShelf.add(id);

    }

    public void addQuantityToStorage(int quantity)
    {
        this.total_quantity += quantity;
        this.storage_quantity += quantity;
    }
    public void addNewQuantityToShelf(int quantity)
    {
        this.total_quantity += quantity;
        this.shop_quantity += quantity;
    }
    public void setCurrentIdt(int currentId) {
        this.currentId = currentId;
    }

    public void addToStorage(List<Integer> ids) {
        onStorage.addAll(ids);

    }
    public void setMinimumQuantity(int minQuantity) {
        this.min_quantity = minQuantity;
    }

    /**
     * adding new supply sorting by the expired date
     * @param sp
     */
    public void addSupply(Supply sp) {

        int index = Collections.binarySearch(productSupply, sp, Comparator.comparing(Supply::getExpiredDate));
        if (index < 0) {
            index = -(index + 1);
        }
        productSupply.add(index, sp);
    }

    public void addSupply1(Supply sp) {

        int index = 0;
        boolean check = false;
       for (Supply supply:productSupply)
       {
           if(supply.getExpiredDate().isAfter(sp.getExpiredDate()))
           {
               productSupply.add(index,sp);
               check = true;
               break;
           }
           index ++;
       }
       if(!check)
       {
           productSupply.add(sp);
       }
    }
    /**
     * transfring all products ids from storage to shop
     * @param amount
     * @param ids
     */
    public void transferFromStorageToShop(int amount,List<Integer> ids) {
//        productSupply.remove(sp);
        storage_quantity -= amount;
        shop_quantity += amount;
        onShelf.addAll(ids);
        removeStorageIds(ids);
    }

    private void removeStorageIds(List<Integer> ids) {
        for(int id : ids)
        {
//            onStorage.remove(id);
            onStorage.remove(Integer.valueOf(id));
        }

    }

    @Override
    public String toString() {
        return "GeneralProduct [name=" + name + ", generalProductId=" + code + ", price=" + price + ", manufacturer=" + manufacturer
                + ", total quantity=" + total_quantity + ", min quantity=" + min_quantity + ", shop quantity=" + shop_quantity + ", storage quantity=" + storage_quantity +
                ", category=" + category1 + ", onDiscount=" + onDiscount + "]";

    }
}
