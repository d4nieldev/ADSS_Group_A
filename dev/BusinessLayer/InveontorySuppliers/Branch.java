package BusinessLayer.InveontorySuppliers;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.ProductStatus;
import BusinessLayer.Inventory.SpecificProduct;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.exceptions.InventoryException;
import DataAccessLayer.DAOs.DiscountDAO;
import DataAccessLayer.DAOs.ProductBranchDAO;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.*;

public class Branch {
    private int branchId;
    private String branchName;
    private HashMap<Integer, ProductBranch> allProductBranches;// maps between productBranch generalId to its object
    // private List<PeriodicReservation> periodicReservations;
    private HashMap<Integer, PeriodicReservation> supplierToPeriodicReservations;
    private CategoryController categoryController;
    private HashMap<Integer, Integer> productToAmount; // maps between product to amount for deficiency quantity
    private int minAmountForDeficiencyReservation;
    private BranchDTO branchDTO;

    // public Branch(int branchId, String branchName,int minAmount) {
    // this.branchId = branchId;
    // this.branchName = branchName;
    // this.allProductBranches = new HashMap<>();
    // this.periodicReservations = new ArrayList<>();
    // this.categoryController = CategoryController.getInstance();
    // this.minAmountForDeficiencyReservation = minAmount;
    // }

    public Branch(BranchDTO branchDTO) {
        this.branchDTO = branchDTO;
        this.branchId = branchDTO.getId();
        this.branchName = branchDTO.getName();
        this.allProductBranches = new HashMap<>();
        this.productToAmount = new HashMap<>();
        HashMap<Integer, PeriodicReservation> res = new HashMap<>();
        this.supplierToPeriodicReservations = new HashMap<>();
        this.categoryController = CategoryController.getInstance();
        this.minAmountForDeficiencyReservation = branchDTO.getMinAmount();
    }

    public int getId() {
        return this.branchId;
    }

    public String getName() {
        return this.branchName;
    }

    public HashMap<Integer, ProductBranch> getAllProductBranches() throws SQLException {
        loadAllProductBranch();

        return allProductBranches;
    }

    public ProductBranch addNewProductBranch(ProductBranchDTO productBranchDTO) throws SQLException {
        ProductBranch newProduct = new ProductBranch(productBranchDTO);
        allProductBranches.put(newProduct.getCode(), newProduct);
        return newProduct;
    }
    // public void receiveSupply(int generalId) {
    // LocalDate tomorrow = LocalDate.now().plusDays(1);
    // LocalDate yesterday = LocalDate.now().minusDays(1);
    // // SpecificProduct sp1 = new SpecificProduct(generalId,20,tomorrow);
    // // SpecificProduct sp2 = new SpecificProduct(generalId,20,tomorrow);
    // // SpecificProduct sp3 = new SpecificProduct(generalId,20,yesterday);
    // // SpecificProduct sp4 = new SpecificProduct(generalId,20,yesterday);
    //// ProductBranch productBranch = allProductBranches.get(generalId);
    //// productBranch.receiveSupply(2, 15, tomorrow);
    //// productBranch.receiveSupply(2, 15, yesterday);
    //
    // }

