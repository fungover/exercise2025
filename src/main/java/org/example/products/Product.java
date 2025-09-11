package org.example.products;

import java.time.LocalDate;

public class Product {
	private final String id;
	private final String name;
	private final Category category;
	private final LocalDate createdAt;
	private LocalDate modifiedAt;
	public Product(String id, String name, Category category, LocalDate createdAt, LocalDate modifiedAt) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}
}
