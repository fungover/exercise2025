package org.example;

import java.util.Random;

public class Product {
	Random rand = new Random();
	private final String id;
	private final String name;

	public Product(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
}