    /***
     * return an hash map with all new productBranch - or updated productBranch
     * 
     * @param reservation
     * @return
     * @throws SQLException
     */
    public HashMap<ProductBranch, List<SpecificProduct>> receiveReservation(Reservation reservation)
            throws SQLException {
        HashMap<ProductBranch, List<SpecificProduct>> toDao = new HashMap<>();
        List<ReceiptItem> allItems = reservation.getReceipt();
        for (ReceiptItem ri : allItems) {
            int amount = ri.getAmount();
            double buyPrice = ri.getPricePerUnitAfterDiscount();
            Product product = ri.getProduct();
            LocalDate expiredDate = ri.getExpiredDate();
            int id = product.getId();
            loadProductBranch(id);
            if (!allProductBranches.containsKey(id)) {
                int idealQuantity = 100;
                int minQuantity = 50;
                ProductsDAO productsDAO = ProductsDAO.getInstance();
                DiscountDAO discountDAO = DiscountDAO.getInstance();
                // TODO: chack if the product belong to some discount -> if yes add the
                // discount's dto ' else should be null
                ProductBranchDTO productBranchDTO = new ProductBranchDTO(productsDAO.getById(id),
                        discountDAO.getById(-1), reservation.getDestination(), buyPrice, minQuantity, idealQuantity,
                        new HashMap<Integer, SpecificProductDTO>());
                ProductBranch newProductBranch = new ProductBranch(productBranchDTO);
                addNewProductBranch(productBranchDTO);
            }
            ProductBranch productBranch = allProductBranches.get(id);
            List<SpecificProduct> addedSpecifics = productBranch.receiveSupply(amount, buyPrice, expiredDate,
                    reservation.getDestination());
            toDao.put(productBranch, addedSpecifics);
        }
        return toDao;
    }

