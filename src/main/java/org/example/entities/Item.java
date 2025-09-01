package org.example.entities;

public class Item {
    private String name;
    private String description;
    private ItemType type;
    private int value; // healing amount or damage bonus

    public enum ItemType {
        WEAPON,
        CONSUMABLE
    }

    // Constructor
    public Item(String name, String description, ItemType type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }
    // Convenience constructor
    public Item(String name, String description, ItemType type) {
        this(name, description, type, 0);
    }

    // Use item method - returns the effect value
    public int use() {
        if (type == ItemType.CONSUMABLE) {
            return value;
        } else if (type == ItemType.WEAPON) {
            return value;
        }
        return 0;
    }

    public boolean isUsable() {
        return type == ItemType.CONSUMABLE;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public ItemType getType() {
        return type;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "-" + description;
    }
}

