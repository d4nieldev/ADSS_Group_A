package BusinessLayer.Inventory;

import DataAccessLayer.DTOs.SpecificProductDTO;

import java.time.LocalDate;

public class SpecificProduct {
    private int generalId;
    private int specificId;
    private double buyPrice;
    private double sellPrice;
    private ProductStatus.status status;
    private String flawDescription;
    private LocalDate expiredDate;
    private LocalDate arrivedDate;
    private SpecificProductDTO specificProductDTO;


    public SpecificProduct(int generalId, double buyPrice, LocalDate expiredDate) {
        this.generalId = generalId;
        this.specificId = Global.getNewSpecificId();
        this.buyPrice = buyPrice;
        this.sellPrice = -1;
        this.status = ProductStatus.status.ON_STORAGE;
        this.expiredDate = expiredDate;
        this.flawDescription = "";
        this.arrivedDate = LocalDate.now();
    }
    public SpecificProduct(SpecificProductDTO specificProductDTO){
        this.generalId = specificProductDTO.getGeneralId();
        this.specificId = specificProductDTO.getSpecificId();
        this.buyPrice = specificProductDTO.getBuyPrice();
        this.sellPrice = specificProductDTO.getSellPrice();
        this.status = specificProductDTO.getStatus();
        this.expiredDate = specificProductDTO.getExpDate();
        this.flawDescription = specificProductDTO.getFlaw();
        this.arrivedDate = specificProductDTO.getArrivedDate();
        int branchId = specificProductDTO.getBranchId();
        this.specificProductDTO = specificProductDTO;
    }

    // getters and setters
    // ================================================
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

    public LocalDate getArrivedDate() {
        return arrivedDate;
    }

    public void setStatus(ProductStatus.status status) {
        this.status = status;
        this.specificProductDTO.UpdateStatus(status);
    }

    public String getFlawDescription() {
        return flawDescription;
    }

    public void setFlawDescription(String flawDescription) {
        this.flawDescription = flawDescription;
        this.specificProductDTO.updateDescription(flawDescription);
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean getIsExpired() {
        return this.expiredDate.isBefore(LocalDate.now().plusDays(1));
    }
}
