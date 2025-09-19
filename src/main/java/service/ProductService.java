package service;

import entities.Category;
import entities.Product;
import repository.ProductRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * I need to inject my repository into this
 **/

public class ProductService {

    //Dependency injection
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = Objects.requireNonNull(repo, "Repo can not be null");
    }

    public Product addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("product can not be null");
        }

        if (repo.existsById(product.getId())) {
            throw new IllegalArgumentException("Product already exists: " + product.getId());
        }
        repo.addProduct(product);
        return product;
    }

    public Product getProductById(String id) {
        String key = Objects.requireNonNull(id, "Id can not be null").trim();
        if (key.isEmpty()) throw new IllegalArgumentException("Id can not be empty");
        return repo.getProductById(key).orElseThrow(() -> new IllegalArgumentException("No product with id: " + key));
    }

    public List<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    public Product updateProduct(String id, String name, Category category, int rating) {
        Objects.requireNonNull(category, "category can not be null");

        String key = Objects.requireNonNull(id, "Id can not be null").trim();
        if (key.isEmpty()) throw new IllegalArgumentException("Id can not be empty");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name can not be empty");
        if (rating < 0 || rating > 10) throw new IllegalArgumentException("Rating must be between 0 and 10");

        Product existing = getProductById(key);

        Product updated = new Product.Builder()
                .id(existing.getId())
                .name(name.trim())
                .category(category)
                .rating(rating)
                .createdDate(existing.getCreatedDate())
                .modifiedDate(LocalDate.now())
                .build();

        repo.updateProduct(updated);
        return updated;
    }

    // Sorted A-Ö, filters products from the same category that I send in. Is returned in alphabetical order, immutable copy
    public List<Product> getProductsByCategorySorted(Category category) {
        Objects.requireNonNull(category, "category can not be null");
        return repo.getAllProducts().stream().filter(p -> p.getCategory() == category)
                .sorted(Comparator.comparing(Product::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    // "Created at what date" - gets and filters through all product and returns which ones have a specific createdDate
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        Objects.requireNonNull(date, "date can not be null");
        return repo.getAllProducts().stream()
                .filter(p -> p.getCreatedDate().isAfter(date))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    // Gets all products, filters all products that are changed after creation (not used yet)
    public List<Product> getModifiedProducts() {
        return repo.getAllProducts().stream().filter(p -> !p.getCreatedDate().equals(p.getModifiedDate()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

}

