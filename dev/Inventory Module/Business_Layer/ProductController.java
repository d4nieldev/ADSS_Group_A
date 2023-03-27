package Business_Layer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductController {

//        private List<SpecificProduct> allSpecificProducts;
        private List<GeneralProduct> allGeneralProducts;
        private HashMap<GeneralProduct,Integer> allExpiredProducts; //map with a product and the amount
        private HashMap<GeneralProduct,Integer> allFlawProducts; //map with a product and the amount
        private List<Supply> allSupply; //store all the supply

        public ProductController(){

                this.allGeneralProducts = new ArrayList<>();
                this.allExpiredProducts = new HashMap<>();
                this.allFlawProducts = new HashMap<>();
                this.allSupply = new ArrayList<>();
        }

//        public List<SpecificProduct> getAllSpecificProducts() {return allSpecificProducts;}

        public List<GeneralProduct> getAllGeneralProducts() {return allGeneralProducts;}

        public HashMap<GeneralProduct,Integer> getAllExpiredProducts() {return allExpiredProducts;}

        public HashMap<GeneralProduct,Integer> getAllFlawProducts() {return allFlawProducts;}

        public List<Supply> getAllSupply() {return allSupply;}

        /**
         * return general product by gene, null if not exit
         * @param code
         * @return
         */
        public GeneralProduct getGeneralProductByGeneralProductId(int generalProductId) {

             for(GeneralProduct gp : allGeneralProducts )
             {
                     if(gp.getGeneralProductId() == generalProductId)
                             return  gp;
                     break;
             }
             return null;
        }

        /**
         * return List of supply's products by code - supply code- null if not exist;
         * @param code
         * @return
         */
        public Supply getSupplyByCode(int code) {
               Supply result = null;
                for (Supply sp : allSupply ){
                        if(sp.getCode() == code) {
                                result = sp;
                                break;
                        }

                }
                return  result;
        }

        /**
         * remove a product from supply-
         * @param code- represent supply code
         */
        private boolean removeFromSupply(int code)
        {
                Supply sp = findSupplyByCode(code);
                boolean check = sp.setAmount();

                return check;

        }
        /***
         * simulate a sell of a product - print if operation failed
         * @param code
         */
        public void sellProduct(int code)
        {
                Supply sp = findSupplyByCode(code);

                 if(sp == null) {
                         System.out.println("cannot sell product with amount 0");
                 }
                 else {
                         GeneralProduct gp = getGeneralProductByCode(code);
                         boolean check = sp.setAmount();
                         if (!check)
                                 System.out.println("this product not available - please choose diffrent product");
                         else {

                                 boolean flag = gp.sellProduct();
                                 if(!flag)
                                         System.out.println("product not available");
                                 double sellPrice = gp.getCurrentPrice();
                                 sp.addToSellPrice(sellPrice);

                         }
                 }
        }
        /**
         * report for flow product - print if exist any error
         * @param code
         */
        public void reportFlowProduct(int code){
                boolean check = removeFromSupply(code);
                if(!check)
                {
                        System.out.println("cannot find this product - please check again for product's code");
                }
                else {
                        GeneralProduct generalProduct = getGeneralProductByCode(code);
                        if (generalProduct != null) {
                                if (allFlawProducts.containsKey(generalProduct)) {
                                        allFlawProducts.put(generalProduct, allFlawProducts.get(generalProduct) + 1);
                                } else
                                        allFlawProducts.put(generalProduct,1);
                        }
                }
        }
        public void checkForExpiredProduct() {
                for(Supply supply : allSupply)
        }
        /**
         * return the general product of a supply by code - null if not such a product
         * @param code
         * @return
         */
        private GeneralProduct getGeneralProductByCode(int code) {
                GeneralProduct gp = null;
                for (Supply supply : allSupply) {
                        if (supply.getCode() == code)
                        {
                                gp = supply.getGeneralProduct();
                                break;
                        }
                }
                if(gp == null)
                {
                        System.out.println("not such product - please check the code again");
                }
               return gp;
        }

        /**
         * return supply product by code -null if not such product
         * @param code
         * @return
         */
        private Supply findSupplyByCode(int code)
        {
                Supply supply = null;
                for(Supply sp : allSupply) {
                        if (sp.getCode() == code){
                                supply = sp;
                                break;
                        }
                }
                if(supply == null)
                {
                        System.out.println("not such product - please check the code");
                }
                return supply;
        }
        public void insertNewSupply(int code)
        {
        }

        /**
         * set a discount on specific category and all its subCategories- will recive the sub categories from the service layer
         * @param allSubCategories
         * @param startDate
         * @param endDate
         * @param discountPercentage
         */
        public void setDiscount(List<Category> allSubCategories, Date startDate,Date endDate, double discountPercentage)
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

        public void updateAllFlowProduct(){

        }
        public


}
