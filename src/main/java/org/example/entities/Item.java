package org.example.entities;
import org.example.entities.behaviors.*;
import org.example.entities.items.ItemType;

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
    public String getName() {
        return name;
    }

    public abstract ItemType getType();

    public Effect getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + " (" + getType() + ", " + effect + ")";
    }
}