package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;
import DataAccessLayer.DTOs.SpecificProductDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpiredAndFlawReport extends Report {
    private Map<Integer, Map<Integer, LocalDate>> idToExpiredSpecificIdAndDate; // maps between productBranch to
    // map specific and ExpireDate
    private Map<Integer, Map<Integer, String>> codeToSpecificDescription; // maps between product code to map of
    // specificId and flaw description all// its specificProducts that flaws
    private Map<Integer, ProductBranch> products;

    public ExpiredAndFlawReport(ExpiredAndFlawReportDTO expiredAndFlawReportDTO, Map<Integer, ProductBranch> products) {
        super(expiredAndFlawReportDTO.getId(), expiredAndFlawReportDTO.getBranchId(), LocalDate.now(),
                expiredAndFlawReportDTO.getReportDTO());

        List<SpecificProductDTO> expiredProducts = expiredAndFlawReportDTO.getExpiredProducts();
        this.idToExpiredSpecificIdAndDate = new HashMap<>();
        for (SpecificProductDTO specificDTO : expiredProducts) {
            int productId = specificDTO.getGeneralId();
            int specificId = specificDTO.getSpecificId();
            LocalDate expiryDate = specificDTO.getExpDate();
            idToExpiredSpecificIdAndDate.computeIfAbsent(productId, k -> new HashMap<>()).put(specificId, expiryDate);
        }

        List<SpecificProductDTO> flawProducts = expiredAndFlawReportDTO.getFlawProducts();
        this.codeToSpecificDescription = new HashMap<>();
        for (SpecificProductDTO specificDTO : flawProducts) {
            int productId = specificDTO.getGeneralId();
            int specificId = specificDTO.getSpecificId();
            String flaw = specificDTO.getFlaw();
            codeToSpecificDescription.computeIfAbsent(productId, k -> new HashMap<>()).put(specificId, flaw);
        }

        this.products = products;
    }

    public Map<Integer, Map<Integer, LocalDate>> getIdToExpiredSpecificIdAndDate() {
        return idToExpiredSpecificIdAndDate;
    }

    public Map<Integer, Map<Integer, String>> getCodeToSpecificDescription() {
        return codeToSpecificDescription;
    }

    public Map<Integer, ProductBranch> getProducts() {
        return products;
    }

}
