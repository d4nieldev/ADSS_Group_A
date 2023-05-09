package BusinessNew;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ExpiredAndFlawReport extends Report{
    private HashMap<Integer,List<SpecificProduct>> idToExpiredAmount; //maps between productBranch Code to the number of expired specificProduct;
    private HashMap<Integer , List<SpecificProduct>> productCodeToAllItsFlaws; //maps between product code and all its specificProducts that flaws

    public ExpiredAndFlawReport(int id, int branchId, LocalDate creationDate) {
        super(id, branchId, creationDate);
        BranchController branchController = BranchController.getInstance() ;
        this.idToExpiredAmount = branchController.getBranchExpired(branchId);
        this.productCodeToAllItsFlaws = branchController.getBranchFlaws(branchId);
    }

}
