package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class InventoryReport extends Report {

    private HashMap<Integer, Integer> idToStorageAmount;
    private HashMap<Integer, Integer> idToShelfAmount;
    private HashMap<Integer, String> idsToName;

    public InventoryReport(Branch branch) {
        super(Global.getNewReportId(), branch.getId(), LocalDate.now());
        // TODO: set all variables
        // Branch branch = branchController.getBranchById(branch);
        this.idsToName = branch.getIdsToName();
        this.idToShelfAmount = branch.getIdsTOShelfAmount();
        this.idToStorageAmount = branch.getIdsTOStorageAmount();

    }

    public InventoryReport(Branch branch, List<Category> categoryList)  {
        super(Global.getNewReportId(), branch.getId(), LocalDate.now());
        // TODO: set all variables
        // Branch branch = branchController.getBranchById(branch);
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
