package service;

import entities.Category;
import entities.Product;
import repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


// ProductService - Business logic layer that uses repository for data access
public class ProductService {

    private final ProductRepository productRepository;


    //Constructor injection av repository
    public ProductService(ProductRepository productRepository) {
        this.productRepository = Objects.requireNonNull(productRepository,
                "ProductRepository cannot be null");
    }

    // Adding a new product
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (product.id() == null || product.id().isBlank()) {
            throw new IllegalArgumentException("Product id cannot be null or blank");
        }

        // Check if the product already exists through the repository
        if (productRepository.getProductById(product.id()).isPresent()) {
            throw new IllegalArgumentException("Product with ID " + product.id() + " already exists");
        }

        // Delegate to repository for data storage
        productRepository.addProduct(product);
    }

    //Convenience method for adding new product using Builder pattern
    public void addNewProduct(String id, String name, Category category, int rating, double price) {
        Product product = new Product.Builder()
                .id(id)
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .asNewProduct()  // Sätter current timestamps
                .build();

        addProduct(product);
    }

    //Updating an existing product
    public void updateProduct(String id, String name, Category category, int rating, double price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null or blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }

        // Find existing product through repository
        Product existingProduct = productRepository.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));

        // Create updated product with Builder
        Product updatedProduct = new Product.Builder()
                .id(existingProduct.id())
                .name(name)
                .category(category)
                .rating(rating)
                .price(price)
                .createdDate(existingProduct.createdDate())  // Behåll ursprungligt datum
                .modifiedDate(LocalDateTime.now())           // Uppdatera modified datum
                .build();

        // Update through repository
        productRepository.updateProduct(updatedProduct);
    }

    // Downloads all products - delegates directly to repository
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    // Retrieves a specific product by ID - delegates directly to repository
    public Optional<Product> getProductById(String id) {
        return productRepository.getProductById(id);
    }

    // Retrieves products in a specific category, sorted A-Z by name
    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.category().equals(category))
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    // Retrieves products created after a specific date
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.createdDate().toLocalDate().isAfter(date))
                .collect(Collectors.toList());
    }

    // Retrieving products that have been modified
    public List<Product> getModifiedProducts() {
        return productRepository.getAllProducts()
                .stream()
                .filter(Product::isModified)
                .collect(Collectors.toList());
    }

    // ========== VG-METODER ==========

    //Retrieves all categories that have at least one product
    public Set<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts()
                .stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }

    // Counts the number of products in a specific category
    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.category() == category)
                .count();
    }

    // Returns a Map with first letters of product names and their quantity
    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts()
                .stream()
                .map(Product::name)
                .filter(name -> name != null && !name.isEmpty())
                .map(name -> name.toUpperCase().charAt(0))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().intValue()
                ));
    }

    // Top rated products created this month, sorted newest first
    public List<Product> getTopRatedProductsThisMonth() {
        YearMonth currentMonth = YearMonth.now();

        List<Product> thisMonthProducts = productRepository.getAllProducts()
                .stream()
                .filter(product -> YearMonth.from(product.createdDate()).equals(currentMonth))
                .collect(Collectors.toList());

        if (thisMonthProducts.isEmpty()) {
            return new ArrayList<>();
        }

        int maxRating = thisMonthProducts.stream()
                .mapToInt(Product::rating)
                .max()
                .orElse(0);

        return thisMonthProducts.stream()
                .filter(product -> product.rating() == maxRating)
                .sorted(Comparator.comparing(Product::createdDate).reversed())
                .collect(Collectors.toList());
    }

     // Retrieves products with ratings above the specified threshold
    public List<Product> getHighQualityProducts(int minRating) {
        if (minRating < 0 || minRating > 10) {
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }

        return productRepository.getAllProducts()
                .stream()
                .filter(product -> product.rating() >= minRating)
                .sorted(Comparator.comparing(Product::rating).reversed())
                .collect(Collectors.toList());
    }

    // Check if warehouse has products in given category
    public boolean hasProductsInCategory(Category category) {
        return productRepository.getAllProducts()
                .stream()
                .anyMatch(product -> product.category().equals(category));
    }
}