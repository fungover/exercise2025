package org.example.entities;

import java.util.Objects;

public abstract class Item {
    private String name;
    private String description;
    private String type;
    private int quantity;


    // Constructor
    public Item(String name, String description, String type, int quantity) {
        this.name = Objects.requireNonNull(name, "name");
        this.description = Objects.requireNonNull(description, "description");
        this.type = Objects.requireNonNull(type, "type");
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be >= 0");
        this.quantity = quantity;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public abstract void displayInfo();

}

