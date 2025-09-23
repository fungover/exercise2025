package org.warehouse;

import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.List;

public class ProductService {
  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void addProduct(Product product) {
    productRepository.addProduct(product);
  }

  public Product getProductById(String id) {
    return productRepository.getProductById(id);
  }

  public List<Product> getAllProducts() {
    return productRepository.getAllProducts();
  }

  public void updateProduct(Product product) {
    productRepository.updateProduct(product);
  }

  public List<Product> getProductsByCategorySorted(Category category) {
    return productRepository.getProductsByCategorySorted(category);
  }

  public List<Product> getProductsCreatedAfter(LocalDate date) {
    return productRepository.getProductsCreatedAfter(date);
  }

  public List<Product> getModifiedProducts() {
    return productRepository.getModifiedProducts();
  }




}