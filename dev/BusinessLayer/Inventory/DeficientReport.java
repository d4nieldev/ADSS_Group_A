package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DTOs.DeficiencyReportDTO;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DeficientReport extends Report {
    //TODO: change amount to hou much the deficiency
    private Map<Integer, Integer> idToMissingAmount;
    private HashMap<Integer, ProductBranch> products;
    private BranchController branchController;

    public DeficientReport(DeficiencyReportDTO deficiencyReportDTO) throws Exception {
        super(deficiencyReportDTO.getId(), deficiencyReportDTO.getBranchId(), LocalDate.now(), deficiencyReportDTO.getReportDTO());
        this.branchController = BranchController.getInstance();
        Branch branch = branchController.getBranchById(deficiencyReportDTO.getBranchId());
        this.idToMissingAmount = deficiencyReportDTO.getIdToMissingAmount();
        this.products = branch.getDeficiencyProductBranches(deficiencyReportDTO.getIdToProductBranch());
    }

    public HashMap<Integer, ProductBranch> getProducts() {
        return products;
    }

    public Map<Integer, Integer> getIdToMissingAmount() {
        return idToMissingAmount;
    }

//    public HashMap<Integer, String> getIdToName() {
//        return idToName;
//    }

//    public HashMap<Integer, Integer> getIdToTotalAmount() {
//        return idToTotalAmount;
//    }
//
//    public HashMap<Integer, Integer> getIdToMinQuantity() {
//        return idToMinQuantity;
//    }
//
//    public HashMap<Integer, Integer> getIdToIdealQuantity() {
//        return idToIdealQuantity;
//    }
}
