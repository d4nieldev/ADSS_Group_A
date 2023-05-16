package BusinessLayer.Inventory;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.ProductController;
import BusinessLayer.exceptions.InventoryException;
import DataAccessLayer.DAOs.DeficiencyReportEntryDAO;
import DataAccessLayer.DAOs.ExpiredAndFlawReportEntryDAO;
import DataAccessLayer.DAOs.InventoryReportEntryDAO;
import DataAccessLayer.DAOs.ReportDAO;
import DataAccessLayer.DTOs.DeficiencyReportDTO;
import DataAccessLayer.DTOs.DeficiencyReportEntryDTO;
import DataAccessLayer.DTOs.ExpiredAndFlawReportDTO;
import DataAccessLayer.DTOs.ExpiredAndFlawReportEntryDTO;
import DataAccessLayer.DTOs.InventoryReportDTO;
import DataAccessLayer.DTOs.InventoryReportEntryDTO;
import DataAccessLayer.DTOs.ProductBranchDTO;
import DataAccessLayer.DTOs.ReportDTO;

public class ReportController {
    private HashMap<Integer, Report> allReports;
    private static ReportController instance = null;
    private InventoryReportEntryDAO inventoryReportEntryDAO;
    private ExpiredAndFlawReportEntryDAO expiredAndFlawReportEntryDAO;
    private DeficiencyReportEntryDAO deficiencyReportEntryDAO;
    private ReportDAO reportDAO;

    private ReportController() {
        this.allReports = new HashMap<>();
        this.inventoryReportEntryDAO = InventoryReportEntryDAO.getInstance();
        this.expiredAndFlawReportEntryDAO = ExpiredAndFlawReportEntryDAO.getInstance();
        this.deficiencyReportEntryDAO = DeficiencyReportEntryDAO.getInstance();
        this.reportDAO = ReportDAO.getInstance();
        this.inventoryReportEntryDAO = InventoryReportEntryDAO.getInstance();
    }

    public static ReportController getInstance() {
        if (instance == null) {
            instance = new ReportController();
        }
        return instance;
    }

    // TODO : change brnachId to Branch object;
    public HashMap<Integer, Report> getAllReports() {
        return this.allReports;
    }

    // public InventoryReport importInventoryReport(int branchId, List<Category>
    // categoryList) {
    // Branch branch = BranchController.getInstance().getBranchById(branchId);
    // InventoryReport report = new InventoryReport(branch, categoryList);
    // allReports.put(report.getId(), report);
    // return report;
    // }

    // TODO: importInventroyReport- regular one without categories filter
    // public InventoryReport importInventoryReport(int branchId) throws
    // SQLException {
    // Branch branch = BranchController.getInstance().getBranchById(branchId);
    // int reportID = Global.getNewReportId();
    // ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
    // reportDAO.insert(repDTO);
    //
    // }
    public InventoryReport importInventoryReport(int branchId, List<Category> categoryList)
            throws SQLException, InventoryException {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        for (ProductBranch productBranch : branch.getAllProductBranches().values()) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();

            InventoryReportEntryDTO invRepEnDTO = new InventoryReportEntryDTO(reportID, code, shelfAmount,
                    storageAmount);
            inventoryReportEntryDAO.insert(invRepEnDTO);
        }

        InventoryReportDTO invRepDTO = inventoryReportEntryDAO.getFullReportById(reportID);
        HashMap<Integer, String> idsToName = branch.getIdsToNameByCategories(categoryList);

