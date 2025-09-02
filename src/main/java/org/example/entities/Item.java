package org.example.entities;

public abstract class Item {
    private String name;
    private String description;
    private String type;
    private int quantity;


    // Constructor
    public Item(String name, String description, String type, int quantity) {
        this.name = name;
        this.description = description;
        this.type = type;
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

