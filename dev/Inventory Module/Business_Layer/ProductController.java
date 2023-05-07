package Business_Layer;
import java.util.*;
import java.time.LocalDate;


public class ProductController {

    private List<GeneralProduct> allGeneralProducts; // list of all general products
    private HashMap<GeneralProduct, Integer> allExpiredProducts; // map with a product and the amount of expired
    private HashMap<GeneralProduct, Integer> allFlawProducts; // map with a product and the amount
    private List<Supply> allSupply; // store all the supply that we received
    private List<Supply> allRelevantSupply; // store all the supply that relevant
    private CategoryController1 categoryController1; // controller for categories
    private HashMap<GeneralProduct, List<Discount1>> productDiscountHistory; // map of product and its discount history


    public ProductController() {

        this.allGeneralProducts = new ArrayList<>();
        this.allExpiredProducts = new HashMap<>();
        this.allFlawProducts = new HashMap<>();
        this.allSupply = new ArrayList<>();
        this.allRelevantSupply = new ArrayList<>();
        this.categoryController1 = CategoryController1.getInstance();
        this.productDiscountHistory = new HashMap<>();
    }
    public List<GeneralProduct> getAllGeneralProducts() {
        return allGeneralProducts;
    }

    public HashMap<GeneralProduct, Integer> getAllExpiredProducts() {
        return allExpiredProducts;
    }

    public HashMap<GeneralProduct, Integer> getAllFlawProducts() {
        return allFlawProducts;
    }

    public List<Supply> getAllSupply() {
        return allSupply;
    }

    public HashMap<GeneralProduct, List<Discount1>> getProductDiscountHistory() {
        return productDiscountHistory;
    }

    /**
     * return general product by code, null if not exit
     *
     * @param code
     * @return
     */
    public GeneralProduct getGeneralProductByCode(int code) {
        // Throw an exception if the code is negative
        if (code < 0)
            throw new IllegalArgumentException("code cannot be negative");

        GeneralProduct general = null; // Initialize a variable to hold the GeneralProduct object that matches the provided code

        // Loop through all GeneralProduct objects in the list of allGeneralProducts
        for (GeneralProduct gp : allGeneralProducts) {
            // If the code of the current GeneralProduct object matches the provided code, set general to that object and break out of the loop
            if (gp.getCode() == code) {
                general = gp;
                break;
            }
        }

        return general; // Return the GeneralProduct object that matches the provided code, or null if no matching object was found
    }


    /**
     * return supply's products by code and id - supply code- null if not exist;
     *
     * @param code
     * @return
     */
    public Supply getSupplyByCodeId(int code, int id) {
        Supply result = null;
        boolean check = false;
        GeneralProduct gp = getGeneralProductByCode(code);
        for (Supply sp : gp.getProductSupply()) {
            check = supplyContainsId(id, sp);
            if (check) {
                result = sp;
                break;
            }
        }
        return result;
    }

    /**
     * @param id
     * @param sp
     * @return return true if the id is in a specific supply, else false
     */
    private Boolean supplyContainsId(int id, Supply sp) {
        boolean res = false;
        if (id > sp.getFirstId() - 1 && id < sp.getLastId() + 1)
            res = true;

        return res;
    }

    /**
     * Retrieves the Supply object that corresponds to the provided ID and GeneralProduct object.
     *
     * @param id The ID of the Supply object to retrieve.
     * @param gp The GeneralProduct object that corresponds to the Supply object.
     * @return The Supply object that corresponds to the provided ID and GeneralProduct object, or null if no matching Supply object was found.
     */
    private Supply getSupplyFromGeneralById(int id, GeneralProduct gp) {
        Supply supply = null; // Initialize a variable to hold the Supply object that corresponds to the provided ID and GeneralProduct

        // Loop through all Supply objects in the list of allSupply
        for (Supply sp : getAllSupply()) {
            // If the current Supply object contains the provided ID and corresponds to the provided GeneralProduct, set supply to that object and break out of the loop
            if (supplyContainsId(id, sp)) {
                supply = sp;
                break;
            }
        }

        return supply; // Return the Supply object that corresponds to the provided ID and GeneralProduct, or null if no matching Supply object was found
    }

        /**
         * remove a product from supply - it removes both from supply and generalProduct
         *
         * @param code- represent supply code
         */
        private boolean removeFromSupply(int code, int id, Enum.Location location) {
                boolean res = false;
                Supply sp = getSupplyByCodeId(code, id);
                GeneralProduct gp = getGeneralProductByCode(code);
                res = gp.removeItem(id, location);
                if (res) {
                        res = sp.setAmount(id, location);
                }
                return (res);
        }