    private ProductBranch loadProductBranch(int id) {
        ProductBranch productBranch = null;
        try {
            ProductBranchDTO productBranchDTO = ProductBranchDAO.getInstance().getByProductAndBranchId(id, branchId);
            if (productBranchDTO != null) {
                productBranch = new ProductBranch(productBranchDTO);
                allProductBranches.put(productBranch.getCode(), productBranch);
                productBranch.loadSpecificByProductId(productBranch.getCode());
                return productBranch;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public SpecificProduct sellProduct(int code, int specificId) throws Exception {
        ProductBranch productBranch = loadProductBranch(code);
        if (productBranch == null)
            throw new Exception("this product doesn't exist");
        SpecificProduct sp = productBranch.sellProduct(specificId);
        checkDeficiencyAfterUpdate(productBranch);
        CheckForDeficiencyReservation();
        return sp;
    }

    public void CheckForDeficiencyReservation() throws SQLException {
        boolean overCapacity = getTotalDeficiencyAmount() > minAmountForDeficiencyReservation;
        if (overCapacity) {
            makeDeficiencyReservation();
        }
    }

    private int getTotalDeficiencyAmount() {

        int result = 0;
        for (Integer amount : productToAmount.values()) {
            result += amount;
        }
        return result;
    }

    private void alertForDeficiency(ProductBranch productBranch) {
        System.out.println("Product " + productBranch.getName() + " is below the mini,um Quantity");
    }

    public HashMap<Integer, List<SpecificProduct>> getExpiredProducts() throws SQLException {
        HashMap<Integer, List<SpecificProduct>> allExpiredProducts = new HashMap<>();
        loadAllProductBranch();
        for (ProductBranch productBranch : allProductBranches.values()) {
            List<SpecificProduct> expiredProducts = productBranch.getAllExpired();
            allExpiredProducts.put(productBranch.getCode(), expiredProducts);
            checkDeficiencyAfterUpdate(productBranch);
        }
        CheckForDeficiencyReservation();
        return allExpiredProducts;
    }

    private void loadAllProductBranch() throws SQLException {
        List<ProductBranchDTO> load = ProductBranchDAO.getInstance().selectAllbyId(branchId);
        for (ProductBranchDTO productBranchDTO : load){
            if(!allProductBranches.containsKey(productBranchDTO.getProductDTO().getId())) {
                ProductBranch productBranch = new ProductBranch(productBranchDTO);
                allProductBranches.put(productBranch.getCode(),productBranch);
            }
        }
    }

    public ProductBranch reportFlawProduct(int productCode, int specificId, String description) throws Exception {
        ProductBranch productBranch = loadProductBranch(productCode);
        if (productBranch == null)
            throw new Exception("this product doesn't exist in the branch");
        productBranch.reportFlawProduct(specificId, description);
        checkDeficiencyAfterUpdate(productBranch);
        CheckForDeficiencyReservation();
        return productBranch;

    }

    /***
     * checks for deficiency after updae - if exist make deficiency reservations
     *
     * @param productBranch
     */
    private void checkDeficiencyAfterUpdate(ProductBranch productBranch) {
        boolean check = productBranch.checkForDeficiency();
        if (check) {
            alertForDeficiency(productBranch);
            int amount = productBranch.getIdealQuantity() - productBranch.getTotalAmount();
            productToAmount.put(productBranch.getCode(), amount);
        }

    }

    public HashMap<Integer, List<SpecificProduct>> getFlawsProducts() throws SQLException {
        HashMap<Integer, List<SpecificProduct>> allFlawsProducts = new HashMap<>();

        for (ProductBranch productBranch : allProductBranches.values()) {
            List<SpecificProduct> flawProducts = productBranch.getAllFlaws();
            allFlawsProducts.put(productBranch.getCode(), flawProducts);
        }
        return allFlawsProducts;
    }

    public HashMap<Integer, List<Discount>> getDiscountsHistory() {
        HashMap<Integer, List<Discount>> allDiscounts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            List<Discount> discounts = productBranch.getDiscountsHistory();
            allDiscounts.put(productBranch.getCode(), discounts);
        }
        return allDiscounts;
    }

    public void updateMinQuantityForProductBranch(int code, int newMinQuantity) {
        ProductBranch product = loadProductBranch(code);
        product.setMinQuantity(newMinQuantity);

    }

    private void makeDeficiencyReservation() throws SQLException {
        ReservationController reservationController = ReservationController.getInstance();
//        reservationController.makeDeficiencyReservation(productToAmount, this.branchId);
    }

    // Dealing with periodic Reservation
    // =============================================================================================

    /***
     * function to check if there is more than 24 hours diffrence between the
     * delivery day.
     * return true if so
     * else return false
     *
     * @return
     */
    private boolean checkTime(PeriodicReservation periodicReservations) {
        // TODO:Change type of day
        // ProductStatus.Day day = periodicReservations.getDay();
        ProductStatus.Day day = ProductStatus.Day.Sunday; // change it
        LocalDate currentDate = LocalDate.now();
        int deliveryDay = 0;
        if (day == ProductStatus.Day.Sunday)
            deliveryDay = 1;
        else if (day == day.Monday)
            deliveryDay = 2;
        else if (day == ProductStatus.Day.Tuesday)
            deliveryDay = 3;
        else if (day == ProductStatus.Day.Wednesday)
            deliveryDay = 4;
        else if (day == ProductStatus.Day.Thursday)
            deliveryDay = 5;
        else if (day == ProductStatus.Day.Friday)
            deliveryDay = 6;
        else if (day == ProductStatus.Day.Saturday)
            deliveryDay = 7;

        int currentDay = 0;
        if (currentDate.getDayOfWeek() == DayOfWeek.SUNDAY)
            currentDay = 1;
        else if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY)
            currentDay = 2;
        else if (currentDate.getDayOfWeek() == DayOfWeek.TUESDAY)
            currentDay = 3;
        else if (currentDate.getDayOfWeek() == DayOfWeek.WEDNESDAY)
            currentDay = 4;
        else if (currentDate.getDayOfWeek() == DayOfWeek.THURSDAY)
            currentDay = 5;
        else if (currentDate.getDayOfWeek() == DayOfWeek.FRIDAY)
            currentDay = 6;
        else if (currentDate.getDayOfWeek() == DayOfWeek.SATURDAY)
            currentDay = 7;
        int max = Math.max(deliveryDay, currentDay);
        int min = Math.min(deliveryDay, currentDay);

        if (max - min <= 1)
            return false;
        else
            return true;

    }

    // Dealing with discount - both on products and categories
    // ==========================================================================================================

    /***
     * set discount on products and return a list of all products that the discount
     * is applying on them
     * 
     * @param productsToDiscount
     * @param discount
     * @return
     * @throws Exception
     */
    public List<ProductBranch> setDiscountOnProducts(List<ProductBranch> productsToDiscount, Discount discount,
            DiscountDTO discountDTO)
            throws Exception {
        HashMap<ProductBranch, DiscountDTO> changeDiscount = new HashMap<>();
        List<ProductBranch> productToDiscount = new ArrayList<>();

        for (ProductBranch productBranch : productsToDiscount) {
            ProductBranch productBranch1 = loadProductBranch(productBranch.getCode());
//            if (!allProductBranches.containsKey(productBranch.getCode())) {
//                throw new Exception("this product not fount on this branch");
//            }
//            boolean ans = productBranch.applyDiscount(discount);
            boolean ans = productBranch1.applyDiscount(discount);
            if (ans) {
                productToDiscount.add(productBranch);
//                ProductBranchDiscountDTO productBranchDiscountDTO = new ProductBranchDiscountDTO(productBranch.getCode(),branchId,discountDTO);
//                ProductBranchDiscountsDAO.getInstance().insert(productBranchDiscountDTO);
            }

        }
        return productToDiscount;
    }

//    public void setDiscountOnCategories(List<Category> categoriesToDiscount, Discount discount) throws Exception {
//        List<Category> allSubCategories = categoryController.getListAllSubCategories(categoriesToDiscount);
//        List<ProductBranch> productsFromCategory = getProductsByCategories(allSubCategories);
//        setDiscountOnProducts(productsFromCategory, discount,discoun);
//    }

    public List<ProductBranch> getProductsByCategories(List<Category> allSubCategories)
            throws SQLException, InventoryException {
        List<ProductBranch> result = new ArrayList<>();
        loadAllProductBranch();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.existInCategories(allSubCategories);
            if (check)
                result.add(productBranch);
        }
        return result;
    }

