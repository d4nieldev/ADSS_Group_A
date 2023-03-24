package Business_Layer;

import java.util.ArrayList;
import java.util.List;

public class ProductController {

        private List<SpecificProduct> allSpecificProducts;
        private List<GeneralProduct> allGeneralProducts;
        private List<SpecificProduct> allExpiredProducts;
        private List<SpecificProduct> allFlawProducts;

        public ProductController(){

                this.allSpecificProducts = new ArrayList<>();
                this.allGeneralProducts = new ArrayList<>();
                this.allExpiredProducts = new ArrayList<>();
                this.allFlawProducts = new ArrayList<>();

        }

        public List<SpecificProduct> getAllSpecificProducts() {return allSpecificProducts;}

        public List<GeneralProduct> getAllGeneralProducts() {return allGeneralProducts;}

        public List<SpecificProduct> getAllExpiredProducts() {return allExpiredProducts;}

        public List<SpecificProduct> getAllFlawProducts() {return allFlawProducts;}

        /**
         * return general product by code, null if not exit
         * @param code
         * @return
         */
        public GeneralProduct getGeneralProductByCode(int code) {

             for(GeneralProduct gp : allGeneralProducts )
             {
                     if(gp.getCode() == code)
                             return  gp;
                     break;
             }
             return null;
        }

        /**
         * return List of Specific products by code
         * @param code
         * @return
         */
        public List<SpecificProduct> getSpecificProductsByCode(int code) {
                List<SpecificProduct> result = new ArrayList<>();
                for(SpecificProduct sp: allSpecificProducts)
                {
                        if(sp.getGeneralProduct().getCode() == code)
                                result.add(sp);
                }
                return result;
        }

        /**
         * return a list of all flows products
         * @return
         */
        public List<SpecificProduct> getAllFlowProducts() {
                List<SpecificProduct> result = new ArrayList<>();
                for(SpecificProduct sp: allSpecificProducts)
                {
                        if(sp.isFlaw())
                                result.add(sp);
                }
                return result;
        }



}
