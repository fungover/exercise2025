package org.example;

public class Product {
	private final String id;
	private final String name;
	private final Category category;
	private final int rating;

	public Product(String id, String name, Category category, int rating) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.rating = rating;
	}

	public String name() {
		return name;
	}

	public String ID() {
		return id;
	}

	public Category category() {
		return category;
	}

	public int rating() {
		return rating;
	}
}

