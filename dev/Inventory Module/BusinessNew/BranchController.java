package BusinessNew;

import java.util.HashMap;

public class BranchController {
    private HashMap<Integer,Branch> allBranches;
    private static BranchController instance = null;

    private BranchController() {
        this.allBranches = new HashMap<>();
    }

    public static BranchController getInstance() {
        if (instance == null) {
            instance = new BranchController();
        }
        return instance;
    }
}


