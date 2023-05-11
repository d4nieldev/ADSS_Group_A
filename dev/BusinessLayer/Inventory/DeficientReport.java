package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;

import java.time.LocalDate;
import java.util.HashMap;

public class DeficientReport extends Report {
    private HashMap<Integer, String> idToName;
    //TODO: change amount to hou much the deficiency
    private HashMap<Integer, Integer> idToMissingAmount;
    private HashMap<Integer, Integer> idToMinQuantity;
    private HashMap<Integer, Integer> idToIdealQuantity;

    private HashMap<Integer,ProductBranch> products;

    private BranchController branchController;

    public DeficientReport(int branchId) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        this.branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(branchId);
        this.idToName = branch.getAllDeficiencyProducts();
        this.idToTotalAmount = branch.getAllTotalAmountForDeficiencyProducts();
        this.products = branch.getAllProductBranches();
        this.idToMinQuantity = branch.getAllMinQuantityForDeficiencyProducts();
        this.idToIdealQuantity = branch.getAllIdealQuantityForDeficiencyProducts();
    }

    public HashMap<Integer, String> getIdToName() {
        return idToName;
    }

    public HashMap<Integer, Integer> getIdToTotalAmount() {
        return idToTotalAmount;
    }

    public HashMap<Integer, Integer> getIdToMinQuantity() {
        return idToMinQuantity;
    }

    public HashMap<Integer, Integer> getIdToIdealQuantity() {
        return idToIdealQuantity;
    }
}
