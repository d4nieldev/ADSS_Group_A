package BusinessNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Branch {
    private int branchId;
    private String branchName;
    HashMap<Integer,ProductBranch> allProductBranches;
    public Branch(int branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.allProductBranches = new HashMap<>();
    }

    public int getId() {
        return this.branchId;
    }
    public String getName() {
        return this.branchName;
    }

}
