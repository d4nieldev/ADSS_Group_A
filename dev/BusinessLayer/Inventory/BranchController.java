package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Reservation;
import DataAccessLayer.DAOs.BranchDAO;
import DataAccessLayer.DAOs.ProductBranchDAO;
import DataAccessLayer.DAOs.SpecificProductDAO;
import DataAccessLayer.DTOs.BranchDTO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class BranchController {
    private HashMap<Integer, Branch> allBranches;//maps between brnachId and branchObject
    private static BranchController instance = null;
    private BranchDAO branchDAO;
    private ProductBranchDAO productBranchDAO;
    private SpecificProductDAO specificProductDAO;


    private BranchController() {
        this.allBranches = new HashMap<>();
        this.branchDAO = BranchDAO.getInstance();
        this.productBranchDAO = ProductBranchDAO.getInstance();
        this.specificProductDAO = SpecificProductDAO.getInstance();

    }

    public static BranchController getInstance() {//
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
    public void addBranch(int branchId,String branchName, int minAmount) throws SQLException {
        BranchDTO branchDTO = new BranchDTO(branchId,branchName);
        branchDAO.insert(branchDTO);
        Branch newBranch = new Branch(branchId,branchName,minAmount);
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
        Branch branch = getBranchById(reservation.getDestination());
        if(branch == null){
            throw new Exception("Branch not exist");
        }
        else {
            HashMap<ProductBranch,List<SpecificProduct>> toDao = branch.receiveReservation(reservation);
        }
    }


}


