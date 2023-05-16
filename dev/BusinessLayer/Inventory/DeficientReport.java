package BusinessLayer.Inventory;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DTOs.DeficiencyReportDTO;

public class DeficientReport extends Report {
    private Map<Integer, Integer> idToMissingAmount;
    private Map<Integer, ProductBranch> products;

    public DeficientReport(DeficiencyReportDTO deficiencyReportDTO, Map<Integer, ProductBranch> products) {
        super(deficiencyReportDTO.getId(), deficiencyReportDTO.getBranchId(), LocalDate.now(),
                deficiencyReportDTO.getReportDTO());
        this.idToMissingAmount = deficiencyReportDTO.getIdToMissingAmount();
        this.products = products;
    }

    public Map<Integer, ProductBranch> getProducts() {
        return products;
    }

    public Map<Integer, Integer> getIdToMissingAmount() {
        return idToMissingAmount;
    }
}
