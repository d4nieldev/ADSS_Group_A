package Business_Layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Supply {

    private int id;
    private int code;
    private GeneralProduct generalProduct;
    private Date expiredDate;
    private double buyPrice;
    List<Double> sellPrice;//a list that store the sell's price of each product from the supply
    private int amount;


    public Supply( int code, GeneralProduct generalProductCode, Date expiredDate, double buyPrice,int amount) {
        this.id = Global.getNewSupplyId();
        this.code = code;
        this.generalProduct = generalProductCode;
        this.expiredDate = expiredDate;
        this.buyPrice = buyPrice;
        this.sellPrice = new ArrayList<>();
        this.amount = amount;

    }

    public List<Double> getSellPrice() {
        return sellPrice;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public GeneralProduct getGeneralProduct() {
        return generalProduct;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public boolean setAmount(){
        if (amount > 0) {
            amount--;
            return true;
        }
        else
            return false;
    }

    public void addToSellPrice(double price) {
        this.sellPrice.add(price);
    }
}
