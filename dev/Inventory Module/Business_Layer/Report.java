package Business_Layer;

import java.util.Date;
import java.util.List;

public class Report {
    private Date creationDate;
    private Type type;
    private List<GeneralProduct> generalProductList;
    private List<SpecificProduct> specificProductList;
    public static enum Type{SHORTAGES,INVENTORY,FLAWS};

    /**
     *
     * @param creationDate
     * @param type
     * @param generalProductList - can be null
     * @param specificProductList -can be null
     */
    public Report(Date creationDate, Type type,List<GeneralProduct> generalProductList,List<SpecificProduct> specificProductList) {
//        super();
        this.creationDate = creationDate;
        this.type = type;
        this.generalProductList = generalProductList;
        this.specificProductList = specificProductList;
    }

    //getters
    public Date getCreationDate() {
        return creationDate;
    }
    public Type getType() {
        return type;
    }
    public List<GeneralProduct> getGeneralProductList() {
        return generalProductList;
    }
    public List<SpecificProduct> getSpecificProductList() {
        return specificProductList;
    }
    @Override
    public String toString() {
        return "Report [creationDate = " + creationDate + ", type = " + type.name() + "]";
    }
}
