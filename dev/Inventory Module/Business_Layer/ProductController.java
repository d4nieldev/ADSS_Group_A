package Business_Layer;

import sun.java2d.loops.GeneralRenderer;

import java.util.*;
import java.time.LocalDate;


public class ProductController {

//        private List<SpecificProduct> allSpecificProducts;
        private List<GeneralProduct> allGeneralProducts;
        private HashMap<GeneralProduct,Integer> allExpiredProducts; //map with a product and the amount
        private HashMap<GeneralProduct,Integer> allFlawProducts; //map with a product and the amount
        private List<Supply> allSupply; //store all the supply that we received
        private List<Supply> allRelevantSupply; //store all the supply that relevant


        public ProductController(){

                this.allGeneralProducts = new ArrayList<>();
                this.allExpiredProducts = new HashMap<>();
                this.allFlawProducts = new HashMap<>();
                this.allSupply = new ArrayList<>();
                this.allRelevantSupply = new ArrayList<>();
        }

//        public List<SpecificProduct> getAllSpecificProducts() {return allSpecificProducts;}

        public List<GeneralProduct> getAllGeneralProducts() {return allGeneralProducts;}

        public HashMap<GeneralProduct,Integer> getAllExpiredProducts() {return allExpiredProducts;}

        public HashMap<GeneralProduct,Integer> getAllFlawProducts() {return allFlawProducts;}

        public List<Supply> getAllSupply() {return allSupply;}

        /**
         * return general product by code, null if not exit
         * @param code
         * @return
         */
        public GeneralProduct getGeneralProductByCode(int code) {

                GeneralProduct general = null;

             for(GeneralProduct gp : allGeneralProducts )
             {
                     if(gp.getCode() == code) {
                             general =  gp;
                             break;
                     }
             }
             return general;
        }

        /**
         * return supply's products by code and id - supply code- null if not exist;
         * @param code
         * @return
         */
        public Supply getSupplyByCodeId(int code,int id) {
               Supply result = null;
               boolean check = false;
               GeneralProduct gp = getGeneralProductByCode(code);
                for (Supply sp : gp.getProductSupply() ){
                        check = supplyContainsId(id , sp);
                        if(check) {
                                result = sp;
                                break;
                        }
                }
                  return  result;
        }

        /**
         *
         * @param id
         * @param sp
         * @return return true if the id is in a specific supply, else false
         */
        private Boolean supplyContainsId(int id,Supply sp)
        {
                boolean res = false;
               if(id > sp.getFirstId() - 1 || id < sp.getLastId() + 1)
                       res = true;

               return res;
        }

        /**
         *
         * @param id
         * @param gp
         * @return supply by id from generalProduct
         */
        private Supply getSupplyFromGeneralById(int id, GeneralProduct gp)
        {
                Supply supply = null;
                for(Supply sp : getAllSupply())
                {
                        if(supplyContainsId(id,sp));
                        {
                                supply = sp;
                                break;
                        }
                }
                return supply;
        }

        /**
         * remove a product from supply - it removes both from supply and generalProduct
         * @param code- represent supply code
         */
        private boolean removeFromSupply(int code,int id,Enum.Location location)
        {
                boolean res = false;
                Supply sp = getSupplyByCodeId(code,id);
                GeneralProduct gp = getGeneralProductByCode(code);
                res = gp.removeItem(id,location);
                if (res) {
                         res = sp.setAmount(id,location);
                }
                return (res);
        }
        /***
         * simulate a sell of a product - print if operation failed
         * @param code
         */
        public void sellProduct(int code, int id)
        {
                GeneralProduct gp = getGeneralProductByCode(code);
                Supply sp = getSupplyFromGeneralById(id,gp);

                 if(sp == null) {
                         System.out.println("cannot sell product with amount 0");
                 }

                 else {
                         boolean check = checkAvailable(sp);
                         if (!check)
                                 System.out.println("this product not available - please choose different product");
                         else {
                                 Enum.Location location = findProductLocation(id,gp);
                                 boolean flag = gp.removeItem(id,location);
                                 if(!flag)
                                         System.out.println("product not available");
                                 else {
                                         sp.setAmount(id,location);
                                         double sellPrice = gp.getCurrentPrice();
                                         gp.addSellPrice(id,sellPrice);
                                         if (sp.getAmount() == 0) {
                                                 allRelevantSupply.remove(sp);
                                         }
                                         AlertForMinimumQuantity(gp);
                                 }
                         }
                 }
        }

