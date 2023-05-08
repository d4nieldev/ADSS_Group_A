package BusinessNew;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class CategoryController {
    private static Hashtable<Integer, Category> categoryDic;
    private static CategoryController instance = null;

    private CategoryController() {
        this.categoryDic = new Hashtable<>();
    }

    public static CategoryController getInstance() {
        if (instance == null) {
            instance = new CategoryController();
        }
        return instance;
    }

    /***
     * return a list of productsBranch that belong to the given categories
     * @param categoriesToDiscount
     * @param branchId
     * @return
     */
    public static List<ProductBranch> getProductsByCategories(List<Category> categoriesToDiscount, int branchId) {
        List<ProductBranch> result = new ArrayList<>();
        //TODO: return a list of all products from the branch that belongs to the categories given - need to check for not duplicate products.
        //TODO : need only the products from the given branch id -> means to enter the specific branch and find ther the products - not from the product controller
        return result;
    }
}
