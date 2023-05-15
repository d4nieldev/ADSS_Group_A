package DataAccessLayer.DTOs;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import BusinessLayer.Inventory.ProductStatus;

public class SpecificProductDTO implements DTO {
    private int specificId;
    private int generalId;
    private int branchId;
    private double buyPrice;
    private double sellPrice;
    private ProductStatus.status status;
    private String flaw;
    private LocalDate expDate;
    private LocalDate arrivedDate;

    public SpecificProductDTO(int specificId, int generalId, int branchId, double buyPrice, double sellPrice,
            ProductStatus.status status, String flaw, LocalDate expDate, LocalDate arrivedDate) {

        this.specificId = specificId;
        this.generalId = generalId;
        this.branchId = branchId;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.status = status;
        this.flaw = flaw;
        this.expDate = expDate;
        this.arrivedDate = arrivedDate;
    }

    public SpecificProductDTO(int specificId, int generalId, int branchId, double buyPrice, String flaw, LocalDate expDate) {

        this.specificId = specificId;
        this.generalId = generalId;
        this.branchId = branchId;
        this.buyPrice = buyPrice;
        this.sellPrice = -1;
        this.status = ProductStatus.status.ON_STORAGE;
        this.flaw = flaw;
        this.expDate = expDate;
        this.arrivedDate = LocalDate.now();
    }
    public SpecificProductDTO(int specificId, int generalId, int branchId, double buyPrice, LocalDate expDate) {

        this.specificId = specificId;
        this.generalId = generalId;
        this.branchId = branchId;
        this.buyPrice = buyPrice;
        this.sellPrice = -1;
        this.status = ProductStatus.status.ON_STORAGE;
        this.flaw = "";
        this.expDate = expDate;
        this.arrivedDate = LocalDate.now();
    }

    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("specificId", "" + specificId);
        nameToVal.put("generalId", "" + generalId);
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("buyPrice", "" + buyPrice);
        nameToVal.put("sellPrice", "" + sellPrice);
        nameToVal.put("status", status.toString());
        nameToVal.put("flaw", flaw);
        if (expDate == null)
            nameToVal.put("expDate", null);
        else
            nameToVal.put("expDate", expDate.toString());
        if (arrivedDate == null)
            nameToVal.put("arrivedDate", null);
        else
            nameToVal.put("arrivedDate", arrivedDate.toString());
        return nameToVal;
    }

    public int getSpecificId() {
        return specificId;
    }

    public int getGeneralId() {
        return generalId;
    }

    public int getBranchId() {
        return branchId;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public ProductStatus.status getStatus() {
        return status;
    }

    public String getFlaw() {
        return flaw;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public LocalDate getArrivedDate() {
        return arrivedDate;
    }

    public void UpdateStatus(ProductStatus.status status) {
        this.status = status;
    }

    public void updateDescription(String flawDescription) {
        this.flaw = flawDescription;
    }

    public void updateStatus(ProductStatus.status updateStatus) {
        this.status = updateStatus;
    }
}
