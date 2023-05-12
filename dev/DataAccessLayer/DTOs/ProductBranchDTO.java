package DataAccessLayer.DTOs;

import java.util.HashMap;
import java.util.Map;

public class ProductBranchDTO implements DTO {
    private ProductDTO productDTO;
    private DiscountDTO discountDTO;
    private int branchId;
    private double price;
    private int minQuantity;
    private int idealQuantity;

    private HashMap<Integer,SpecificProductDTO> allSpecificProducts;
    public ProductBranchDTO(ProductDTO productDTO, DiscountDTO discountDTO,int branchId, double price, int minQuantity,int idealQuantity, HashMap<Integer,SpecificProductDTO> allSpecificProducts) {
        this.productDTO = productDTO;
        this.discountDTO = discountDTO;
        this.branchId = branchId;
        this.price = price;
        this.minQuantity = minQuantity;
        this.idealQuantity = idealQuantity;
        this.allSpecificProducts = allSpecificProducts;
    }
    @Override
    public Map<String, String> getNameToVal() {
        Map<String, String> nameToVal = new HashMap<>();
        nameToVal.put("productId", "" + productDTO.getId());
        nameToVal.put("discountId", "" + discountDTO.getId());
        nameToVal.put("branchId", "" + branchId);
        nameToVal.put("price", "" + price);
        nameToVal.put("minQuantity", "" + minQuantity);
        nameToVal.put("idealQuantity", "" + idealQuantity);
        return nameToVal;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }
    public DiscountDTO getDiscountDTO() {
        return discountDTO;
    }

    public int getBranchId() {
        return branchId;
    }

    public double getPrice() {
        return price;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getIdealQuantity() {
        return idealQuantity;
    }

    public HashMap<Integer, SpecificProductDTO> getAllSpecificProducts() {
        return allSpecificProducts;
    }


    public void updateDiscount(DiscountDTO discountDTO) {
        this.discountDTO = discountDTO;
    }

}