        /**

         Decreases the amount of a product that is available for sale by 1, and updates the product's sell price and supply
         accordingly. If the product is not available for sale, or does not exist, an appropriate message will be printed to
         the console.
         @param code The code of the product to be sold.
         @param id The ID of the supply of the product to be sold.
         */
        public void sellProduct(int code, int id) {
                // Get the GeneralProduct and Supply objects corresponding to the given code and id
                GeneralProduct gp = getGeneralProductByCode(code);
                Supply sp = getSupplyFromGeneralById(id, gp);

                // Check if the Supply object exists and has a positive amount
                if (sp == null) {
                        System.out.println("cannot sell product with amount 0");
                } else {
                        boolean check = checkAvailable(sp);
                        if (!check)
                                System.out.println("this product not available - please choose different product");
                        else {
                                // Find the location of the product within the Supply object
                                Enum.Location location = findProductLocation(id, gp);
                                // Remove the product from the GeneralProduct object and update the Supply object
                                boolean flag = gp.removeItem(id, location);
                                if (!flag)
                                        System.out.println("product not available");
                                else {
                                        sp.setAmount(id, location);
                                        // Record the selling price for the product
                                        double sellPrice = gp.getCurrentPrice();
                                        gp.addSellPrice(id, sellPrice);
                                        if (sp.getAmount() == 0) {
                                                // If the Supply object is now empty, remove it from the relevant lists
                                                allRelevantSupply.remove(sp);
                                        }
                                        // Check if the GeneralProduct object now has a quantity below the minimum quantity threshold
                                        alertForMinimumQuantity(gp);
                                }
                        }
                }
        }


        /**
         * alert for reaching to the minimum quantity
         *
         * @param gp
         */
        public void alertForMinimumQuantity(GeneralProduct gp) {
                if (gp.getMin_quantity() > gp.getTotal_quantity())
                        System.out.println("Warning!!! the +" + gp.getName() + " is below the minimum Quantity !!!!!!!!!!!!!");
        }
    /**
     * @param id
     * @param gp
     * @return the location of a specific product
     */
    private Enum.Location findProductLocation(int id, GeneralProduct gp) {
        if (gp.getOnShelf().contains(id)) {
            return Enum.Location.SHOP;
        } else {
            return Enum.Location.STORAGE;
        }
    }

    public String findProductLocation(int code, int id) {
        GeneralProduct gp = getGeneralProductByCode(code);
        if (gp.getOnShelf().contains(id)) {
            return Enum.Location.SHOP.toString();
        } else {
            return Enum.Location.STORAGE.toString();
        }
    }


    private boolean checkAvailable(Supply sp) {
        return (sp.getAmount() > 0);
    }

    /**
     * report for flow product - remove the product from the supply amount and from the general product amount.
     * print if exist any error
     *
     * @param code
     */
    public void reportFlowProduct(int code, int id, String description) {
        GeneralProduct gp = getGeneralProductByCode(code);
        Supply sp = getSupplyFromGeneralById(id, gp);

        if (sp == null) {
            System.out.println("product doesn't exist - please check product code");
        } else {
            Enum.Location location = findProductLocation(id, gp);

            boolean check = removeFromSupply(code, id, location);
            if (check) {
                if (gp != null) {
                    gp.addFlowProduct(id, description);

                    if (allFlawProducts.containsKey(gp)) {
                        allFlawProducts.put(gp, allFlawProducts.get(gp) + 1);
                    } else
                        allFlawProducts.put(gp, 1);
                }
            } else {
                System.out.println("cannot remove this product - amount is 0!!");
            }
        }

    }

    /**
     * find and insert to the allExpiredProducts all of the products that already expired
     */
    public void findExpiredProducts() {
        LocalDate today = LocalDate.now();
        List<Supply> test = new ArrayList<>();
        for (Supply sp : allRelevantSupply) {

            if (today.isAfter(sp.getExpiredDate())) {
                if (!allExpiredProducts.containsKey(sp)) {
                    GeneralProduct gp = sp.getGeneralProduct();
                    allExpiredProducts.put(gp, sp.getAmount());
                    gp.removeFromShop(sp.getShopAmount());
                    gp.removeFromStorage(sp.getStorageAmount());
                    sp.remove();
                    gp.addExpiredProducts(sp.getIds());
                    test.add(sp);
                }
            }
        }
        if (test.size() > 0) {
            for (Supply sp : test) {
                allRelevantSupply.remove(sp);
            }
        }
    }

