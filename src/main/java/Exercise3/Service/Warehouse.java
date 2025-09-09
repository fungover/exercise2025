package Exercise3.Service;

import Exercise3.Entities.Category;
import Exercise3.Entities.Product;

import java.util.List;

public interface Warehouse {

    //Lägg till kontroll av uniq id
    public void addProduct(Product product);

    public void updateProduct(String id, String name, Category category, int rating);

    public List<Product> getAllProducts();

    public Product getProductById(String id);

    public List<Product> getProductsByCategorySorted(Category category);
}