        /**
         * alert for reaching to the minimum quantity
         * @param gp
         */
        private void AlertForMinimumQuantity(GeneralProduct gp) {
                if(gp.getMin_quantity() > gp.getTotal_quantity())
                        System.out.println("Warning!!! the +" + gp.getName() +" is below the minimum Quantity !!!!!!!!!!!!!");

        }


        /**
         *
         * @param id
         * @param gp
         * @return the location of a specific product
         */
        private Enum.Location findProductLocation(int id, GeneralProduct gp) {
                if(gp.getOnShelf().contains(id))
                {
                        return Enum.Location.SHOP;
                }
                else
                {
                        return Enum.Location.STORAGE;
                }
        }

        private boolean checkAvailable(Supply sp)
        {
                return (sp.getAmount() > 0);
        }

        /**
         * report for flow product - remove the product from the supply amount and from the general product amount.
         * print if exist any error
         * @param code
         */
        public void reportFlowProduct(int code, int id,String description){
                GeneralProduct gp = getGeneralProductByCode(code);
                Supply sp = getSupplyFromGeneralById(id,gp);

                if(sp == null) {
                        System.out.println("product doesn't exist - please check product code");
                }
                else{
                        Enum.Location location = findProductLocation(id,gp);

                       boolean check = removeFromSupply(code,id,location);
                        if(check) {
                                if (gp != null) {
                                        gp.addFlowProduct(id,description);

                                        if (allFlawProducts.containsKey(gp)) {
                                                allFlawProducts.put(gp, allFlawProducts.get(gp) + 1);
                                        } else
                                                allFlawProducts.put(gp, 1);
                                }
                        }
                        else {
                                System.out.println("cannot remove this product - amount is 0!!");
                        }
                }

        }

        /**
         * find and insert to the allExpiredProducts all of the products that already expired
         */
        public void FindExpiredProducts() {
                LocalDate today = LocalDate.now();
                for(Supply sp : allRelevantSupply)
                {
                        if (today.isBefore(sp.getExpiredDate()))
                        {
                                if(!allExpiredProducts.containsKey(sp))
                                {
                                        GeneralProduct gp = sp.getGeneralProduct();
                                    allExpiredProducts.put(gp, sp.getAmount()) ;
                                    gp.removeFromShop(sp.getShopAmount());
                                    gp.removeFromStorage(sp.getStorageAmount());
                                    sp.remove();
                                    gp.addExpiredProducts(sp.getIds());
                                    allRelevantSupply.remove(sp);
                                }
                        }
                }
        }

        /**
         *
         * @param expiredDate -
         * @return a list of supply with products that will be expired before the given expiredDate
         */
        public List<Supply> getAllFutureExpiredProducts(LocalDate expiredDate){
                List<Supply> result = new ArrayList<>();
                for(Supply sp : allRelevantSupply)
                {
                        if(sp.getExpiredDate().isBefore(expiredDate))
                        {
                                result.add(sp);
                        }
                }
                return result;
        }

        /**
         *
         * @param expiredDate
         * @return HashMap with all general products and their amount of product that will be expired before the given date
         */
        public HashMap<GeneralProduct,Integer> getAllExpiredAndAmount(LocalDate expiredDate)
        {
                HashMap<GeneralProduct,Integer> result = new HashMap<>();
                List<Supply> lst = getAllFutureExpiredProducts(expiredDate);
                for(Supply sp : lst)
                {
                        GeneralProduct gp = sp.getGeneralProduct();
                        if(result.containsKey(gp))
                        {
                                result.put(gp,result.get(gp) + sp.getAmount());
                        }
                        else {
                                result.put(gp, sp.getAmount());
                        }
                }
                return result;
        }



//        /**
//         * return the general product of a supply by code - null if not such a product
//         * @param code
//         * @return
//         */
//        private GeneralProduct getGeneralProductByCod(int code) {
//                GeneralProduct gp = null;
//                for (Supply supply : allSupply) {
//                        if (supply.getCode() == code)
//                        {
//                                gp = supply.getGeneralProduct();
//                                break;
//                        }
//                }
//                if(gp == null)
//                {
//                        System.out.println("not such product - please check the code again");
//                }
//               return gp;
//        }

