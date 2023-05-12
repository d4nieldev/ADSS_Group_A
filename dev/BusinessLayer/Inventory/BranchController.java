package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.Branch;
import BusinessLayer.InveontorySuppliers.Reservation;
import DataAccessLayer.DAOs.*;
import DataAccessLayer.DTOs.*;

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
        BranchDTO branchDTO = new BranchDTO(branchId,branchName,minAmount);
        branchDAO.insert(branchDTO);
        Branch newBranch = new Branch(branchDTO);
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
            for (ProductBranch productBranch : toDao.keySet()){
                ProductBranchDTO productBranchDTO = checkExistProductDTO(productBranch,reservation.getDestination());
                productBranchDAO.insert(productBranchDTO);
                for(SpecificProduct specificProduct : toDao.get(productBranch)){
                    //create new SpecificProductDto object and insert it to DAO
                    SpecificProductDTO specificProductDTO = new SpecificProductDTO(specificProduct.getSpecificId(),specificProduct.getGeneralId(), branch.getId(),
                            specificProduct.getBuyPrice(),specificProduct.getSellPrice(),specificProduct.getStatus(),specificProduct.getFlawDescription(),specificProduct.getExpiredDate(),
                            specificProduct.getArrivedDate());
                    specificProductDAO.insert(specificProductDTO);
                }

            }

        }
    }

    /***
     * check if the DTO is exist - return if so - else create new one
     * @param productBranch
     * @param branchId
     * @return
     * @throws SQLException
     */
    private ProductBranchDTO checkExistProductDTO(ProductBranch productBranch, int branchId) throws SQLException {
        ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranchId(productBranch.getCode(),branchId);
        if (productBranchDTO != null)
            return productBranchDTO;
        else
        {
            ProductDTO productDTO = ProductsDAO.getInstance().getById(productBranch.getCode());
            DiscountDTO discountDTO = DiscountDAO.getInstance().getById(productBranch.getDiscount().getDiscountId());
            ProductBranchDTO result = new ProductBranchDTO(productDTO,discountDTO,branchId,productBranch.getPrice(),productBranch.getMinQuantity(),productBranch.getIdealQuantity(),null);
            return result;
        }


    }


}


