package org.warehouse;

import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  void addProduct(Product product);
  Product getProductById(String id);
  List<Product> getAllProducts();
  void updateProduct(Product product);
  public List<Product> getProductsByCategorySorted(Category category);
  public List<Product> getProductsCreatedAfter(LocalDate date);
  public List<Product> getModifiedProducts();
}