        /**
         * return supply product by code -null if not such product
         * @param code
         * @return
         */
//        private Supply findSupplyByCode(int code)
//        {
//                Supply supply = null;
//                for(Supply sp : allSupply) {
//                        if (sp.getCode() == code){
//                                supply = sp;
//                                break;
//                        }
//                }
//                if(supply == null)
//                {
//                        System.out.println("not such product - please check the code");
//                }
//                return supply;
//        }
        /**
         * set a discount on specific category and all its subCategories- will receive the sub categories from the service layer
         * @param allSubCategories
         * @param startDate
         * @param endDate
         * @param discountPercentage
         */
        public void setDiscountOnCategory(List<Category> allSubCategories, LocalDate startDate,LocalDate endDate, double discountPercentage)
        {
                Discount discount = new Discount(startDate,endDate,discountPercentage);
                for(GeneralProduct gp : allGeneralProducts)
                {
                        if (allSubCategories.contains(gp.getCategory()))
                        {

                                gp.setOnDiscount(true);
                                gp.setDiscount(discount);
                        }
                }
        }

        /**
         * set a discount on a specific products
         * @param allProducts
         * @param startDate
         * @param endDate
         * @param discountPercentage
         */
        public void setDiscountOnProducts(List<GeneralProduct> allProducts, LocalDate startDate,LocalDate endDate, double discountPercentage)
        {
                Discount discount = new Discount(startDate,endDate,discountPercentage);
                for(GeneralProduct gp : allProducts)
                {
                        if (allGeneralProducts.contains(gp))
                        {
                                gp.setOnDiscount(true);
                                gp.setDiscount(discount);
                        }
                }
        }

        /**
         * receive supply -
         * @param code
         * @param name
         * @param price
         * @param amount
         * @param expiredDate
         * @param manufacturer
         */
        public void receiveSupply(int code,String name, double price, int amount, LocalDate expiredDate, String manufacturer)
        {
                GeneralProduct gp = getGeneralProductByCode(code);
                if(gp != null)
                {
                        receiveExistSupply(code,price,amount,expiredDate);
                }
                else {
                        receiveNewSupply(code,name,price,amount,expiredDate,manufacturer);

                }
        }

        /**
         * if the general produce was already exist receive it
         * @param code
         * @param price
         * @param amount
         * @param expiredDate
         */
        private void receiveExistSupply(int code, double price, int amount, LocalDate expiredDate)
        {

                GeneralProduct gp = getGeneralProductByCode(code);
                int index = gp.getCurrentId();
                List<Integer> ids = addIdsList(index,amount);
                int currenId = ids.get(ids.size() - 1) + 1;
                gp.setCurrentIdt(currenId);
                gp.addToStorage(ids);
                Supply sp = new Supply(gp,expiredDate,price,amount,ids);
                gp.addSupply(sp);
                allSupply.add(sp);
                allRelevantSupply.add(sp);
        }
        private List<Integer> addIdsList(int index,int amount)
        {
                List<Integer> result = new ArrayList<>();
                while (amount >= 0)
                {
                        result.add(index);
                        index++;
                        amount--;
                }

                return result;
        }

