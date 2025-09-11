package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Warehouse {
	private final ArrayList<Product> products;

	public Warehouse() {
		products = new ArrayList<>();
	}
	public boolean addProduct(Product product) {
		if  (product == null || product.getName() == null || product.getName().isEmpty()) {
			return false;
		}
		else {
			products.add(product);
			return true;
		}
	}

	public List<Product> getProducts() {
		return Collections.unmodifiableList(products);
	}
}
