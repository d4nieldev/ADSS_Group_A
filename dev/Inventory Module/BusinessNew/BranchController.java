package BusinessNew;

import java.util.HashMap;
import java.util.List;

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

    public HashMap<Integer,List<SpecificProduct>> getBranchFlaws(int branchId) {
        HashMap<Integer,List<SpecificProduct>> allFlaws  = new HashMap<>();
        Branch branch = allBranches.get(branchId);
        allFlaws = branch.getBranchFlaws();
        return allFlaws;
    }
    public HashMap<Integer, List<SpecificProduct>> getBranchExpired(int branchId) {
        HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
        Branch branch = allBranches.get(branchId);
        result = branch.getExpiredProducts();
        return result;
    }
    public void addBranch(int branchId,String branchName) {
        Branch newBranch = new Branch(branchId,branchName);
        allBranches.put(branchId,newBranch) ;
    }
    public Branch getBranchById(int branchId){
        return allBranches.get(branchId);
    }
    public Branch getBranchByName(String name){
        Branch result = null;
        for(Branch branch : allBranches.values()){
            if(branch.getName() == name)
                result = branch;
        }
        return result;
    }


    public void receiveReservation(Reservation reservation) throws Exception {
        Branch branch = getBranchByName(reservation.getDestinationBranch);
        if(branch == null){
            throw new Exception("Branch not exist");
        }
        else
            branch.receiveReservation(reservation);
    }


}