        InventoryReport report = new InventoryReport(invRepDTO, idsToName);
        saveReport(report);
        return report;
    }

    private void saveReport(Report report) throws SQLException {
        allReports.put(report.getId(), report);
    }

    public InventoryReport importInventoryReport(int branchId) throws Exception {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        for (ProductBranch productBranch : branch.getAllProductBranches().values()) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();

            InventoryReportEntryDTO invRepEnDTO = new InventoryReportEntryDTO(reportID, code, shelfAmount,
                    storageAmount);
            inventoryReportEntryDAO.insert(invRepEnDTO);
        }

        InventoryReport report = getInventoryReport(reportID);
        return report;
    }

    public ExpiredAndFlawReport importExpiredAndFlawReport(int branchId) throws Exception {
        BranchController branchController = BranchController.getInstance();
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        HashMap<Integer, List<SpecificProduct>> productSpecificsExpired = branchController.getBranchExpired(branchId);
        for (Integer productCode : productSpecificsExpired.keySet()) {
            for (SpecificProduct specificProduct : productSpecificsExpired.get(productCode)) {
                int specificId = specificProduct.getSpecificId();
                ExpiredAndFlawReportEntryDTO expAndFlawEntDTO = new ExpiredAndFlawReportEntryDTO(reportID, specificId);
                expiredAndFlawReportEntryDAO.insert(expAndFlawEntDTO);
            }
        }
        ExpiredAndFlawReport report = getExpiredAndFlawReport(reportID);
        return report;
    }

    public DeficientReport importDeficientReport(int branchId) throws Exception {
        BranchController branchController = BranchController.getInstance();
        int reportID = Global.getNewReportId();
        ReportDTO repDTO = new ReportDTO(reportID, branchId, LocalDate.now());
        reportDAO.insert(repDTO);

        HashMap<Integer, ProductBranch> deficiencyProductsBranch = branchController
                .getBranchDeficiencyProducts(branchId);
        for (Integer productId : deficiencyProductsBranch.keySet()) {
            ProductBranch deficiencyProduct = deficiencyProductsBranch.get(productId);
            int missingAmount = deficiencyProduct.getMinQuantity() - deficiencyProduct.getTotalAmount();
            DeficiencyReportEntryDTO defEntDTO = new DeficiencyReportEntryDTO(reportID, deficiencyProduct.getCode(),
                    missingAmount);
            deficiencyReportEntryDAO.insert(defEntDTO);
        }

        DeficientReport report = getDeficientReport(reportID);
        return report;
    }

    public InventoryReport getInventoryReport(int reportId) throws InventoryException, SQLException {
        if (allReports.containsKey(reportId) && allReports.get(reportId) instanceof InventoryReport)
            return (InventoryReport) allReports.get(reportId);
        else if (allReports.containsKey(reportId) && !(allReports.get(reportId) instanceof InventoryReport))
            throw new InventoryException("this is not Inventory Report");

        InventoryReportDTO dto = inventoryReportEntryDAO.getFullReportById(reportId);
        if (dto == null)
            throw new InventoryException("this is not Inventory Report");

        Map<Integer, String> idsToNames = new HashMap<>();
        for (Integer productId : dto.getIdToShopAmount().keySet()) {
            String name = ProductController.getInstance().getProductById(productId).getName();
            idsToNames.put(productId, name);
        }

        InventoryReport report = new InventoryReport(dto, idsToNames);
        saveReport(report);
        return report;
    }

    public ExpiredAndFlawReport getExpiredAndFlawReport(int reportId) throws InventoryException, SQLException {
        if (allReports.containsKey(reportId) && allReports.get(reportId) instanceof ExpiredAndFlawReport)
            return (ExpiredAndFlawReport) allReports.get(reportId);
        else if (allReports.containsKey(reportId) && !(allReports.get(reportId) instanceof InventoryReport))
            throw new InventoryException("this is not Expired And Flaw Report");

        ExpiredAndFlawReportDTO dto = expiredAndFlawReportEntryDAO.getFullReportById(reportId);
        if (dto == null)
            throw new InventoryException("this is not Inventory Report");

        HashMap<Integer, ProductBranch> allProductsOfBranch = BranchController.getInstance()
                .getBranchById(dto.getBranchId()).getAllProductBranches();
        Map<Integer, ProductBranch> products = new HashMap<>();
        for (Integer productId : allProductsOfBranch.keySet()) {
            boolean isExpired = dto.getExpiredProducts().stream().filter(sp -> sp.getGeneralId() == productId)
                    .count() > 0;
            boolean isFlaw = dto.getFlawProducts().stream().filter(sp -> sp.getGeneralId() == productId).count() > 0;
            if (isExpired || isFlaw)
                products.put(productId, allProductsOfBranch.get(productId));
        }

        ExpiredAndFlawReport report = new ExpiredAndFlawReport(dto, products);

        saveReport(report);
        return report;
    }

    public DeficientReport getDeficientReport(int reportId) throws InventoryException, SQLException {
        if (allReports.containsKey(reportId) && allReports.get(reportId) instanceof DeficientReport)
            return (DeficientReport) allReports.get(reportId);
        else if (allReports.containsKey(reportId) && !(allReports.get(reportId) instanceof DeficientReport))
            throw new InventoryException("this is not Deficient Report");

        DeficiencyReportDTO dto = deficiencyReportEntryDAO.getFullReportById(reportId);
        if (dto == null)
            throw new InventoryException("this is not Inventory Report");

        HashMap<Integer, ProductBranch> allProductsOfBranch = BranchController.getInstance()
                .getBranchById(dto.getBranchId()).getAllProductBranches();
        Map<Integer, ProductBranchDTO> productsDTOs = dto.getIdToProductBranch();
        Map<Integer, ProductBranch> products = new HashMap<>();
        for (Integer productId : productsDTOs.keySet())
            products.put(productId, allProductsOfBranch.get(productId));

        DeficientReport report = new DeficientReport(dto, products);

        saveReport(report);
        return report;
    }

}
