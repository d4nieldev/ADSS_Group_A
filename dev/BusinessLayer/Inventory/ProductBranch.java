package BusinessLayer.Inventory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BusinessLayer.InveontorySuppliers.Product;

public class ProductBranch {

    private Product product;
    private double price;
    private int idealQuantity;
    private int minQuantity;
    private int totalAmount;
    private HashMap<Integer, SpecificProduct> allSpecificProducts;// maps between specificId and its object
    private List<Discount> discountsHistory;
    private Discount discount;

    // TODO : check if product exist on suppliers
    private Boolean ExistOnSuppliers;

    public ProductBranch(Product product, double price, int idealQuantity, int minQuantity) {
        this.product = product;
        this.price = price;
        this.idealQuantity = idealQuantity;
        this.minQuantity = minQuantity;
        this.allSpecificProducts = new HashMap<>();
        this.discount = null;
        this.totalAmount = 0;
        this.discountsHistory = new ArrayList<>();
    }

    public double getPrice() {
        this.discount = getCurrentMaxDiscount();
        if (discount != null) {
            return discount.getPriceWithDiscount(price);
        } else
            return price;
    }

    public String getName() {
        return product.getName();
    }

    public int getIdealQuantity() {
        return idealQuantity;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public List<Discount> getDiscountsHistory() {
        return discountsHistory;
    }

    public void changeProductStatus(int specificProduct, ProductStatus.status status) {
        SpecificProduct sp = allSpecificProducts.get(specificProduct);
        if (sp != null)
            sp.setStatus(status);
    }

    public void changeProductStatus(int specificProduct, String description) {
        SpecificProduct sp = allSpecificProducts.get(specificProduct);
        if (sp != null)
            sp.setFlawDescription(description);
    }

    public void applyDiscount(Discount discount) {
        discountsHistory.add(discount);
        Discount maxDiscount = getCurrentMaxDiscount();
        this.discount = maxDiscount;
    }

    public int getCode() {
        return product.getId();
    }

    public int getMinQuantity() {
        return this.minQuantity;
    }

    public void setMinQuantity(int newMinQuantity) {
        this.minQuantity = newMinQuantity;
    }

    private Discount getCurrentMaxDiscount() {
        Discount maxDiscount = null;
        double currentPrice = price;
        for (Discount dis : discountsHistory) {
            if (dis.getStart_date().isBefore(LocalDate.now()) && dis.getEnd_date().isAfter(LocalDate.now())) {
                if (dis instanceof DiscountPercentage) {
                    double p = price * (1 - ((DiscountPercentage) dis).getDiscountPercentage());
                    if (currentPrice > p) {
                        maxDiscount = dis;
                        currentPrice = p;
                    }
                } else if (dis instanceof DiscountFixed) {
                    double p = Math.max(0, price - ((DiscountFixed) dis).getDiscountValue());
                    if (currentPrice > p) {
                        maxDiscount = dis;
                        currentPrice = p;
                    }
                }
            }
        }
        return maxDiscount;
    }

    public List<SpecificProduct> getAllExpired() {
        List<SpecificProduct> allExpired = new ArrayList<>();
        for (SpecificProduct sp : allSpecificProducts.values()) {
            if (sp.getIsExpired()) {
                if (sp.getStatus() != ProductStatus.status.SOLD) {
                    allExpired.add(sp);
                    if (sp.getStatus() == ProductStatus.status.ON_STORAGE
                            || sp.getStatus() == ProductStatus.status.ON_SHELF) {
                        sp.setStatus(ProductStatus.status.EXPIRED);
                        totalAmount--;
                    }
                }
            }
        }
        return allExpired;
    }

    public List<SpecificProduct> getAllFlaws() {
        List<SpecificProduct> allFlaws = new ArrayList<>();
        for (SpecificProduct sp : allSpecificProducts.values()) {
            if (sp.getStatus() == ProductStatus.status.IS_FLAW) {
                allFlaws.add(sp);
            }
        }
        return allFlaws;
    }

    public void receiveSupply(int amount, double buyPrice, LocalDate expiredDate) {
        for (int i = 0; i < amount; i++) {
            SpecificProduct newSpecific = new SpecificProduct(product.getId(), buyPrice, expiredDate);
            allSpecificProducts.put(newSpecific.getSpecificId(), newSpecific);
            totalAmount++;
        }
    }

    public void transferToShop(int amount) {
        List<SpecificProduct> onStorageProducts = getOnStorageProducts();
        if (amount > onStorageProducts.size())
            amount = onStorageProducts.size();
        for (int i = 0; i < amount; i++) {
            onStorageProducts.get(i).setStatus(ProductStatus.status.ON_SHELF);
        }
    }

    private List<SpecificProduct> getOnStorageProducts() {
        List<SpecificProduct> onStorageProducts = new ArrayList<>();
        for (SpecificProduct sp : allSpecificProducts.values()) {
            if (sp.getStatus() == ProductStatus.status.ON_STORAGE)
                onStorageProducts.add(sp);
        }
        return onStorageProducts;
    }

    private List<SpecificProduct> getOnSelfProducts() {
        List<SpecificProduct> OnSelfProducts = new ArrayList<>();
        for (SpecificProduct sp : allSpecificProducts.values()) {
            if (sp.getStatus() == ProductStatus.status.ON_SHELF)
                OnSelfProducts.add(sp);
        }
        return OnSelfProducts;
    }

    private SpecificProduct getSpecificProduct(int specificId) {
        return allSpecificProducts.get(specificId);
    }

    /**
     * sell a product and update the inventory quantity
     * 
     * @param specificId
     */
    public void sellProduct(int specificId) throws Exception {
        SpecificProduct sp = getSpecificProduct(specificId);
        if (sp != null) {
            if (sp.getStatus() == ProductStatus.status.ON_SHELF || sp.getStatus() == ProductStatus.status.ON_STORAGE) {
                sp.setStatus(ProductStatus.status.SOLD);
                totalAmount--;
                // updating sellPrice
                UpdateSellPrice(sp);
                System.out.println(getPrice());
            } else
                throw new Exception("cannot sell products not from shelf");
        }

    }

    private void UpdateSellPrice(SpecificProduct sp) {
        double sellPrice = getPrice();
        sp.setSellPrice(sellPrice);
    }

    public boolean checkForDeficiency() {
        if (totalAmount < minQuantity) {
            return true;
        }
        return false;
    }

    public void reportFlawProduct(int specificId, String description) throws Exception {
        SpecificProduct specificProduct = allSpecificProducts.get(specificId);
        if (specificProduct == null)
            throw new Exception("this specific product doesn't exist");
        ProductStatus.status status = specificProduct.getStatus();
        if (specificProduct.getStatus() != ProductStatus.status.SOLD)
            specificProduct.setStatus(ProductStatus.status.IS_FLAW);
        specificProduct.setFlawDescription(description);
        // change total amount only if the product was for sale
        if (status == ProductStatus.status.ON_SHELF || status == ProductStatus.status.ON_STORAGE) {
            totalAmount--;
        }
    }

    public List<SpecificProduct> getOnShelfProduct() {
        List<SpecificProduct> result = new ArrayList<>();
        for (SpecificProduct specificProduct : allSpecificProducts.values()) {
            if (specificProduct.getStatus() == ProductStatus.status.ON_SHELF) {
                result.add(specificProduct);
            }
        }
        return result;
    }

    public int getExpiredAmount() {
        int result = 0;
        for (SpecificProduct specificProduct : allSpecificProducts.values()) {
            if (specificProduct.getIsExpired())
                result++;
        }
        return result;
    }

    public String getManufacturer() {
        return product.getManufacturer();
    }

    public Category getCategory() {
        return product.getCategory();
    }

    public boolean existInCategories(List<Category> allSubCategories) {
        boolean result = false;
        for (Category category : allSubCategories) {
            if (product.getCategory() == category) {
                result = true;
                break;
            }
        }

        return result;
    }
}