    public List<SpecificProduct> getShelfProductsByProductCode(int productCode) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("this product doesnt exist in the branch");
        return productBranch.getOnShelfProduct();
    }

    /***
     * iterate on all productBranch and gets for each product branch a list of it
     * flaws specific product
     *
     * @return
     */
    public HashMap<Integer, List<SpecificProduct>> getBranchFlaws() throws SQLException {
        HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
        loadAllProductBranch();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            List<SpecificProduct> allFlaws = productBranch.getAllFlaws();
            result.put(code, allFlaws);
        }
        return result;
    }

    public HashMap<Integer, Integer> getBranchExpiredAmount() {
        HashMap<Integer, Integer> result = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            int amount = productBranch.getExpiredAmount();
            result.put(code, amount);
        }
        return result;
    }

    public HashMap<Integer, String> getAllDeficiencyProducts() {
        HashMap<Integer, String> allDeficiencyProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.checkForDeficiency();
            if (check) {
                allDeficiencyProducts.put(productBranch.getCode(), productBranch.getName());
            }
        }
        return allDeficiencyProducts;
    }

    public HashMap<Integer, Integer> getAllTotalAmountForDeficiencyProducts() {
        HashMap<Integer, Integer> allTotalAmountForDeficiencyProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.checkForDeficiency();
            if (check) {
                allTotalAmountForDeficiencyProducts.put(productBranch.getCode(), productBranch.getTotalAmount());
            }
        }
        return allTotalAmountForDeficiencyProducts;
    }

    public HashMap<Integer, Integer> getAllMinQuantityForDeficiencyProducts() {
        HashMap<Integer, Integer> allMinQuantityForDeficiencyProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.checkForDeficiency();
            if (check) {
                allMinQuantityForDeficiencyProducts.put(productBranch.getCode(), productBranch.getMinQuantity());
            }
        }
        return allMinQuantityForDeficiencyProducts;
    }

    public HashMap<Integer, Integer> getAllIdealQuantityForDeficiencyProducts() {
        HashMap<Integer, Integer> allIdealQuantityForDeficiencyProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.checkForDeficiency();
            if (check) {
                allIdealQuantityForDeficiencyProducts.put(productBranch.getCode(), productBranch.getIdealQuantity());
            }
        }
        return allIdealQuantityForDeficiencyProducts;
    }

    public HashMap<Integer, String> getIdsToName() {
        HashMap<Integer, String> idsToName = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            String name = productBranch.getName();
            idsToName.put(code, name);
        }
        return idsToName;
    }

    public HashMap<Integer, Integer> getIdsTOShelfAmount() {
        HashMap<Integer, Integer> idsToShelfAmount = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            idsToShelfAmount.put(code, shelfAmount);
        }
        return idsToShelfAmount;
    }

    public HashMap<Integer, Integer> getIdsTOStorageAmount() {
        HashMap<Integer, Integer> idsToStorageAmount = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();
            idsToStorageAmount.put(code, storageAmount);
        }
        return idsToStorageAmount;
    }

    public HashMap<Integer, HashMap<Integer, LocalDate>> getBranchesExpired() throws SQLException {
        HashMap<Integer, HashMap<Integer, LocalDate>> result = new HashMap<>();
        HashMap<Integer, List<SpecificProduct>> productSpecificsExpired = getExpiredProducts();
        for (Integer productCode : productSpecificsExpired.keySet()) {
            HashMap<Integer, LocalDate> expired = new HashMap<>();
            for (SpecificProduct specificProduct : productSpecificsExpired.get(productCode)) {
                int specificId = specificProduct.getSpecificId();
                LocalDate expiredDate = specificProduct.getExpiredDate();
                expired.put(specificId, expiredDate);
            }
            result.put(productCode, expired);
        }

        return result;
    }

    public Set<Integer> expiredProductBranchCode(List<SpecificProductDTO> expiredProducts) {
        Set<Integer> expiredProductBranchCodes = new HashSet();
        for (SpecificProductDTO specificProductDTO : expiredProducts) {
            Integer code = specificProductDTO.getGeneralId();
            expiredProductBranchCodes.add(code);
        }
        return expiredProductBranchCodes;
    }

    public HashMap<Integer, HashMap<Integer, String>> getBranchFlawsIdsToDescription() throws SQLException {
        HashMap<Integer, HashMap<Integer, String>> result = new HashMap<>();
        HashMap<Integer, List<SpecificProduct>> branchFlows = getBranchFlaws();
        for (Integer productCode : branchFlows.keySet()) {
            HashMap<Integer, String> flaws = new HashMap<>();
            for (SpecificProduct specificProduct : branchFlows.get(productCode)) {
                int specificId = specificProduct.getSpecificId();
                String description = specificProduct.getFlawDescription();
                flaws.put(specificId, description);
            }
            result.put(productCode, flaws);
        }
        return result;
    }

    public HashMap<Integer, String> getCodeToCategory() throws SQLException, Exception {
        HashMap<Integer, String> result = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            Category cat = categoryController.getCategoryById(productBranch.getCategoryId());
            String CategoryName = cat.getName();
            result.put(code, CategoryName);
        }
        return result;
    }

    public HashMap<Integer, String> getIdsToNameByCategories(List<Category> categoryList)
            throws SQLException, InventoryException {
        HashMap<Integer, String> idsToName = new HashMap<>();
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);

        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            String name = productBranch.getName();
            idsToName.put(code, name);
        }
        return idsToName;
    }

    public HashMap<Integer, String> getIdsToNameForAllegories() throws SQLException {
        HashMap<Integer, String> idsToName = new HashMap<>();
        List<ProductBranch> productsForAllegories = new ArrayList<>();
        for (Integer productBrancheCode : getAllProductBranches().keySet()) {
            productsForAllegories.add(getAllProductBranches().get(productBrancheCode));
        }
        for (ProductBranch productBranch : productsForAllegories) {
            int code = productBranch.getCode();
            String name = productBranch.getName();
            idsToName.put(code, name);
        }
        return idsToName;
    }

    public HashMap<Integer, Integer> getIdsTOShelfAmountByCategories(List<Category> categoryList)
            throws SQLException, Exception {
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);
        HashMap<Integer, Integer> idsToShelfAmount = new HashMap<>();
        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            idsToShelfAmount.put(code, shelfAmount);
        }
        return idsToShelfAmount;
    }

    public HashMap<Integer, Integer> getIdsTOStorageAmountByCategories(List<Category> categoryList)
            throws SQLException, Exception {
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);
        HashMap<Integer, Integer> idsToStorageAmount = new HashMap<>();
        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();
            idsToStorageAmount.put(code, storageAmount);
        }
        return idsToStorageAmount;

    }

    public List<ProductBranch> getProductsByCode(List<Integer> lst) throws SQLException {
        List<ProductBranch> result = new ArrayList<>();
        loadAllProductBranch();
        for (ProductBranch productBranch : allProductBranches.values()) {
            if (lst.contains(productBranch.getCode()))
                result.add(productBranch);
        }
        return result;
    }

    public ProductBranch getProductByCode(int productCode) throws Exception {
        ProductBranch productBranch = loadProductBranch(productCode);
        if (productBranch == null)
            throw new Exception("this product doesn't exist in the branch");
        return productBranch;
    }

    public HashMap<Integer, Integer> getProductsToAmountById(int id) {
        HashMap<Integer, Integer> productsToAmount = new HashMap<>();

        return productsToAmount;
    }

    public PeriodicReservation getPeriodicReservation(int supplierId) {
        return supplierToPeriodicReservations.get(supplierId);
    }

    public BranchDTO getBranchDTO() {
        return this.branchDTO;
    }

    public List<Integer> getCodeByProducts(List<ProductBranch> productsToDiscount) {
        List<Integer> res = new ArrayList<>();
        for (ProductBranch productBranch : productsToDiscount) {
            res.add(productBranch.getCode());
        }
        return res;
    }

    public HashMap<Integer, ProductBranch> getAllDeficiencyProductsBranch() throws SQLException {
        HashMap<Integer, ProductBranch> allDeficiencyProducts = new HashMap<>();
        if(allProductBranches.size() == 0)
            loadAllProductBranch();
        for (ProductBranch productBranch : allProductBranches.values()) {
            boolean check = productBranch.checkForDeficiency();
            if (check) {
                allDeficiencyProducts.put(productBranch.getCode(), productBranch);
            }
        }
        return allDeficiencyProducts;
    }

    public HashMap<Integer, ProductBranch> getDeficiencyProductBranches(
            Map<Integer, ProductBranchDTO> idToProductBranch) throws SQLException {
        HashMap<Integer, ProductBranch> deficiencyProductBranches = new HashMap<>();
        loadAllProductBranch();
        for (Integer productCode : idToProductBranch.keySet()) {
            ProductBranchDTO productBranchDTO = idToProductBranch.get(productCode);
            ProductBranch productBranch = allProductBranches.get(productBranchDTO.getProductDTO().getId());
            deficiencyProductBranches.put(productCode, productBranch);
        }
        return deficiencyProductBranches;
    }

    public HashMap<Integer, HashMap<Integer, LocalDate>> getBranchesExpiredBySpecificProductList(
            List<SpecificProductDTO> expiredProducts) throws SQLException {
        HashMap<Integer, HashMap<Integer, LocalDate>> result = new HashMap<>();
        Set<Integer> expiredProductBranchCodes = expiredProductBranchCode(expiredProducts);
        loadAllProductBranch();
        for (Integer expiredProductBranchCode : expiredProductBranchCodes) {
            HashMap<Integer, LocalDate> expired = new HashMap<>();
            for (SpecificProductDTO specificProductDTO : expiredProducts) {
                if (specificProductDTO.getGeneralId() == expiredProductBranchCode) {
                    int specificId = specificProductDTO.getSpecificId();
                    LocalDate expiredDate = specificProductDTO.getExpDate();
                    expired.put(specificId, expiredDate);
                }
            }
            result.put(expiredProductBranchCode, expired);
        }

        return result;
    }

}