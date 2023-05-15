package ServiceLayer.InventorySuppliers;

import BusinessLayer.Inventory.BranchController;

public class BranchService {
    private BranchController branchController;

    public BranchService() {
        branchController = BranchController.getInstance();
    }
}
