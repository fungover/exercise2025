package org.example.service;


import org.example.entities.Product;
import org.example.entities.Category;
import org.example.repository.ProductRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService /*Warehouse*/ {

    /*private final Map<String, Product> products = new HashMap<>();*/

    private final ProductRepository productRepository;

/*    public ProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }*/

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // addProduct(Product product): Add a new product (validate name is not empty)
    public void addProduct(Product product) {

        productRepository.addProduct(product);
    }

    public Optional<Product> getProductByID(String id) {
        return productRepository.getProductByID(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    /*    *
     * Get products by category sorted by name A-Z
     *
     * @param category the category to filter by
     * @return a list of products in the specified category sorted by name*/
    public List<Product> getProductsByCategorySorted(Category category) {
        return getAllProducts()
                .stream()
                .filter(p -> p.category() == category)
                .sorted(Comparator.comparing(Product::name))
                .collect(Collectors.toList());
    }

    /**
     * Get products created after a specific date
     *
     * @param date the date to filter by
     * @return list of products created after the specified date
     */
    public List<Product> getProductsCreatedAfter(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return getAllProducts()
                .stream()
                .filter(p -> p.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    //getModifiedProducts(): Products where createdDate != modifiedDate
    public List<Product> getModifiedProducts() {
        return getAllProducts()
                .stream()
                .filter(product -> !product.createdDate().equals(product.modifiedDate()))
                .collect(Collectors.toList());
    }

    /**
     * Get all categories that have at least one product
     *
     * @return set of categories with products
     */
    public Set<Category> getCategoriesWithProducts() {
        return getAllProducts().stream()
                .map(Product::category)
                .collect(Collectors.toSet());
    }

    /**
     * Count products in a specific category
     *
     * @param category the category to filter by
     * @return count of products in the specified category
     */
    public int countProductsInCategory(Category category) {
        return (int) getAllProducts()
                .stream()
                .filter(p -> p.category() == category)
                .count();
    }

    public Map<Character, Integer> getProductInitialsMap() {
        return getAllProducts()
                .stream()
                .map(p -> p.name().toUpperCase().charAt(0))
                .collect(Collectors.toMap(
                        initial -> initial,
                        initial -> 1,
                        Integer::sum
                ));
    }

    /**
     * Get the top 3 highest rated products created in the current month
     *
     * @return list of top 3 highest rated products created this month
     */
    public List<Product> getTopRatedProductsThisMonth() {
        LocalDate now = LocalDate.now();
        return getAllProducts()
                .stream()
                .filter(p -> p.createdDate().getMonth() == now.getMonth() && p.createdDate().getYear() == now.getYear())
                .sorted(Comparator.comparingInt(Product::rating).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

}
