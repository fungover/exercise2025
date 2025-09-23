package org.example.entities;

public class Item {
    private String name;
    private String type;
    private int effectValue;
    private String description;

    public Item(String name, String type, int effectValue, String description) {
        this.name = name;
        this.type = type;
        this.effectValue = effectValue;
        this.description = description;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getEffectValue() {
        return effectValue;
    }

    public String getDescription() {
        return description;
    }

    // Common item types
    public boolean isWeapon() {
        return "weapon".equals(type);
    }

    public boolean isPotion() {
        return "potion".equals(type);
    }

    public boolean isKey() {
        return "key".equals(type);
    }

    public boolean isTreasure() {
        return "treasure".equals(type);
    }

    @Override
    public String toString() {
        return name + " (" + type + ") - " + description;
    }
}