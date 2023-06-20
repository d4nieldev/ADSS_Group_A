package BusinessLayer.Inventory;

import BusinessLayer.InveontorySuppliers.*;
import BusinessLayer.exceptions.SuppliersException;
import DataAccessLayer.DAOs.*;
import DataAccessLayer.DTOs.*;

import java.sql.SQLException;
import java.time.LocalDate;
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

    public HashMap<Integer, List<SpecificProduct>> getBranchFlaws(int branchId) throws Exception {
        HashMap<Integer, List<SpecificProduct>> allFlaws = new HashMap<>();
        if (allBranches.containsKey(branchId)) {
            Branch branch = allBranches.get(branchId);
            allFlaws = branch.getBranchFlaws();
            return allFlaws;
        } else {
            try {
                Branch branch;

                branch = LoadBranchFromData(branchId);
                if (branch != null) {
                    allFlaws = branch.getBranchFlaws();
                    return allFlaws;
                }
            } catch (Exception e) {
                throw new Exception("failed to import from data");
            }
        }
        return allFlaws;
    }

    private Branch LoadBranchFromData(int branchId) throws SQLException {
        BranchDTO branchDTO = branchDAO.getAll(branchId);
        Branch branch = new Branch(branchDTO);
        allBranches.put(branch.getId(), branch);
        return branch;
    }

    // public HashMap<Integer, List<SpecificProduct>> getBranchExpired(int branchId)
    // throws Exception {
    // HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
    // if (allBranches.containsKey(branchId)) {
    // Branch branch = allBranches.get(branchId);
    // result = branch.getExpiredProducts();
    // updateExpiredProducts(result);
    // return result;
    // } else {try{
    // Branch branch;
    // branch = LoadBranchFromData(branchId);
    // result = branch.getExpiredProducts();
    // updateExpiredProducts(result);
    // return result;
    // }
    // catch (Exception e) {
    // throw new Exception("failed to import from data");
    // }
    // }
    // }
    public HashMap<Integer, List<SpecificProduct>> getBranchExpired(int branchId) throws Exception {
        HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
        Branch branch = getBranchById(branchId);
        result = branch.getExpiredProducts();
        updateExpiredProducts(result);
        return result;
        // if (allBranches.containsKey(branchId)) {
        // Branch branch = allBranches.get(branchId);
        // result = branch.getExpiredProducts();
        // updateExpiredProducts(result);
        // return result;
        // } else {try{
        // Branch branch;
        // branch = LoadBranchFromData(branchId);
        // result = branch.getExpiredProducts();
        // updateExpiredProducts(result);
        // return result;
        // }
        // catch (Exception e) {
        // throw new Exception("failed to import from data");
        // }
        // }
    }

    public HashMap<Integer, ProductBranch> getBranchDeficiencyProducts(int branchId) throws SQLException {
        Branch branch = BranchController.getInstance().getBranchById(branchId);
        return branch.getAllDeficiencyProductsBranch();
    }

    private void updateExpiredProducts(HashMap<Integer, List<SpecificProduct>> result) throws SQLException {
        for (Integer productCode : result.keySet()) {
            for (SpecificProduct specificProduct : result.get(productCode)) {
                SpecificProductDTO specificProductDTO = specificProductDAO.getById(specificProduct.getSpecificId());
                specificProductDTO.updateStatus(ProductStatus.status.EXPIRED);
                // specificProductDAO.delete(specificProductDTO);
                // specificProductDAO.insert(specificProductDTO);
                specificProductDAO.update(specificProductDTO);
            }
        }
    }
    // private void updateDeficiencyProducts(HashMap<Integer, ProductBranch> result,
    // int branchId) throws SQLException {
    // for(Integer productCode : result.keySet()){
    // ProductBranchDTO productBranchDTO =
    // productBranchDAO.getByProductAndBranchId(productCode,branchId);
    // productBranchDTO.
    // specificProductDTO.updateStatus(ProductStatus.status.EXPIRED);
    // specificProductDAO.delete(specificProductDTO);
    // specificProductDAO.insert(specificProductDTO);
    // }
    // }
    // }

    public void addBranch(int branchId, String branchName, int minAmount) throws Exception {
        try {
            List<PeriodicReservationDTO> allBranchReservations = new ArrayList<>();
            BranchDTO branchDTO = new BranchDTO(branchId, branchName, minAmount, allBranchReservations);
            branchDAO.insert(branchDTO);
            Branch newBranch = new Branch(branchDTO);
            allBranches.put(branchId, newBranch);
        } catch (Exception e) {
            throw new Exception("already exist");
        }

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
        System.out.println("======================================== \n New reservation has arrived to branch: "
                + reservation.getDestination() + "\n ======================================== ");
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
        // if exist - update table with the updated dto after receive reservation
        if (productBranchDTO != null) {
            productBranchDAO.update(productBranchDTO);
        }
        // if not exist - create new one and insert it to the DAO
        else {
            ProductDTO productDTO = ProductsDAO.getInstance().getById(productBranch.getCode());
            Discount d = productBranch.getDiscount();
            DiscountDTO discountDTO;
            if (d == null) {
                // TODO: make sure this isn't a bug
                discountDTO = null;
            } else {
                discountDTO = DiscountDAO.getInstance().getById(d.getDiscountId());
            }
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
                    discount.getDiscountValue(), "Fixed");
        } else {
            discountDTO = new DiscountDTO(discount.getDiscountId(), discount.getStart_date(), discount.getEnd_date(),
                    discount.getDiscountValue(), "Precentage");
        }
        discountDAO.insert(discountDTO);
        Branch branch = getBranchById(branchId);
        // return a list of all products who the new discount on them been changed
        List<ProductBranch> productsToDiscount = branch.getProductsByCode(products);
        List<ProductBranch> changeDiscount = branch.setDiscountOnProducts(productsToDiscount, discount, discountDTO);

        // add the discount to the product and update the discount DTO on PRODUCT
        for (ProductBranch productBranch : changeDiscount) {
            ProductBranchDTO productBranchDTO = productBranchDAO.getByProductAndBranchId(productBranch.getCode(),
                    branchId);
            productBranchDAO.delete(productBranchDTO);
            productBranchDAO.insert(productBranchDTO);
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
    public List<ProductBranch> setDiscountOnCategories(int branchId, List<Integer> categoriesToDiscount,
            Discount discount)
            throws Exception {

        // Branch branch = checkBranchExist(branchId);
        Branch branch = getBranchById(branchId);
        CategoryController categoryController = CategoryController.getInstance();
        List<Category> allCategories = categoryController.getCategoriesByIds(categoriesToDiscount);
        List<Category> allSubCategories = categoryController.getListAllSubCategories(allCategories);
        List<ProductBranch> productsToDiscount = branch.getProductsByCategories(allSubCategories);
        List<Integer> productsToCode = branch.getCodeByProducts(productsToDiscount);
        setDiscountOnProducts(branchId, productsToCode, discount);
        return productsToDiscount;
    }

    // public void setDiscountOnCategories(List<Category> categoriesToDiscount,
    // Discount discount, int branchId) throws Exception {
    // List<Category> allSubCategories =
    // CategoryController.getInstance().getListAllSubCategories(categoriesToDiscount);
    // List<ProductBranch> productsFromCategory =
    // getProductsByCategories(allSubCategories,branchId);
    // setDiscountOnProducts(productsFromCategory, discount,branchId);
    // }

    public void receiveSupply(int code, int amount, double buyPrice, LocalDate expiredDate, int branchId)
            throws Exception {
        List<SpecificProduct> specificProducts = allBranches.get(branchId).getProductByCode(code).receiveSupply(amount,
                buyPrice, expiredDate, branchId);
        for (SpecificProduct specificProduct : specificProducts) {
            SpecificProductDTO specificProductDTO = specificProduct.getSpecificProductDTO();
            specificProductDAO.insert(specificProductDTO);
        }
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
        Branch branch = getBranchById(branchId);
        SpecificProduct sp = branch.sellProduct(productCode, specificId);
        SpecificProductDTO specificProductDTO = sp.getSpecificProductDTO();
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

    public ProductBranch addNewProductBranch(int productId, int branchId, LocalDate discountStartDate,
            LocalDate discountEndDate, double discountVal, boolean isDiscountPrecentage, double price,
            int minQuantity, int idealQuantity) throws SQLException {
        getBranchById(branchId); // just to check if exists
        // if(ProductController.getInstance().productExists(productId))
        ProductDTO productDTO = ProductController.getInstance().getProductById(productId).getDTO();
        DiscountDTO discountDTO = null;
        if (discountStartDate != null) {
            int discountId = Global.getNewDiscountId();
            discountDTO = new DiscountDTO(discountId, discountStartDate, discountEndDate,
                    discountVal, isDiscountPrecentage ? "Precentage" : "Fixed");
            discountDAO.insert(discountDTO);
            ProductBranchDiscountDTO branchDiscountDTO = new ProductBranchDiscountDTO(productId, branchId, discountDTO);
            productBranchDiscountsDAO.insert(branchDiscountDTO);
        }

        ProductBranchDTO productBranchDTO = new ProductBranchDTO(productDTO, discountDTO, branchId, price, minQuantity,
                idealQuantity, new HashMap<>());
        productBranchDAO.insert(productBranchDTO);
        return allBranches.get(branchId).addNewProductBranch(productBranchDTO);
    }

    public void clearData() {
        allBranches.clear();
    }

}
