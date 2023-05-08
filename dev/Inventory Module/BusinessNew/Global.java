package BusinessNew;

public class Global {
    public static int Productid = 0;
    public static int Categoryid = 0;
    public static int SpecificId = 0;

    public static int getNewProductid() {
        Productid++;
        return Productid - 1;
    }
    public static int getNewCategoryid() {
        Categoryid++;
        return Categoryid - 1;
    }

    /**
     * harta barrta
     * @return
     */
    public static int getNewSpecificId() {

        return SpecificId++;
    }
}
