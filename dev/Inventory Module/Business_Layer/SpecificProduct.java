package Business_Layer;
import Service_Layer.ProductService;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SpecificProduct {
    private int id;
    private String location;
    private int buyingPrice;
    private boolean isFlaw;
    private Date reportedFlaw;
    private Date expired;
    private GeneralProduct generalProduct;

    public SpecificProduct( String location, boolean isFlaw, int buyingPrice,Date expired, GeneralProduct generalProduct) {
        this.id = Global.getNewProductid();
        this.location = location;
        this.buyingPrice = buyingPrice;
        this.isFlaw = isFlaw;
        this.reportedFlaw = null;
        this.expired = expired;
        this.generalProduct = generalProduct;
    }
    //getters and setters
    public int getId() {
        return this.id;
    }
    public String getLocation() {
        return this.location;
    }
    public boolean isFlaw() {
        return this.isFlaw;
    }
    public Date getExpired() {
        return this.expired;
    }
    public GeneralProduct getGeneralProduct() {
        return this.generalProduct;
    }
    public void setLocation(String location) {this.location = location;}
    public void setFlaw(boolean flaw) {
        isFlaw = flaw;
//        if(isFlaw)
           // reportedFlaw = Date.now
    }
    public void setReportedFlaw(Date reportedFlaw) {this.reportedFlaw = reportedFlaw;}

    @Override
    public String toString() {
        return "SpecificProduct [id=" + id + ", location=" + location + ", isFlaw=" + isFlaw + ", expired=" + expired
                + ", generalProduct=" + generalProduct + "]";
    }

}