    /**
     * @param expiredDate -
     * @return a list of supply with products that will be expired before the given expiredDate
     */
    public List<Supply> getAllFutureExpiredProducts(LocalDate expiredDate) {
        List<Supply> result = new ArrayList<>();
        for (Supply sp : allRelevantSupply) {
            if (sp.getExpiredDate().isBefore(expiredDate)) {
                result.add(sp);
            }
        }
        return result;
    }

    /**
     * @param expiredDate
     * @return HashMap with all general products and their amount of product that will be expired before the given date
     */
    public HashMap<GeneralProduct, Integer> getAllExpiredAndAmount(LocalDate expiredDate) {
        HashMap<GeneralProduct, Integer> result = new HashMap<>();
        List<Supply> lst = getAllFutureExpiredProducts(expiredDate);
        for (Supply sp : lst) {
            GeneralProduct gp = sp.getGeneralProduct();
            if (result.containsKey(gp)) {
                result.put(gp, result.get(gp) + sp.getAmount());
            } else {
                result.put(gp, sp.getAmount());
            }
        }
        return result;
    }


    public void setDiscountOnCategory(List<Category1> categories, LocalDate startDate, LocalDate endDate, double discountPercentage) {
        // Get a list of all sub-categories from the provided list of categories
        List<Category1> allSubCategories = categoryController1.getListAllSubCategories(categories);

        // Create a new Discount object using the provided start date, end date, and discount percentage
        Discount1 discount1 = new Discount1(startDate, endDate, discountPercentage);

        // Loop through all general products
        for (GeneralProduct gp : allGeneralProducts) {
            // Check if the product's category is in the list of sub-categories
            if (allSubCategories.contains(gp.getCategory())) {
                // If the start date is before today, mark the product as being on discount
                if (startDate.isBefore(LocalDate.now().plusDays(1))) {
                    gp.setOnDiscount(true);
                }
                if(gp.getDiscount() == null){
                    gp.setDiscount(discount1);
                }
                // If the product already has a discount, and the new discount is greater, update the discount
                if (gp.getDiscount() != null && gp.getDiscount().getDiscount_percentage() < discount1.getDiscount_percentage())
                    gp.setDiscount(discount1);

                // Add the product and discount to a list of discounted products
                addProductDiscount(gp, discount1);
            }
        }
    }

    private void addProductDiscount(GeneralProduct gp, Discount1 discount1) {

        List<Discount1> lst;
        if (productDiscountHistory.containsKey(gp)) {
            lst = productDiscountHistory.get(gp);
        } else {
            lst = new ArrayList<>();
        }
        lst.add(discount1);
        productDiscountHistory.put(gp, lst);
    }

    /**
     * set a discount on a specific products
     *
     * @param allProducts
     * @param startDate
     * @param endDate
     * @param discountPercentage
     */
    public void setDiscountOnProducts(List<GeneralProduct> allProducts, LocalDate startDate, LocalDate endDate, double discountPercentage) {       // Get a list of all sub-categories from the provided list of categories
        Discount1 discount1 = new Discount1(startDate, endDate, discountPercentage);
        for (GeneralProduct gp : allProducts) {
            if (allGeneralProducts.contains(gp)) {
                if (startDate.isBefore(LocalDate.now().plusDays(1))) {
                    gp.setOnDiscount(true);
                }
                if(gp.getDiscount() == null){
                    gp.setDiscount(discount1);
                }
                // If the product already has a discount, and the new discount is greater, update the discount
                if (gp.getDiscount() != null && gp.getDiscount().getDiscount_percentage() < discount1.getDiscount_percentage())
                    gp.setDiscount(discount1);
                addProductDiscount(gp, discount1);
            }
        }
    }


    /**
     * if the general product was already exist receive it
     *
     * @param code
     * @param price
     * @param amount
     * @param expiredDate
     */
    public void receiveExistSupply(int code, double price, int amount, LocalDate expiredDate) {

        GeneralProduct gp = getGeneralProductByCode(code);
        int index = gp.getCurrentId();
        List<Integer> ids = addIdsList(index, amount);
        int currenId = ids.get(ids.size() - 1) + 1;
        gp.setCurrentIdt(currenId);
        gp.addToStorage(ids);
        Supply sp = new Supply(gp, expiredDate, price, amount, ids);
        gp.addSupply(sp);
        if (sp.getExpiredDate().isBefore(LocalDate.now())) {

        }
        allSupply.add(sp);
        allRelevantSupply.add(sp);
        findExpiredProducts();
    }

