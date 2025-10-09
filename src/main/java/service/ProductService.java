package service;

import entities.Category;
import entities.Product;
import java.time.LocalDate;
//import java.util.ArrayList;
import java.util.List;
import repository.ProductRepository;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) { //För att lägga till produkter i listan, några steg för att validera, så som att det inte är null.
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.name() == null || product.name().isBlank()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        productRepository.addProduct(product);
    }

    public List<Product> getAllProducts() {  //Kopia på orginallistan där värden inte kan ändras.
        return productRepository.getAllProducts();
    }

    public Product getProductId(String id) {
        return productRepository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts().stream()
                .filter(p -> p.category() == category)
                .sorted((p1, p2) -> p1.name().compareToIgnoreCase(p2.name()))
                .toList();
    }



    public List<Product> getProductsCreatedAfter(LocalDate date) { //Sorterar ut produkter efter datum.
        return productRepository.getAllProducts().stream()
                .filter(p -> p.createdDate().isAfter(date))
                .toList();
    }

    public List<Product> getModifiedProducts() {  //Inte samma datum som skapat utan kan vara ändrat.
        return productRepository.getAllProducts().stream()
                .filter(p -> !p.createdDate().equals(p.modifiedDate()))
                .toList();
    }

    public void updateProduct(String id, String name, Category category, int rating) {
        Product existing = getProductId(id);

        Product updated = new Product.Builder()
                .id(existing.id())
                .name(name)
                .category(category)
                .rating(rating)
                .createdDate(existing.createdDate())
                .modifiedDate(LocalDate.now())
                .build();

        productRepository.updateProduct(updated);
    }
}
