package BusinessNew;

import java.util.ArrayList;
import java.util.Hashtable;

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


}
