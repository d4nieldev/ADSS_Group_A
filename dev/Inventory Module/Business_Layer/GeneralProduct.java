package Business_Layer;

import java.time.LocalDate;
import java.util.*;

public class GeneralProduct {
    private String name;
    private int code;
    private double price;
    private String manufacturer;
    private int min_quantity;
    private int total_quantity;
    private int shop_quantity;
    private int storage_quantity;
    private Category category;
    private boolean onDiscount;
    private Discount discount;
    private List<Supply> productSupply;
    private List<Integer> onShelf;
    private List<Integer> onStorage;
    private HashMap<Integer,String> allFlowProducts;// id-flow description
    private  List<Integer> allExpiredProducts; // ids




    public  enum Location{STORAGE,SHOP};
    public HashMap<Integer, Double> getIdsSellPrice() {
        return idsSellPrice;
    }
    private HashMap<Integer,Double> idsSellPrice;
    private int currentId;


    public GeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, Category category) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = 0;
        this.shop_quantity = 0;
        this.storage_quantity = 0;
        this.category = category;
        this.onDiscount = false;
        discount = null;
        this.productSupply = new ArrayList<>();
        this.onShelf = new ArrayList<>();
        this.onStorage = new ArrayList<>();
        this.idsSellPrice = new HashMap<>();
        this.allExpiredProducts = new ArrayList<>();
        this.allFlowProducts = new HashMap<>();
        this.currentId = 0;
    }

    public GeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, Category category,int total_quantity) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.manufacturer = manufacturer;
        this.min_quantity = min_quantity;
        this.total_quantity = total_quantity;
        this.shop_quantity = 0;
        this.storage_quantity = total_quantity;
        this.category = category;
        this.onDiscount = false;
        discount = null;
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

    public double getPrice() {
        return price;
    }
    public double getCurrentPrice() {
//        if(onDiscount)
        double result = price;
        if(discount != null){
            if(discount.getStart_date().isBefore(LocalDate.now().plusDays(1)) && discount.getEnd_date().isAfter(LocalDate.now().minusDays(1)));
            result = price * (1 - this.discount.getDiscount_percentage()/100);
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

    public Category getCategory() {
        return category;
    }

    public boolean isOnDiscount() {
        return onDiscount;
    }

    public Discount getDiscount() {
        return discount;
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
    public void setDiscount(Discount discount) {

        this.discount = discount;
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
    public boolean removeItem(int id, Enum.Location location)
    {
        boolean res = false;
        if(total_quantity == 0 || total_quantity < 0 )
            res = false;
        else {
            if (location.equals(Enum.Location.SHOP)) {
                if(onShelf.contains(id)) {
                    onShelf.remove(id);
                    shop_quantity--;
                    total_quantity--;
                    res = true;
                }
                } else if (location.equals(Enum.Location.STORAGE)) {
                if (onStorage.contains(id)) {
                    onStorage.remove(id);
                    storage_quantity--;
                    total_quantity--;
                    res = true;
                }
            }

        }
        return res;
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
            onStorage.remove(id);
        }

    }


    @Override
    public String toString() {
        return "GeneralProduct [name=" + name + ", generalProductId=" + code + ", price=" + price + ", manufacturer=" + manufacturer
                + ", total quantity=" + total_quantity + ", min quantity=" + min_quantity + ", shop quantity=" + shop_quantity + ", storage quantity=" + storage_quantity +
                ", category=" + category + ", onDiscount=" + onDiscount + "]";

    }
}
