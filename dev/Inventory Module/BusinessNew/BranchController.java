package BusinessNew;

import Business_Layer.Branch1;

import java.util.HashMap;

public class BranchController {
    private HashMap<Integer,Branch> allBranches;//maps between brnachId and branchObject
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
    public void addBranch(int branchId,String branchName) {
        Branch newBranch = new Branch(branchId,branchName);
        allBranches.put(branchId,newBranch) ;
    }
    private Branch getBranch(String name){
        Branch result = null;
        for(Branch branch : allBranches.values()){
            if(branch.getName() == name)
                result = branch;
        }
        return result;
    }

    public void receiveReservation(Reservation reservation) throws Exception {
        Branch branch = getBranch(reservation.getDestinationBranch);
        if(branch == null){
            throw new Exception("Branch not exist");
        }
        else
            branch.receiveReservation(reservation);
    }

}


