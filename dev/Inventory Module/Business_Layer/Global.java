package Business_Layer;

public class Global {
    public static int Productid = 0;
    public static int Categoryid = 0;
    public static int SupplyId = 0;

    public static int getNewProductid()
    {
        Productid++;
        return  Productid - 1;
    }
    public static int getNewCategoryid()
    {
        Categoryid++;
        return  Categoryid - 1;
    }

    /**
     * harta barrta
     * @return
     */
    public static int getNewSupplyId()
    {
        SupplyId++;
        return  SupplyId - 1;
    }
}
