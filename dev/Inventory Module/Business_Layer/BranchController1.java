package Business_Layer;

import java.util.ArrayList;
import java.util.List;

public class BranchController1 {

  private List<Branch1> allBranch1s;

  public BranchController1() {
    this.allBranch1s = new ArrayList<>();
  }

  public void addBranch(int branchId) {
    Branch1 newBranch1 = new Branch1(branchId);
    allBranch1s.add(newBranch1);
  }

  public Branch1 getBranch(int branchId) {
    Branch1 result = null;
    for (Branch1 branch1 : allBranch1s) {
      if (branch1.getId() == branchId);
      {
        result = branch1;
      }
    }
    return result;
  }

  public ProductController1 getBranchProductController(int branchId) {
    Branch1 branch1 = getBranch(branchId);
    return branch1.getProductController();
  }

  public ReportController1 getBranchReportController(int branchId) {
    Branch1 branch1 = getBranch(branchId);
    return branch1.getReportController();
  }
}
