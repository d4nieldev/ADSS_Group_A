package BusinessLayer.Inventory;

import java.time.LocalDate;
import java.util.HashMap;

public class ExpiredAndFlawReport extends Report {
    private HashMap<Integer, HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate; // maps between productBranch to
                                                                                        // map specific and ExpireDate
    private HashMap<Integer, HashMap<Integer, String>> codeToSpecificDescription; // maps between product code to map of
                                                                                  // specificId and flaw description all
                                                                                  // its specificProducts that flaws
    private HashMap<Integer, String> codeToCategory;
    private HashMap<Integer, String> idsToName;
    private BranchController branchController;

    public ExpiredAndFlawReport(int branchId) {
        super(Global.getNewReportId(), branchId, LocalDate.now());
        this.branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(branchId);
        this.idToExpiredSpecificIdAndDate = branch.getBranchesExpired();
        this.codeToSpecificDescription = branch.getBranchFlawsIdsToDescription();
        this.idsToName = branch.getIdsToName();
        this.codeToCategory = branch.getCodeToCategory();
    }

    public HashMap<Integer, HashMap<Integer, LocalDate>> getIdToExpiredSpecificIdAndDate() {
        return idToExpiredSpecificIdAndDate;
    }

    public HashMap<Integer, HashMap<Integer, String>> getCodeToSpecificDescription() {
        return codeToSpecificDescription;
    }

    public HashMap<Integer, String> getIdsToName() {
        return idsToName;
    }

    public HashMap<Integer, String> getCodeToCategory() {
        return codeToCategory;
    }

}
