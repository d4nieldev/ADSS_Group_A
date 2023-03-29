package Business_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supply {
    private int code;
    private GeneralProduct generalProduct;
    private LocalDate expiredDate;
    private double buyPrice;
//    List<Double> sellPrice;//a list that store the sell's price of each product from the supply
    private int amount;
    private int shopAmount;
    private int storageAmount;
    private List<Integer> ids;
    private  int firstId;
    private int lastId;



    public Supply(GeneralProduct generalProductCode, LocalDate expiredDate, double buyPrice,int amount,int shopAmount) {
//        this.id = id;
//        this.code = code;
        this.generalProduct = generalProductCode;
        this.expiredDate = expiredDate;
        this.buyPrice = buyPrice;
//        this.sellPrice = new ArrayList<>();
        this.amount = amount;
        this.shopAmount = 0;
        this.storageAmount = amount;
        this.firstId = -1;
        this.lastId = -1;
        this.ids = new ArrayList<>();

    }

//    public List<Double> getSellPrice() {
//        return sellPrice;
//    }

    public int getAmount() {
        return amount;
    }

//    public int getId() {
//        return id;
//    }

    public int getCode() {
        return code;
    }

    public GeneralProduct getGeneralProduct() {
        return generalProduct;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public boolean setAmount(int id,Enum.Location location) {
        boolean res = false;
        if (ids.contains(id)) {
            if (location == Enum.Location.SHOP)
            {
                res = setAmountFromShop(id);
            }
            else
            {
                res = setAmountFromStorage(id);
            }

        }
        return res;
    }
    public boolean setAmountFromShop(int id){
        if (amount > 0) {
            amount--;
            shopAmount--;
            ids.remove(id);

            return true;
        }
        else
            return false;
    }
    public boolean setAmountFromStorage(int id){
        if (amount > 0) {
            amount--;
            storageAmount--;
            ids.remove(id);

            return true;
        }
        else
            return false;
    }

//    public void addToSellPrice(double price) {
//        this.sellPrice.add(price);
//    }

    public int getShopAmount() {
        return this.shopAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public int getFirstId() {
        return firstId;
    }

    public int getLastId() {
        return lastId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setFirstId(int firstId) {
        this.firstId = firstId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

    public void addId(int id)
    {
        ids.add(id);
    }

    public void remove() {
        this.amount = 0;
        this.shopAmount = 0;
        this.storageAmount = 0;

    }
}