        /**
         * if product is a new product create it and receive the supply
         * @param code
         * @param name
         * @param price
         * @param amount
         * @param expiredDate
         * @param manufacturer
         */
        private void receiveNewSupply(int code,String name, double price, int amount, LocalDate expiredDate, String manufacturer)
        {
                Scanner scanner = new Scanner(System.in);
                int id = -1;
                // Prompt the user to enter their name
                System.out.print("We notice a new General Product: name: "+ name +" code "+ code );
                System.out.println("is the product's category exist? enter y/n");
                String answer = scanner.nextLine();
                if (answer.toLowerCase() == "y"){
                System.out.println("please enter product lowest category id");
                // Read the user's input as a string
                 id = scanner.nextInt();
                }
                System.out.println("please enter minimum quantity for product : " + name );
                int minQuantity = scanner.nextInt();
                boolean check = CategoryController.ExistCategory(id);
                //if Category already exist
                if(check)
                {
                        Category category = CategoryController.getCategoyById(id);
                        //CREATE NEW SUPPLY OBJECT!!!!!!!!
                        GeneralProduct gp = new GeneralProduct(name,code,price,manufacturer,minQuantity,category,amount);
                        allGeneralProducts.add(gp);
                        receiveExistSupply(code,price,amount,expiredDate);
                }
                //it's a new category
                else {
                        System.out.println("this category is a new category, please enter category name: ");
                        String categoryName = scanner.nextLine();
                        System.out.println("if category is sub category enter its parent category, otherwise -1");
                        int parentCategory = scanner.nextInt();
                        Category parent = CategoryController.getCategoyById(id);
                        Category category = new Category(categoryName,parent);

                        //CREATE NEW SUPPLY OBJECT!!!!!!!!
                        GeneralProduct gp = new GeneralProduct(name,code,price,manufacturer,minQuantity,category,amount);
                        allGeneralProducts.add(gp);
                        receiveExistSupply(code,price,amount,expiredDate);
                }
        }

        /**
         * return a list in size @amount with all the products ids that the worker need to transfer from
         * storage to shop- שso transfer the products
         * @param code
         * @param amount
         * @return
         */
        public List<Integer> transferFromStorageToShop(int code, int amount)
        {
                GeneralProduct gp = getGeneralProductByCode(code);
                List<Integer> result = new ArrayList<>();
                Scanner scanner = new Scanner(System.in);
                String answer = "y";
                if(amount > gp.getStorage_quantity()) {
                        System.out.println("sorry your amount is more then the storage amount");
                        System.out.println("do you want to get all the available products? - choose y/n");
                        answer = scanner.nextLine();
                        if(answer == "y")
                                amount = gp.getStorage_quantity();
                }

                if(answer == "y") {
                         int index = 0;
                        while (amount > 0) {
                                Supply sp = gp.getProductSupply().get(index);
                                int cut = 0;
                                //i need to take amount from list
                                int check = amount - sp.getAmount();
                                if(check < 0)
                                {
                                         cut = amount;
                                         amount = 0;
                                        List<Integer> sublist = sp.getIds().subList(0, cut); // Get the first cut elements from the list
                                        result.addAll(sublist);
                                        sp.setIds(new ArrayList<>(sp.getIds().subList(cut , sp.getIds().size() -1))) ;

                                        sp.transferFromStorageToShop(cut);
                                        gp.transferFromStorageToShop(cut,new ArrayList<>(sp.getIds().subList(cut , sp.getIds().size() -1)) );
                                }
                                // i need extra supply
                                else {
                                        cut = sp.getAmount();
                                        amount -= cut;
                                        result.addAll(sp.getIds());
                                        sp.transferFromStorageToShop(cut);
                                        gp.transferFromStorageToShop(cut,sp.getIds());
                                        allRelevantSupply.remove(sp);
                                }

                        }

                }

                return result;
        }
        /**
         * returning a product to inventory - might be use after canceling of a buy
         * @param code
         * @param id
         */
        public void returnProduct(int code ,int id)
        {
                GeneralProduct gp = getGeneralProductByCode(code);
                Supply sp = getSupplyFromGeneralById(id,gp);
                gp.addNewQuantityToShelf(1);
                gp.addToShelf(id);
                sp.addId(id);
                sp.addNewToShop(1);


        }

        /**
         * return all products that belongs to one of the category received in the list
         * @param categories
         * @return
         */
        public List<GeneralProduct> getAllProductByCategories(List<Category> categories) {
                List<GeneralProduct> result = new ArrayList<>();
               List<Category> allSubCategories = new ArrayList<>();
               for(Category category : categories){
                       List<Category> subCategory = CategoryController.getAllSubCategories(category);
                       allSubCategories.addAll(subCategory);
               }
               for(GeneralProduct gp : allGeneralProducts){
                       boolean flag = allSubCategories.contains(gp.getCategory());
                       if(flag)
                       {
                               result.add(gp);
                       }
               }
                //drop duplicates value
                Set<GeneralProduct> uniqueSet = new HashSet<>(result);
               result.clear();
               result.addAll(uniqueSet);

               return result;
        }
}
