package BusinessLayer.InveontorySuppliers;

import BusinessLayer.Inventory.*;
import BusinessLayer.Suppliers.ReservationController;
import BusinessLayer.Suppliers.SupplierController;
import DataAccessLayer.DAOs.DiscountDAO;
import DataAccessLayer.DAOs.PeriodicReservationDAO;
import DataAccessLayer.DAOs.ProductsDAO;
import DataAccessLayer.DTOs.*;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Branch {
    private int branchId;
    private String branchName;
    private HashMap<Integer, ProductBranch> allProductBranches;// maps between productBranch generalId to its object
//    private List<PeriodicReservation> periodicReservations;

    private HashMap<Integer,PeriodicReservation> supplierToPeriodicReservations;
    private CategoryController categoryController;
    private HashMap<Integer,Integer> productToAmount; // maps between product to amount for deficiency quantity
    private int minAmountForDeficiencyReservation;
    private BranchDTO branchDTO;


//    public Branch(int branchId, String branchName,int minAmount) {
//        this.branchId = branchId;
//        this.branchName = branchName;
//        this.allProductBranches = new HashMap<>();
//        this.periodicReservations = new ArrayList<>();
//        this.categoryController = CategoryController.getInstance();
//        this.minAmountForDeficiencyReservation = minAmount;
//    }

    public Branch(BranchDTO branchDTO) {
        this.branchId = branchDTO.getId();
        this.branchName = branchDTO.getName();
        this.allProductBranches = new HashMap<>();
        HashMap<Integer,PeriodicReservation> res = new HashMap<>();
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

    public HashMap<Integer, ProductBranch> getAllProductBranches() {
        return allProductBranches;
    }

public void addNewProductBranch(ProductBranchDTO productBranchDTO) throws SQLException {
    ProductBranch newProduct = new ProductBranch(productBranchDTO)  ;
    allProductBranches.put(newProduct.getCode(), newProduct);
}
    public void receiveSupply(int generalId) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // SpecificProduct sp1 = new SpecificProduct(generalId,20,tomorrow);
        // SpecificProduct sp2 = new SpecificProduct(generalId,20,tomorrow);
        // SpecificProduct sp3 = new SpecificProduct(generalId,20,yesterday);
        // SpecificProduct sp4 = new SpecificProduct(generalId,20,yesterday);
//        ProductBranch productBranch = allProductBranches.get(generalId);
//        productBranch.receiveSupply(2, 15, tomorrow);
//        productBranch.receiveSupply(2, 15, yesterday);

    }

    public HashMap<ProductBranch,List<SpecificProduct>> receiveReservation(Reservation reservation) throws SQLException {
        HashMap<ProductBranch,List<SpecificProduct>> toDao = new HashMap<>();
        List<ReceiptItem> allItems = reservation.getReceipt();
        for (ReceiptItem ri : allItems) {
            int amount = ri.getAmount();
            double buyPrice = ri.getPricePerUnitAfterDiscount();
            Product product = ri.getProduct();
            LocalDate expiredDate = ri.getExpiredDate();
            int id = product.getId();
            if (!allProductBranches.containsKey(id)) {
                int idealQuantity = 100;
                int minQuantity = 50;
                ProductsDAO productsDAO = ProductsDAO.getInstance();
                DiscountDAO discountDAO = DiscountDAO.getInstance();
                ProductBranchDTO productBranchDTO = new ProductBranchDTO(productsDAO.getById(id),discountDAO.getById(-1),reservation.getDestination(),buyPrice,minQuantity,idealQuantity,new HashMap<Integer, SpecificProductDTO>() );
                ProductBranch newProductBranch = new ProductBranch(productBranchDTO);
                addNewProductBranch(productBranchDTO);
            }
            ProductBranch productBranch = allProductBranches.get(id);
            List<SpecificProduct> addedSpecifics = productBranch.receiveSupply(amount, buyPrice, expiredDate,reservation.getDestination());
           toDao.put(productBranch,addedSpecifics);
        }
        return toDao;
    }

    public SpecificProduct sellProduct(int code, int specificId) throws Exception {
        ProductBranch productBranch = allProductBranches.get(code);
        if (productBranch == null)
            throw new Exception("this product doesn't exist");
        SpecificProduct sp =productBranch.sellProduct(specificId);
        checkDeficiencyAfterUpdate(productBranch);
        CheckForDeficiencyReservation();
        return sp;
    }

    public void CheckForDeficiencyReservation() throws SQLException {
        boolean overCapacity = getTotalDeficiencyAmount() > minAmountForDeficiencyReservation;
        if ( overCapacity) {

            makeDeficiencyReservation();
        }
    }

    private int getTotalDeficiencyAmount(){

        int result = 0;
        for(Integer amount :productToAmount.values()){
            result += amount;
        }
        return result;
    }

    private void alertForDeficiency(ProductBranch productBranch) {
        System.out.println("Product " + productBranch.getName() + " is below the mini,um Quantity");
    }

    public HashMap<Integer, List<SpecificProduct>> getExpiredProducts() throws SQLException {
        HashMap<Integer, List<SpecificProduct>> allExpiredProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            List<SpecificProduct> expiredProducts = productBranch.getAllExpired();
            allExpiredProducts.put(productBranch.getCode(), expiredProducts);
            checkDeficiencyAfterUpdate(productBranch);
        }
        CheckForDeficiencyReservation();
        return allExpiredProducts;
    }

    public ProductBranch reportFlawProduct(int productCode, int specificId, String description) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
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
        if(check){
            alertForDeficiency(productBranch);
            int amount = productBranch.getIdealQuantity() -
                    productBranch.getTotalAmount();
            productToAmount.put(productBranch.getCode(),amount);
        }

    }

    public HashMap<Integer, List<SpecificProduct>> getFlawsProducts() {
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
        ProductBranch product = allProductBranches.get(code);
        product.setMinQuantity(newMinQuantity);

    }

    private void makeDeficiencyReservation() throws SQLException {
        ReservationController reservationController = ReservationController.getInstance();
        reservationController.makeDeficiencyReservation(productToAmount,this.branchId);
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
    private boolean checkTime(PeriodicReservation periodicReservations ){
        ProductStatus.Day day = periodicReservations.getDay();
        LocalDate currentDate = LocalDate.now();
        int deliveryDay = 0;
        if (day == ProductStatus.Day.Sunday )
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

    /**
     * add new empty periodic reservation
     * @param supplierId
     * @param day
     * @return
     */
    public BranchDTO addNewPeriodicReservation(int supplierId, ProductStatus.Day day){
        SupplierController supplierController = SupplierController.getInstance();
        List<PeriodicReservationItemDTO> lst = new ArrayList<>();
        PeriodicReservationDTO periodicReservationDTO = new PeriodicReservationDTO(supplierId,branchId,day,lst);
        branchDTO.addNewPeriodicReservation(periodicReservationDTO);
        PeriodicReservation periodic = supplierController.addNewPeriodicReservation(periodicReservationDTO);
        supplierToPeriodicReservations.put(supplierId,periodic);
        return branchDTO;
    }

    /**
     * add new Periodic Item to the PeriodicDTO
     * @param supplierId
     * @param productCode
     * @param amount
     * @throws Exception
     */
    public boolean addProductToPeriodicReservation(int supplierId,int productCode, int amount) throws Exception {
        boolean res = false;
//        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
//        PeriodicReservationDTO periodicReservationDTO = periodicReservationDAO.getById(supplierId,branchId);
//        PeriodicReservationItemDTO periodicReservationItemDTO = new PeriodicReservationItemDTO(supplierId,branchId,productCode,amount);
        PeriodicReservation pr = supplierToPeriodicReservations.get(supplierId);
        if (checkTime(pr)) {
            ProductBranch productBranch = allProductBranches.get(productCode);
            if (productBranch == null)
                throw new Exception("product doesn't exist");
            int minQuantity = productBranch.getMinQuantity();
            int totalQuantity = productBranch.getTotalAmount();
//            periodicReservationDTO.addProductAndAmount(periodicReservationItemDTO);
           res = supplierToPeriodicReservations.get(supplierId).addNewProduct(productCode, amount, minQuantity, totalQuantity);

        } else {
            throw new Exception("You cannot edit periodic reservation in less then 24 hours");
        }
        return res;
    }

    public boolean changeAmountPeriodicReservation(int supplierId,int productCode, int amount) throws Exception {
       boolean res = false;
//        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
//        PeriodicReservationDTO periodicReservationDTO = periodicReservationDAO.getById(supplierId,branchId);
        PeriodicReservation pr = supplierToPeriodicReservations.get(supplierId);
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();
         res = supplierToPeriodicReservations.get(supplierId).changeAmount(productCode, amount, minQuantity, totalQuantity);
        if(!res)
            throw new Exception("the total quantity will be less then the min quantity");
        else
            return true;
    }

    public boolean addAmountPeriodicReservation(int supplierId,int productCode, int amount) throws Exception {
        boolean res = false;
//        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
//        PeriodicReservationDTO periodicReservationDTO = periodicReservationDAO.getById(supplierId,branchId);
        PeriodicReservation pr = supplierToPeriodicReservations.get(supplierId);
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();
         res = supplierToPeriodicReservations.get(supplierId).addAmount(productCode, amount, minQuantity, totalQuantity);
        if(!res)
            throw new Exception("the total quantity will be less then the min quantity");
        else
            return true;
    }

    public boolean reduceAmountPeriodicReservation(int supplierId,int productCode, int amount) throws Exception {
        boolean res = false;
//        PeriodicReservationDAO periodicReservationDAO = PeriodicReservationDAO.getInstance();
//        PeriodicReservationDTO periodicReservationDTO = periodicReservationDAO.getById(supplierId,branchId);
        PeriodicReservation pr = supplierToPeriodicReservations.get(supplierId);
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();
         res = supplierToPeriodicReservations.get(supplierId).reduceAmount(productCode, amount, minQuantity, totalQuantity);
        if(!res)
            throw new Exception("the total quantity will be less then the min quantity");
        else
            return true;
    }

    // Dealing with discount - both on products and categories
    // ==========================================================================================================

    /***
     * set discount on products and return a list of all products that the discount is applying on them
     * @param productsToDiscount
     * @param discount
     * @return
     * @throws Exception
     */
    public List<ProductBranch> setDiscountOnProducts(List<ProductBranch> productsToDiscount, Discount discount) throws Exception {
      HashMap<ProductBranch,DiscountDTO> changeDiscount = new HashMap<>();
      List<ProductBranch> productToDiscount = new ArrayList<>();

        for (ProductBranch productBranch : productsToDiscount) {
            if (!allProductBranches.containsKey(productBranch.getCode())) {
                throw new Exception("this product not fount on this branch");
            }
            boolean ans = productBranch.applyDiscount(discount);
            if(ans){
                productToDiscount.add(productBranch);
            }
        }
        return productToDiscount;
    }

    public void setDiscountOnCategories(List<Category> categoriesToDiscount, Discount discount) throws Exception {
        List<Category> allSubCategories = categoryController.getListAllSubCategories(categoriesToDiscount);
        List<ProductBranch> productsFromCategory = getProductsByCategories(allSubCategories);
        setDiscountOnProducts(productsFromCategory, discount);
    }

    public List<ProductBranch> getProductsByCategories(List<Category> allSubCategories) {
        List<ProductBranch> result = new ArrayList<>();
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
    public HashMap<Integer, List<SpecificProduct>> getBranchFlaws() {
        HashMap<Integer, List<SpecificProduct>> result = new HashMap<>();
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

    public HashMap<Integer, HashMap<Integer, String>> getBranchFlawsIdsToDescription() {
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

    public HashMap<Integer, String> getCodeToCategory() {
        HashMap<Integer, String> result = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            int code = productBranch.getCode();
            Category cat = categoryController.getCategoryById(productBranch.getCategoryId());
            String CategoryName = cat.getName();
            result.put(code, CategoryName);
        }
        return result;
    }

    public HashMap<Integer, String> getIdsToNameByCategories(List<Category> categoryList) {
        HashMap<Integer, String> idsToName = new HashMap<>();
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);
        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            String name = productBranch.getName();
            idsToName.put(code, name);
        }
        return idsToName;
    }

    public HashMap<Integer, Integer> getIdsTOShelfAmountByCategories(List<Category> categoryList) {
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);
        HashMap<Integer, Integer> idsToShelfAmount = new HashMap<>();
        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            int shelfAmount = productBranch.getOnShelfProduct().size();
            idsToShelfAmount.put(code, shelfAmount);
        }
        return idsToShelfAmount;
    }

    public HashMap<Integer, Integer> getIdsTOStorageAmountByCategories(List<Category> categoryList) {
        List<ProductBranch> productsByCategories = getProductsByCategories(categoryList);
        HashMap<Integer, Integer> idsToStorageAmount = new HashMap<>();
        for (ProductBranch productBranch : productsByCategories) {
            int code = productBranch.getCode();
            int storageAmount = productBranch.getTotalAmount() - productBranch.getOnShelfProduct().size();
            idsToStorageAmount.put(code, storageAmount);
        }
        return idsToStorageAmount;

    }

    public List<ProductBranch> getProductsByCode(List<Integer> lst) {
        List<ProductBranch> result = new ArrayList<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            if (lst.contains(productBranch.getCode()))
                result.add(productBranch);
        }
        return result;
    }

    public ProductBranch getProductByCode(int productCode) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("this product doesn't exist in the branch");
        return productBranch;
    }

    public HashMap<Integer, Integer> getProductsToAmountById(int id) {
        HashMap<Integer,Integer> productsToAmount = new HashMap<>();


        return productsToAmount;
    }

    public PeriodicReservation getPeriodicReservation(int supplierId) {
        return supplierToPeriodicReservations.get(supplierId);
    }

    public BranchDTO getBranchDTO() {
        return this.branchDTO;
    }
}