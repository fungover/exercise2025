package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {
	private final ArrayList<Product> products;

	public Warehouse() {
		products = new ArrayList<>();
	}
	public void addProduct(Product product) {
		if  (product == null || product.name() == null || product.name().isEmpty()) {
			System.out.println("ERROR: Product name is null or empty");
		}
		else {
			products.add(product);
		}
	}

	public List<Product> getAllProducts() {
		return Collections.unmodifiableList(products);
	}



}
