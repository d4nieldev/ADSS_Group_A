package BusinessLayer.Inventory;

public class Global {
    public static int Productid = 0;
    public static int Categoryid = 0;
    public static int SpecificId = 0;
    public static int reportId = 0;
    public static int periodicId = 0;

    public static int getNewProductid() {
        Productid++;
        return Productid - 1;
    }

    public static int getNewCategoryid() {
        Categoryid++;
        return Categoryid - 1;
    }

    /**
     * s
     * 
     * @return
     */
    public static int getNewSpecificId() {

        return SpecificId++;
    }

    public static int getNewReportId() {

        return reportId++;
    }
    public static int getNewPeriodicId() {
        return periodicId++;
    }
}
