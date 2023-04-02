package Business_Layer;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Report {
    private LocalDate creationDate;
    private Type type;
    private List<GeneralProduct> generalProductList;
    public static enum Type{SHORTAGES,INVENTORY,FLAWS};

    /**
     *
     * @param creationDate
     * @param type
     * @param generalProductList - can be null
     */
    public Report(LocalDate creationDate, Type type,List<GeneralProduct> generalProductList) {
//        super();
        this.creationDate = creationDate;
        this.type = type;
        this.generalProductList = generalProductList;

    }

    //getters
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public Type getType() {
        return type;
    }
    public List<GeneralProduct> getGeneralProductList() {
        return generalProductList;
    }
    @Override
    public String toString() {
        return "Report [creationDate = " + creationDate + ", type = " + type.name() + "]";
    }
}
