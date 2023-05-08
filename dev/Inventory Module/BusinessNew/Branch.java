package BusinessNew;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Branch {
    private int branchId;
    private String branchName;
    HashMap<Integer,ProductBranch> allProductBranches;// maps between productBranch generalId to its object
    public Branch(int branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.allProductBranches = new HashMap<>();
    }

    public int getId() {
        return this.branchId;
    }
    public String getName() {
        return this.branchName;
    }
    public void addNewProductBranch(Product product ,double price, int idealQuantity, int minQuantity){
        ProductBranch newProduct = new ProductBranch( product , price,  idealQuantity, minQuantity)  ;
        allProductBranches.put(product.getCode(), newProduct);
    }
    public void receiveReservation(Reservation reservation){
        List<ReceiptItem> allItems = reservation.getReceipt();
        for(ReceiptItem ri: allItems){
            int amount = ri.getAmount();
            double buyPrice = ri.getPricePerUnitAfterDiscount();
            Product product = ri.getProduct();
            LocalDate expiredDate = ri.getExpiredDate();
            int code = product.getCode();
            if(!allProductBranches.containsKey(code))
            {
                int idealQuantity = 100;
                int minQuantity = 50;
                addNewProductBranch(product,buyPrice,idealQuantity,minQuantity);
            }
            ProductBranch productBranch = allProductBranches.get(code);
            productBranch.receiveSupply(amount,buyPrice,expiredDate);
            }
    }
    public void sellProduct(int code, int specificId) throws Exception {
        ProductBranch productBranch =allProductBranches.get(code);
        if(productBranch == null)
            throw new Exception("this product doesn't exist");
        productBranch.sellProduct(specificId);
        boolean check = productBranch.checkForDeficiency();
        if(check)
            makeDeficiencyReservation(productBranch);
    }
    public HashMap<Integer, List<SpecificProduct>> getExpiredProducts(){
        HashMap<Integer,List<SpecificProduct>> allExpiredProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()){
            List<SpecificProduct> expiredProducts  = productBranch.getAllExpired();
            allExpiredProducts.put(productBranch.getCode(),expiredProducts);
        }
        return allExpiredProducts;
    }

    public HashMap<Integer, List<SpecificProduct>> getFlawsProducts(){
        HashMap<Integer,List<SpecificProduct>> allFlawsProducts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()){
            List<SpecificProduct> flawProducts  = productBranch.getAllFlaws();
            allFlawsProducts.put(productBranch.getCode(),flawProducts);
        }
        return allFlawsProducts;
    }

    public HashMap<Integer, List<Discount>> getDiscountsHistory(){
        HashMap<Integer,List<Discount>> allDiscounts = new HashMap<>();
        for (ProductBranch productBranch : allProductBranches.values()){
            List<Discount> discounts  = productBranch.getDiscountsHistory();
            allDiscounts.put(productBranch.getCode(),discounts);
        }
        return allDiscounts;
    }
    public void updateMinQuantityForProductBranch(int code, int newMinQuantity){
        ProductBranch product = allProductBranches.get(code);
        product.setminQuantity(newMinQuantity);

    }

    private void makeDeficiencyReservation(ProductBranch productBranch) {
        // TODO: make reservation for deficiency
    }


}
