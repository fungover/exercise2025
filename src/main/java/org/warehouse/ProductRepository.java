package org.warehouse;

import org.warehouse.entities.Category;
import org.warehouse.entities.Product;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository {
  void addProduct(Product product);
  Product getProductById(String id);
  List<Product> getAllProducts();
  void updateProduct(Product product);
  List<Product> getProductsByCategorySorted(Category category);
  List<Product> getProductsCreatedAfter(LocalDate date);
  List<Product> getModifiedProducts();
}
