package org.example.service;

import org.example.entities.Category;
import org.example.entities.Product;
import org.example.repository.ProductRepository;

import java.time.LocalDate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public boolean addProduct(Product product) {
        return productRepository.addProduct(product);
    }

    public void updateProduct(String id, String name, Category category,
                              int rating) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null/blank");
        }
        if (category == null) {
            throw new IllegalArgumentException("category cannot be null");
        }
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating should be between 0 and 10");
        }

        //gets our product from id
        Product existing = productRepository.getProductById(id)
                                            .orElseThrow(
                                              () -> new IllegalArgumentException(
                                                "Product not found"));

        //we keep id and createdDate, and update the rest
        Product updated = new Product.Builder().id(existing.id())
                                               .name(name)
                                               .category(category)
                                               .rating(rating)
                                               .createdDate(existing.createdDate())
                                               .price(existing.price())
                                               .modifiedDate(LocalDate.now())
                                               .build();
//          (existing.id(), name, category, rating,
//          existing.createdDate(), LocalDate.now());

        productRepository.updateProduct(updated);
    }

    public Product getProductById(String id) {

        return productRepository.getProductById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                  "Product not found"));
    }

    public Product removeProductById(String id) {

        return productRepository.removeProductById(id);
    }


    public List<Product> getAllProducts() {
        return productRepository.getAllProducts(); //returns a copy so no one can
        // .clean()

    }

    @Override public String toString() {
        return "ProductService Inventory:\n" + productRepository.getAllProducts()
                                                                .stream()
                                                                .map(
                                                                  Product::toString)
                                                                .collect(
                                                                  Collectors.joining(
                                                                    "\n"));
    }

    //business logic methods should stay in service but use repository for data
    public List<Product> getProductsByCategorySorted(Category category) {
        return productRepository.findByCategory(category)
                                .stream()
                                .sorted(Comparator.comparing(Product::name))
                                .collect(Collectors.toUnmodifiableList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return productRepository.findCreatedAfter(date)
                                .stream()
                                .collect(Collectors.toUnmodifiableList());
    }

    public List<Product> getModifiedProducts() {
        return productRepository.findModified()
                                .stream()
                                .collect(Collectors.toUnmodifiableList());
    }

    public List<Category> getCategoriesWithProducts() {
        return productRepository.getAllProducts()
                                .stream()
                                .map(Product::category)//get the category of each
                                // product
                                .distinct()//a category only appears once in results
                                .collect(Collectors.toUnmodifiableList());
    }

    public long countProductsInCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        return productRepository.findByCategory(category)
                                .size();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return productRepository.getAllProducts()
                                .stream()
                                //for each p get first char, make char toUpperCase to
                                // avoid H and h to be different
                                .map(p -> Character.toUpperCase(p.name()
                                                                 .charAt(0)))
                                //group identical chars together,count how many
                                // times each
                                // appears
                                .collect(Collectors.groupingBy(c -> c,
                                  Collectors.counting()))
                                //convert map char,long -> map.Entry char,long
                                .entrySet()
                                .stream()
                                .collect(Collectors.toMap(Map.Entry::getKey,
                                  i -> i.getValue()
                                        .intValue()));
    }

    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate currentDate = LocalDate.now();

        return productRepository.getAllProducts()
                                .stream()
                                .filter(p -> p.rating() == 10)
                                .filter(p -> p.createdDate()
                                              .getMonth() ==
                                  currentDate.getMonth() && p.createdDate()
                                                             .getYear() ==
                                  currentDate.getYear())
                                .sorted(Comparator.comparing(Product::createdDate)
                                                  .reversed())
                                .collect(Collectors.toUnmodifiableList());

    }
}
