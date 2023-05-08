package BusinessNew;

import Business_Layer.Enum;

import java.time.LocalDate;

public class SpecificProduct {
    private int generalId;
    private int specificId;
    private double buyPrice;
    private double sellPrice;
    private ProductStatus.status status;
    private String flawDescription;
    private LocalDate expiredDate;


    public SpecificProduct(int generalId, double buyPrice, ProductStatus.status status, LocalDate expiredDate) {
        this.generalId = generalId;
        this.specificId = Global.getNewSpecificId();
        this.buyPrice = buyPrice;
        this.sellPrice = -1;
        this.status = status;
        this.expiredDate = expiredDate;
        this.flawDescription = "";
        this.isExpired = false;
    }

    //getters and setters
    //================================================
    public int getGeneralId() {
        return generalId;
    }

    public void setGeneralId(int generalId) {
        this.generalId = generalId;
    }

    public int getSpecificId() {
        return specificId;
    }

    public void setSpecificId(int specificId) {
        this.specificId = specificId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public ProductStatus.status getStatus() {
        return status;
    }

    public void setStatus(ProductStatus.status status) {
        this.status = status;
    }

    public String getFlawDescription() {
        return flawDescription;
    }

    public void setFlawDescription(String flawDescription) {
        this.flawDescription = flawDescription;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }
    public boolean getIsExpired(){
        return this.expiredDate.isBefore(LocalDate.now().plusDays(1));
    }
}
