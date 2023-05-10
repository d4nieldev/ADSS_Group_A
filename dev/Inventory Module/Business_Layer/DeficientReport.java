package Business_Layer;

import java.time.LocalDate;
import java.util.HashMap;

public class DeficientReport extends Report{
    private HashMap<Integer,String> idToName;
    private HashMap<Integer,Integer> idToTotalAmount;
    private HashMap<Integer,Integer> idToMinQuantity;
    private HashMap<Integer,Integer> idToIdealQuantity;

    private  BranchController branchController;

    public DeficientReport(int branchId) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        this.branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(branchId);
        this.idToName = branch.getAllDeficiencyProducts();
        this.idToTotalAmount = branch.getAllTotalAmountForDeficiencyProducts();
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
