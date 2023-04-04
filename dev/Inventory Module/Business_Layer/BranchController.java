package Business_Layer;

import java.util.ArrayList;
import java.util.List;

public class BranchController {
    List<Branch> allBranches;

    public BranchController(){
        this.allBranches = new ArrayList<>();
    }

    public void addBranch(int branchId){
        Branch newBranch = new Branch(branchId);
        allBranches.add(newBranch);
    }
    public Branch getBranch(int branchId){
        Branch result = null;
        for(Branch branch : allBranches){
            if(branch.getId() == branchId);{
                result = branch;
            }
        }
        return result;
    }

    public ProductController getBranchProductController(int branchId){
        Branch branch = getBranch(branchId);
        return branch.getProductController();

    }
    public ReportController getBranchReportController(int branchId){
        Branch branch = getBranch(branchId);
        return branch.getReportController();
    }
}