    private List<Integer> addIdsList(int index, int amount) {
        List<Integer> result = new ArrayList<>();
        while (amount > 0) {
            result.add(index);
            index++;
            amount--;
        }

        return result;
    }

    /**
     * function for receving new supply - if it is a new general product it will be created else will be added
     *
     * @param code
     * @param name
     * @param price
     * @param amount
     * @param expiredDate
     * @param manufacturer
     * @param minQuantity
     * @param categoryId
     * @param categoryName
     * @param categoryParentId
     */
    public void receiveNewSupply(int code, String name, double price, int amount, LocalDate expiredDate, String manufacturer, int minQuantity, int categoryId, String categoryName, int categoryParentId) {
//                Scanner scanner = new Scanner(System.in);
        Category1 category1;
        if (categoryId != -1) {
            category1 = categoryController1.getCategoryById(categoryId);
            if (category1 == null)
                category1 = new Category1(categoryName);
        } else {
            Category1 parent = categoryController1.getCategoryById(categoryParentId);
            category1 = new Category1(categoryName, parent);
        }
        categoryController1.addNewCategory(category1);
        GeneralProduct gp = new GeneralProduct(name, code, price, manufacturer, minQuantity, category1);
        allGeneralProducts.add(gp);
        receiveExistSupply(code, price, amount, expiredDate);

    }

    /**
     * return a list in size @amount with all the products ids that the worker need to transfer from
     * storage to shop- שso transfer the products
     *
     * @param code
     * @param amount
     * @return
     */
    public List<Integer> transferFromStorageToShop(int code, int amount) {
        GeneralProduct gp = getGeneralProductByCode(code);
        List<Integer> result = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String answer = "y";
        if (amount > gp.getStorage_quantity()) {
            System.out.println("sorry your amount is more then the storage amount");
            System.out.println("do you want to get all the available products? - choose y/n");
            answer = scanner.nextLine();
            if (answer == "y")
                amount = gp.getStorage_quantity();
        }

        if (answer == "y") {
            int index = 0;
            while (amount > 0) {
                Supply sp = gp.getProductSupply().get(index);
                int cut = 0;
                //i need to take amount from list
                int check = amount - sp.getAmount();
                if (check < 0) {
                    cut = amount;
                    amount = 0;
                    List<Integer> sublist = sp.getIds().subList(0, cut); // Get the first cut elements from the list
                    result.addAll(sublist);
                    sp.setIds(new ArrayList<>(sp.getIds().subList(cut, sp.getIds().size() - 1)));

                    sp.transferFromStorageToShop(cut);
                    List<Integer> newList = new ArrayList<>(sp.getIds());
                    gp.transferFromStorageToShop(cut, sublist);
                }
                // i need extra supply
                else {
                    cut = sp.getAmount();
                    amount -= cut;
                    result.addAll(sp.getIds());
                    sp.transferFromStorageToShop(cut);
                    gp.transferFromStorageToShop(cut, sp.getIds());
                    allRelevantSupply.remove(sp);
                }

            }

        }

        return result;
    }

    /**
     * returning a product to inventory - might be use after canceling of a buy
     *
     * @param code
     * @param id
     */
    public void returnProduct(int code, int id) {
        GeneralProduct gp = getGeneralProductByCode(code);
        Supply sp = getSupplyFromGeneralById(id, gp);
        gp.addNewQuantityToShelf(1);
        gp.addToShelf(id);
        sp.addId(id);
        sp.addNewToShop(1);


    }

    /**
     * Returns a list of all GeneralProduct objects that belong to any of the given categories or their subcategories.
     *
     * @param categories a list of Category objects to search for GeneralProduct objects in
     * @return a list of GeneralProduct objects belonging to the given categories or their subcategories
     */
    public List<GeneralProduct> getAllProductByCategories(List<Category1> categories) {
        List<GeneralProduct> result = new ArrayList<>();
        List<Category1> allSubCategories = new ArrayList<>();
        // Loop through each category and add all subcategories to a new list, including the original category
        for (Category1 category1 : categories) {
            List<Category1> subCategory1 = categoryController1.getAllSubCategories(category1);
            subCategory1.add(category1);
            allSubCategories.addAll(subCategory1);
        }
        // Loop through each GeneralProduct and add it to the result list if it belongs to any of the specified categories or their subcategories
        for (GeneralProduct gp : allGeneralProducts) {
            boolean flag = allSubCategories.contains(gp.getCategory());
            if (flag) {
                result.add(gp);
            }
        }
        // Remove any duplicate values from the result list
        Set<GeneralProduct> uniqueSet = new HashSet<>(result);
        result.clear();
        result.addAll(uniqueSet);
        return result;
    }

