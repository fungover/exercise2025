package service;

import entities.Category;
import entities.Product;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final List<Product> products;  //final för att inte kunna byta ut listan, men möjligt att ändra och lägga till.

    public Warehouse() {
        this.products = new ArrayList<>();  //Tom lista, kan fyllas med produkter.
    }

    public void addProduct(Product product) { //För att lägga till produkter i listan, några steg för att validera, så som att det inte är null.
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        products.add(product);
    }

    public List<Product> getAllProducts() {  //Kopia på orginallistan där värden inte kan ändras.
        return new ArrayList<>(products);
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return products.stream()
                .filter(p -> p.category() == category)
                .sorted((p1, p2) -> p1.name().compareToIgnoreCase(p2.name()))  //Sorterar i alfabetiskt ordning.
                .toList();

    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) { //Sorterar ut produkter efter datum.
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {  //Inte samma datum som skapat utan kan vara ändrat.
        return products.stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .toList();
    }

    public void updateProduct(String id, String name, Category category, int rating) {

        Product existing = products.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Product updated = new Product.Builder()
                .id(existing.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDate.now())
                .build();

        products.remove(existing);
        products.add(updated);

    }
}
