package BusinessNew;

import java.time.LocalDate;
import java.util.HashMap;

public class InventoryReport extends Report{
    private HashMap<Integer, ProductBranch> ProductCodeToProduct;
    private HashMap<Integer,Integer> idToStorageAmount;
    private HashMap<Integer,Integer> idToShelfAmount;
    private HashMap<Integer,Double> idToPrice;

    public InventoryReport(int id, int branchId) {
        super(id, branchId, LocalDate.now());
    }


    @Override
    public void importReport() {
        return this.ProductCodeToProduct;
    }
    }
    public HashMap<Integer,ProductBranch> importFullInventory(){

}
