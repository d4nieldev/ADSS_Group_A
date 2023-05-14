package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.*;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.*;
import DataAccessLayer.DTOs.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BranchController {
    private HashMap<Integer, Branch> allBranches;// maps between brnachId and branchObject
    private static BranchController instance = null;
    private BranchDAO branchDAO;
    private ProductBranchDAO productBranchDAO;
    private SpecificProductDAO specificProductDAO;
    private DiscountDAO discountDAO;
    private ProductBranchDiscountsDAO productBranchDiscountsDAO;
    private PeriodicReservationDAO periodicReservationDAO;
    private PeriodicReservationItemDAO periodicReservationItemDAO;

    //
    private BranchController() {
        this.allBranches = new HashMap<>();
        this.branchDAO = BranchDAO.getInstance();
        this.productBranchDAO = ProductBranchDAO.getInstance();
        this.specificProductDAO = SpecificProductDAO.getInstance();
        this.discountDAO = DiscountDAO.getInstance();
        this.productBranchDiscountsDAO = ProductBranchDiscountsDAO.getInstance();
        this.periodicReservationDAO = PeriodicReservationDAO.getInstance();
        this.periodicReservationItemDAO = PeriodicReservationItemDAO.getInstance();

    }

    public static BranchController getInstance() {//
        if (instance == null) {
            instance = new BranchController();
        }
        return instance;
    }

    public HashMap<Integer, List<SpecificProduct>> getBranchFlaws(int branchId) {
        HashMap<Integer, List<SpecificProduct>> allFlaws = new HashMap<>();
        Branch branch = allBranches.get(branchId);
        allFlaws = branch.getBranchFlaws();
        return allFlaws;
    }

    public HashMap<Integer, List<SpecificProduct>> getBranchExpired(int branchId) throws SQLException {
        HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
        Branch branch = allBranches.get(branchId);
        result = branch.getExpiredProducts();
        return result;
    }

    public void addBranch(int branchId, String branchName, int minAmount) throws SQLException {
        List<PeriodicReservationDTO> allBranchReservations = new ArrayList<>();
        BranchDTO branchDTO = new BranchDTO(branchId, branchName, minAmount, allBranchReservations);
        branchDAO.insert(branchDTO);
        Branch newBranch = new Branch(branchDTO);
        allBranches.put(branchId, newBranch);
    }

    public Branch getBranchById(int branchId) throws SuppliersException, SQLException {
        if (allBranches.containsKey(branchId))
            return allBranches.get(branchId);
        else {
            BranchDTO branchDTO = branchDAO.getById(branchId);
            if (branchDTO == null) {
                throw new SuppliersException("this branch doesnt exist");
            }
            Branch branch = new Branch(branchDTO);
            allBranches.put(branchId, branch);
            return branch;
        }
    }

    public Branch getBranchByName(String name) throws SQLException {
        Branch result = null;
        for (Branch branch : allBranches.values()) {
            if (branch.getName() == name)
                result = branch;
        }
        if (result == null) {
            BranchDTO branchDTO = branchDAO.getByName(name);
            Branch branch = new Branch(branchDTO);
            allBranches.put(branch.getId(), branch);
            result = branch;
        }
        return result;
    }

    public void receiveReservation(Reservation reservation) throws SuppliersException, SQLException {
        Branch branch = getBranchById(reservation.getDestination());
        if (branch == null) {
            throw new SuppliersException("Branch not exist");
        } else {
            HashMap<ProductBranch, List<SpecificProduct>> toDao = branch.receiveReservation(reservation);
            for (ProductBranch productBranch : toDao.keySet()) {
                updateProductBranchOrCreate(productBranch, reservation.getDestination());
                for (SpecificProduct specificProduct : toDao.get(productBranch)) {
                    // create new SpecificProductDto object and insert it to DAO
                    SpecificProductDTO specificProductDTO = new SpecificProductDTO(specificProduct.getSpecificId(),
                            specificProduct.getGeneralId(), branch.getId(),
                            specificProduct.getBuyPrice(), specificProduct.getSellPrice(), specificProduct.getStatus(),
                            specificProduct.getFlawDescription(), specificProduct.getExpiredDate(),
                            specificProduct.getArrivedDate());
                    specificProductDAO.insert(specificProductDTO);
                }

            }
            BranchDTO branchDTO = branch.getBranchDTO();
            branchDAO.update(branchDTO);
        }
    }

    /***
     * check if the DTO is exist - return if so - else create new one
     *
     * @param productBranch
     * @param branchId
     * @return
     * @throws SQLException
     */
    private void updateProductBranchOrCreate(ProductBranch productBranch, int branchId) throws SQLException {
        ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranchId(productBranch.getCode(), branchId);
        //if exist - update table with the updated dto after receive reservation
        if (productBranchDTO != null) {
            productBranchDAO.update(productBranchDTO);
        }
        //if not exist - create new one and insert it to the DAO
        else {
            ProductDTO productDTO = ProductsDAO.getInstance().getById(productBranch.getCode());
            DiscountDTO discountDTO = DiscountDAO.getInstance().getById(productBranch.getDiscount().getDiscountId());
            ProductBranchDTO result = new ProductBranchDTO(productDTO, discountDTO, branchId, productBranch.getPrice(),
                    productBranch.getMinQuantity(), productBranch.getIdealQuantity(), null);
            productBranchDAO.insert(result);
        }
    }

    private Branch checkBranchExist(int branchId) throws Exception {
        if (allBranches.containsKey(branchId))
            return allBranches.get(branchId);
        else {
            BranchDTO branchDTO = branchDAO.getById(branchId);
            if (branchDTO == null) {
                throw new Exception("this branch doesn't exist");
            }
            Branch branch = new Branch(branchDTO);
            allBranches.put(branchId, branch);
            return branch;
        }

    }

    /***
     * create dto object for the discount -> add it to DiscountDAO -> and find all
     * the products that the discount on them
     * changed -> update their discount
     *
     * @param
     * @param discount
     * @param branchId
     * @throws Exception
     */
    public void setDiscountOnProducts(int branchId, List<Integer> products, Discount discount) throws Exception {
        DiscountDTO discountDTO = null;
        if (discount instanceof DiscountFixed) {
            discountDTO = new DiscountDTO(discount.getDiscountId(), discount.getStart_date(), discount.getEnd_date(),
                    discount.getDiscountValue(), "fixed Discount");
        } else {
            discountDTO = new DiscountDTO(discount.getDiscountId(), discount.getStart_date(), discount.getEnd_date(),
                    discount.getDiscountValue(), "Percentage discount");
        }
        discountDAO.insert(discountDTO);
        Branch branch = allBranches.get(branchId);
        // return a list of all products who the new discount on them been changed
        List<ProductBranch> productsToDiscount = branch.getProductsByCode(products);
        List<ProductBranch> changeDiscount = branch.setDiscountOnProducts(productsToDiscount, discount);

        // add the discount to the product and update the discount DTO on PRODUCT
        for (ProductBranch productBranch : changeDiscount) {
            ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranchId(productBranch.getCode(),
                    branchId);
            productBranchDAO.update(productBranchDTO);
            ProductBranchDiscountDTO productBranchDiscountDTO = new ProductBranchDiscountDTO(productBranch.getCode(),
                    branchId, discountDTO);
            productBranchDiscountsDAO.insert(productBranchDiscountDTO);
        }
    }

    /***
     * find all products from catgories -> apply on them setDiscountOnProducts
     * function
     *
     * @param categoriesToDiscount
     * @param discount
     * @param branchId
     * @throws Exception
     */
    public void setDiscountOnCategories(int branchId, List<Integer> categoriesToDiscount, Discount discount)
            throws Exception {

        Branch branch = checkBranchExist(branchId);
        CategoryController categoryController = CategoryController.getInstance();
        List<Category> allCategories = categoryController.getCategoriesByIds(categoriesToDiscount);
        List<Category> allSubCategories = categoryController.getListAllSubCategories(allCategories);
        List<ProductBranch> productsToDiscount = branch.getProductsByCategories(allSubCategories);
        List<Integer> productsToCode = branch.getCodeByProducts(productsToDiscount);
        setDiscountOnProducts(branchId, productsToCode, discount);
    }

    // public void setDiscountOnCategories(List<Category> categoriesToDiscount,
    // Discount discount, int branchId) throws Exception {
    // List<Category> allSubCategories =
    // CategoryController.getInstance().getListAllSubCategories(categoriesToDiscount);
    // List<ProductBranch> productsFromCategory =
    // getProductsByCategories(allSubCategories,branchId);
    // setDiscountOnProducts(productsFromCategory, discount,branchId);
    // }

    /***
     * private method for receving the product of branch by categories
     *
     * @param allSubCategories
     * @param branchId
     * @return
     */
    private List<ProductBranch> getProductsByCategories(List<Category> allSubCategories, int branchId) {
        Branch branch = allBranches.get(branchId);
        List<ProductBranch> productFromCategories = branch.getProductsByCategories(allSubCategories);
        return productFromCategories;
    }

    /**
     * sell a product
     *
     * @param branchId
     * @param productCode
     * @param specificId
     * @throws Exception
     */
    public void sellProduct(int branchId, int productCode, int specificId) throws Exception {
        Branch branch = allBranches.get(branchId);
        SpecificProduct sp = branch.sellProduct(productCode, specificId);
        SpecificProductDTO specificProductDTO = specificProductDAO.getById(specificId);
        specificProductDAO.update(specificProductDTO);

    }

    /**
     * report on a flaw product
     *
     * @param branchId
     * @param productCode
     * @param specificId
     * @param description
     * @throws Exception
     */
    public void reportFlawProduct(int branchId, int productCode, int specificId, String description) throws Exception {
        Branch branch = checkBranchExist(branchId);
        ProductBranch productBranch = branch.reportFlawProduct(productCode, specificId, description);
        SpecificProductDTO specificProductDTO = productBranch.getSpecificById(specificId).getSpecificProductDTO();
        specificProductDAO.update(specificProductDTO);
        ProductBranchDTO productBranchDTO = productBranch.getProductBranchDTO();
        productBranchDAO.update(productBranchDTO);
        BranchDTO branchDTO = branch.getBranchDTO();
        branchDAO.update(branchDTO);

    }

}