    public String getProductManufacturer(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        return gp.getManufacturer();
    }

    public int getProductAmount(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        return gp.getTotal_quantity();
    }

    public int getProductShelfAmount(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        int shelfAmount = gp.getShop_quantity();
        return shelfAmount;
    }

    public int getProductStorageAmount(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        int storageAmount = gp.getStorage_quantity();
        return storageAmount;
    }

    public List<GeneralProduct> getShortageProducts() {
        List<GeneralProduct> result = new ArrayList<>();
        for (GeneralProduct gp : allGeneralProducts) {
            if (gp.isOnShortage()) {
                result.add(gp);
            }
        }
        return result;
    }

    /**
     * Get a list of GeneralProduct objects based on a list of product codes.
     *
     * @param productsDiscount a list of product codes to search for
     * @return a list of GeneralProduct objects matching the specified product codes
     */
    public List<GeneralProduct> getProductsByCode(List<Integer> productsDiscount) {
        List<GeneralProduct> result = new ArrayList<>();
        for (int code : productsDiscount) {
            GeneralProduct gp = getGeneralProductByCode(code);
            result.add(gp);
        }
        return result;
    }


    /**
     * Adds a new GeneralProduct to the system.
     *
     * @param name           the name of the product
     * @param code           the unique code of the product
     * @param price          the price of the product
     * @param manufacturer   the manufacturer of the product
     * @param min_quantity   the minimum quantity of the product required for the system to function properly
     * @param total_quantity the total quantity of the product in the system
     * @param categoryId     the ID of the category of the product
     * @param categoryName   the name of the category of the product (used only if a new category is created)
     * @param parentCategory the ID of the parent category of the product (used only if a new category is created)
     */
    public void addNewGeneralProduct(String name, int code, double price, String manufacturer, int min_quantity, int total_quantity, int categoryId, String categoryName, int parentCategory) {
        GeneralProduct gp2 = getGeneralProductByCode(code);
        if (gp2 == null) {
            boolean check = categoryController1.ExistCategory(categoryId);
            // if Category already exists
            if (check) {
                Category1 category1 = categoryController1.getCategoryById(categoryId);
                // create new GeneralProduct object
                GeneralProduct gp = new GeneralProduct(name, code, price, manufacturer, min_quantity, category1, total_quantity);
                allGeneralProducts.add(gp);
                System.out.println("Product added successfully");
            }
            // it's a new category
            else {
                Category1 parent = categoryController1.getCategoryById(parentCategory);
                Category1 category1 = new Category1(categoryName, parent);
                categoryController1.addNewCategory(category1);
                GeneralProduct gp = new GeneralProduct(name, code, price, manufacturer, min_quantity, category1, total_quantity);
                allGeneralProducts.add(gp);
                System.out.println("Product added successfully");
            }
        }
    }


    /**
     * Sets the minimum quantity of a product with a given code.
     *
     * @param code        The code of the product to update.
     * @param minQuantity The new minimum quantity for the product.
     */
    public void setProductMinQuantity(int code, int minQuantity) {
        GeneralProduct gp = getGeneralProductByCode(code);
        gp.setMinimumQuantity(minQuantity);
    }

    /**
     * Retrieves the discount history for a given product code.
     *
     * @param code the code of the product to retrieve discount history for
     * @return a List of Discount objects representing the discount history for the given product
     */
    public List<Discount1> getProductDiscountHistory(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        return productDiscountHistory.get(gp);
    }


    /**
     * Gets the sell price of a general product by its code.
     *
     * @param code the code of the general product to get the sell price for
     * @return a HashMap containing the IDs and sell prices of the general product
     */
    public HashMap<Integer, Double> getSellPrice(int code) {
        GeneralProduct gp = getGeneralProductByCode(code);
        return gp.getIdsSellPrice();
    }

    /**
     * Retrieves the buy price of a supply with the given code and id.
     *
     * @param code The code of the general product.
     * @param id   The id of the supply.
     * @return The buy price of the supply.
     */
    public double getBuyPrice(int code, int id) {
        return getSupplyByCodeId(code, id).getBuyPrice();
    }

    /**
     * Adds a new general product to the system.
     *
     * @param generalProduct the general product object to be added
     * @param totalQuantity  the total quantity of the product to be added
     */
    public void addNewGeneralProduct(GeneralProduct generalProduct, int totalQuantity) {
        // Set the total quantity of the general product object
        generalProduct.setTotal_quantity(totalQuantity);
        // Add the general product object to the list of all general products in the system
        allGeneralProducts.add(generalProduct);
    }


}
