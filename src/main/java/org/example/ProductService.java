package org.example;

import org.example.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;

public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getProductsCreatedAfter(LocalDateTime now) {
		return productRepository.getAllProducts().stream()
						.filter(product -> product.createdAt().isAfter(now))
						.toList();
	}

	public List<Product> getModifiedProducts() {
		return productRepository.getAllProducts().stream()
						.filter(product -> !product.createdAt().equals(product.modifiedAt()))
						.toList();
	}

	public List<Product> getProductsByCategorySorted(Category category) {
		return productRepository.getAllProducts().stream()
						.filter(product -> product.category().equals(category))
						.sorted(Comparator.comparing(Product::name))
						.toList();
	}
}
