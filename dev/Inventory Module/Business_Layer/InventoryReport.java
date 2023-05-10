package Business_Layer;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class InventoryReport extends Report {


    private HashMap<Integer, Integer> idToStorageAmount;
    private HashMap<Integer, Integer> idToShelfAmount;
    private HashMap<Integer,String> idsToName;
    private BranchController branchController;

    public InventoryReport(int branchId) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        //TODO: set all variables
        branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(branchId);
        this.idsToName = branch.getIdsToName();
        this.idToShelfAmount = branch.getIdsTOShelfAmount();
        this.idToStorageAmount = branch.getIdsTOStorageAmount();

    }
    public InventoryReport(int branchId,List<Category> categoryList) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        //TODO: set all variables
        branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(branchId);
        this.idsToName = branch.getIdsToNameByCategories(categoryList);
        this.idToShelfAmount = branch.getIdsTOShelfAmountByCategories(categoryList);
        this.idToStorageAmount = branch.getIdsTOStorageAmountByCategories(categoryList);

    }


    public HashMap<Integer, String> getIdToName() {
        return idsToName;
    }

    public HashMap<Integer, Integer> getIdToStorageAmount() {
        return idToStorageAmount;
    }

    public HashMap<Integer, Integer> getIdToShelfAmount() {
        return idToShelfAmount;
    }
}

