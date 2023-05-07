package Service_Layer;

import Business_Layer.Branch1;
import Business_Layer.BranchController1;

import java.util.ArrayList;
import java.util.List;

public class BranchService {

  BranchController1 branchController1;
  List<ProductService> allProductService;
  List<ReportService> allReportServices;
  int index = 1;

  public BranchService() {
    this.branchController1 = new BranchController1();
    this.allProductService = new ArrayList<>();
    this.allReportServices = new ArrayList<>();
    addBranch();
  }

  public void addBranch() {
    branchController1.addBranch(index);
    ProductService ps = new ProductService(index);
    allProductService.add(ps);
    allReportServices.add(new ReportService(ps));
    index++;
  }

  public Branch1 getBranch(int branchID) {
    return branchController1.getBranch(branchID);
  }

  public ProductService getBranchProductService(int branchId) {
    ProductService result = null;
    for (ProductService ps : allProductService) {
      if (ps.getBranchId() == branchId) result = ps;
    }
    return result;
  }

  public ReportService getBranchReportService(int branchId) {
    ReportService result = null;
    for (ReportService rs : allReportServices) {
      if (rs.getBranchId() == branchId) result = rs;
    }
    return result;
  }
}
