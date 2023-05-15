package ServiceLayer.InventorySuppliers;

import java.sql.SQLException;

import BusinessLayer.Inventory.BranchController;

public class BranchService {
    private BranchController branchController;

    public BranchService() {
        branchController = BranchController.getInstance();
    }

    public String addBranch(int branchId, String branchName, int minAmount) {
        try {
            branchController.addBranch(branchId, branchName, minAmount);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String addProductBranch(int productId, int branchId, Integer discountId, double price, int minQuantity,
            int idealQuantity) {
        try {
            branchController.addNewProductBranch(productId, branchId, discountId, price, minQuantity, idealQuantity);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
