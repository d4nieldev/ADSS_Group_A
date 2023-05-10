package BusinessLayer.InveontorySuppliers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BusinessLayer.Inventory.Category;
import BusinessLayer.Inventory.CategoryController;
import BusinessLayer.Inventory.Discount;
import BusinessLayer.Inventory.PeriodicReservation;
import BusinessLayer.Inventory.ProductBranch;
import BusinessLayer.Inventory.SpecificProduct;
import BusinessLayer.enums.Day;

public class Branch {
    private int branchId;
    private String branchName;
    private HashMap<Integer, ProductBranch> allProductBranches;// maps between productBranch generalId to its object
    private PeriodicReservation periodicReservation;
    private CategoryController categoryController;

    public Branch(int branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.allProductBranches = new HashMap<>();
        this.periodicReservation = new PeriodicReservation();
        this.categoryController = CategoryController.getInstance();
    }

    public int getId() {
        return this.branchId;
    }

    public String getName() {
        return this.branchName;
    }

    public void addNewProductBranch(Product product, double price, int idealQuantity, int minQuantity) {
        ProductBranch newProduct = new ProductBranch(product, price, idealQuantity, minQuantity);
        allProductBranches.put(product.getId(), newProduct);
    }

    public void receiveSupply(int generalId) {
        LocalDate tommorow = LocalDate.now().plusDays(1);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        // SpecificProduct sp1 = new SpecificProduct(generalId,20,tommorow);
        // SpecificProduct sp2 = new SpecificProduct(generalId,20,tommorow);
        // SpecificProduct sp3 = new SpecificProduct(generalId,20,yesterday);
        // SpecificProduct sp4 = new SpecificProduct(generalId,20,yesterday);
        ProductBranch productBranch = allProductBranches.get(generalId);
        productBranch.receiveSupply(2, 15, tommorow);
        productBranch.receiveSupply(2, 15, yesterday);

    }

    public void receiveReservation(Reservation reservation) {
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
                addNewProductBranch(product, buyPrice, idealQuantity, minQuantity);
            }
            ProductBranch productBranch = allProductBranches.get(id);
            productBranch.receiveSupply(amount, buyPrice, expiredDate);
        }
    }

    public void sellProduct(int code, int specificId) throws Exception {
        ProductBranch productBranch = allProductBranches.get(code);
        if (productBranch == null)
            throw new Exception("this product doesn't exist");
        productBranch.sellProduct(specificId);
        boolean check = productBranch.checkForDeficiency();
        if (check) {
            alertForDeficiency(productBranch);
            // makeDeficiencyReservation(productBranch);
        }
    }

    private void alertForDeficiency(ProductBranch productBranch) {
        System.out.println("Product " + productBranch.getName() + " is below the mini,um Quantity");
    }

    public HashMap<Integer, List<SpecificProduct>> getExpiredProducts() {
        HashMap<Integer, List<SpecificProduct>> allExpiredProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()) {
            List<SpecificProduct> expiredProducts = productBranch.getAllExpired();
            allExpiredProducts.put(productBranch.getCode(), expiredProducts);
            checkDeficiencyAfterUpdate(productBranch);
        }

        return allExpiredProducts;
    }

    public void reportFlawProduct(int productCode, int specificId, String description) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("this product doesn't exist in the branch");
        productBranch.reportFlawProduct(specificId, description);
        checkDeficiencyAfterUpdate(productBranch);

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
            makeDeficiencyReservation(productBranch);
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

    private void makeDeficiencyReservation(ProductBranch productBranch) {
        // TODO: make reservation for deficiency
        // find the difference to the ideal quantity
        int amount = productBranch.getIdealQuantity() -
                productBranch.getTotalAmount();
        SupplierController.makeDifiecincyReservstion(productBranch.getCode(), amount);
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
    private boolean checkTime() {
        Day day = periodicReservation.getDay();
        LocalDate currentDate = LocalDate.now();
        int deliveryDay = 0;
        if (day == Day.SUNDAY)
            deliveryDay = 1;
        else if (day == Day.MONDAY)
            deliveryDay = 2;
        else if (day == Day.TUESDAY)
            deliveryDay = 3;
        else if (day == Day.WEDNESDAY)
            deliveryDay = 4;
        else if (day == Day.THURSDAY)
            deliveryDay = 5;
        else if (day == Day.FRIDAY)
            deliveryDay = 6;
        else if (day == Day.SATURDAY)
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

    public void addProductToPeriodicReservation(int productCode, int amount) throws Exception {
        if (checkTime()) {
            ProductBranch productBranch = allProductBranches.get(productCode);
            if (productBranch == null)
                throw new Exception("product doesn't exist");
            int minQuantity = productBranch.getMinQuantity();
            int totalQuantity = productBranch.getTotalAmount();
            periodicReservation.addNewProduct(productCode, amount, minQuantity, totalQuantity);
        } else {
            throw new Exception("You cannot edit periodic reservation in less then 24 hours");
        }
    }

    public void changeAmountPeriodicReservation(int productCode, int amount) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();
        periodicReservation.changeAmount(productCode, amount, minQuantity, totalQuantity);
    }

    public void addAmountPeriodicReservation(int productCode, int amount) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();
        periodicReservation.addAmount(productCode, amount, minQuantity, totalQuantity);
    }

    public void reduceAmountPeriodicReservation(int productCode, int amount) throws Exception {
        ProductBranch productBranch = allProductBranches.get(productCode);
        if (productBranch == null)
            throw new Exception("product doesn't exist");
        int minQuantity = productBranch.getMinQuantity();
        int totalQuantity = productBranch.getTotalAmount();

        periodicReservation.reduceAmount(productCode, amount, minQuantity, totalQuantity);
    }

    // Dealing with discount - both on products and categories
    // ==========================================================================================================
    public void setDiscountOnProducts(List<ProductBranch> productsToDiscount, Discount discount) throws Exception {
        for (ProductBranch productBranch : productsToDiscount) {
            if (!allProductBranches.containsKey(productBranch.getCode())) {
                throw new Exception("this product not fount on this branch");
            }
            productBranch.applyDiscount(discount);
        }
    }

    public void setDiscountOnCategories(List<Category> categoriesToDiscount, Discount discount) throws Exception {
        List<Category> allSubCategories = categoryController.getListAllSubCategories(categoriesToDiscount);
        List<ProductBranch> productsFromCategory = getProductsByCategories(allSubCategories);
        setDiscountOnProducts(productsFromCategory, discount);
    }

    private List<ProductBranch> getProductsByCategories(List<Category> allSubCategories) {
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

    public HashMap<Integer, HashMap<Integer, LocalDate>> getBranchesExpired() {
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
            String CategoryName = productBranch.getCategory().getName();
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
}
