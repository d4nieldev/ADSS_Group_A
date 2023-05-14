package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;

import java.time.LocalDate;
import java.util.HashMap;

public class ExpiredAndFlawReport extends Report {
    private HashMap<Integer, HashMap<Integer, LocalDate>> idToExpiredSpecificIdAndDate; // maps between productBranch to
                                                                                        // map specific and ExpireDate
    private HashMap<Integer, HashMap<Integer, String>> codeToSpecificDescription; // maps between product code to map of
                                                                                    // specificId and flaw description all// its specificProducts that flaws
    private HashMap<Integer,ProductBranch> products;

    private BranchController branchController;
//    private HashMap<Integer, String> codeToCategory;
//    private HashMap<Integer, String> idsToName;


    public ExpiredAndFlawReport(ExpiredAndFlawReportDTO expiredAndFlawReportDTO) throws Exception {
        super(expiredAndFlawReportDTO.getId(), expiredAndFlawReportDTO.getBranchId(), LocalDate.now(), expiredAndFlawReportDTO.getReportDTO());

        this.branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(expiredAndFlawReportDTO.getBranchId());
        this.idToExpiredSpecificIdAndDate = branch.getBranchesExpiredBySpecificProductList(expiredAndFlawReportDTO.getExpiredProducts());
        this.codeToSpecificDescription = branch.getBranchFlawsIdsToDescription();
        this.products = branch.getAllProductBranches();
//        this.idsToName = branch.getIdsToName();
//        this.codeToCategory = branch.getCodeToCategory();
    }

    public HashMap<Integer, HashMap<Integer, LocalDate>> getIdToExpiredSpecificIdAndDate() {
        return idToExpiredSpecificIdAndDate;
    }

    public HashMap<Integer, HashMap<Integer, String>> getCodeToSpecificDescription() {
        return codeToSpecificDescription;
    }

    public HashMap<Integer,ProductBranch> getProducts() {
        return products;
    }

//    public HashMap<Integer, String> getIdsToName() {
//
//        return idsToName;
//    }
//
//    public HashMap<Integer, String> getCodeToCategory() {
//        return codeToCategory;
//    }

}
