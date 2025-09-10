package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Warehouse {

    //Lägg till kontroll av uniq id
    void addProduct(Product product);

    void updateProduct(String id, String name, Category category, int rating);

    List<Product> getAllProducts();

    Product getProductById(String id);

    List<Product> getProductsByCategorySorted(Category category);

    List<Product> getProductsCreatedAfter(LocalDate after);

    List<Product> getModifiedProducts();

    List<Category> getCategoriesWithProducts();

    Long countProductsInCategory(Category category);

    Map<Character, Integer> getProductsInitialsMap();
}
