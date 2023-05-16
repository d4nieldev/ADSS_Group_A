package ServiceLayer.InventorySuppliers;

import java.sql.SQLException;
import java.time.LocalDate;

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String addProductBranch(int productId, int branchId, LocalDate discountStartDate, LocalDate discountEndDate,
            double discountVal, boolean isDiscountPrecentage, double price, int minQuantity,
            int idealQuantity) {
        try {
            branchController.addNewProductBranch(productId, branchId, discountStartDate, discountEndDate, discountVal,
                    isDiscountPrecentage, price, minQuantity, idealQuantity);
            return "Success";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
}
