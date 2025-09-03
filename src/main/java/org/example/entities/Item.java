package org.example.entities;

public enum ItemType {
    WEAPON,
    POTION
}

interface Effect {
    void apply(Player player);
    String toString();
}

public abstract class Item {
    private final String name;
    private final Effect effect;

    protected Item(String name, Effect effect) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }
        if (effect == null) {
            throw new IllegalArgumentException("Effect cannot be null");
        }
        this.name = name;
        this.effect = effect;
    }
}
