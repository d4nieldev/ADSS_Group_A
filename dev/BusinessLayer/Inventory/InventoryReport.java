package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DTOs.InventoryReportDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class InventoryReport extends Report {

    private Map<Integer, Integer> idToStorageAmount;
    private Map<Integer, Integer> idToShelfAmount;
    private HashMap<Integer, String> idsToName;

//    public InventoryReport(Branch branch) {
//        super(Global.getNewReportId(), branch.getId(), LocalDate.now());
//        // TODO: set all variables
//        // Branch branch = branchController.getBranchById(branch);
//        this.idsToName = branch.getIdsToName();
//        this.idToShelfAmount = branch.getIdsTOShelfAmount();
//        this.idToStorageAmount = branch.getIdsTOStorageAmount();

//    }

    public InventoryReport(InventoryReportDTO inventoryReportDTO, HashMap<Integer, String> idsToName)  {
        super(inventoryReportDTO.getId(), inventoryReportDTO.getBranchId(), LocalDate.now(), inventoryReportDTO.getReportDTO());
        this.idsToName = idsToName;
        this.idToShelfAmount = inventoryReportDTO.getIdToShopAmount();
        this.idToStorageAmount = inventoryReportDTO.getIdToStorageAmount();

    }

    public HashMap<Integer, String> getIdToName() {
        return idsToName;
    }

    public Map<Integer, Integer> getIdToStorageAmount() {
        return idToStorageAmount;
    }

    public Map<Integer, Integer> getIdToShelfAmount() {
        return idToShelfAmount;
    }
}
