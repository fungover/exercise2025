package org.example.entities.items;

import org.example.entities.characters.Player;

/** Base class for anything that can live in the inventory. */
public abstract class Item {
    private final String name;
    private final String description;

    protected Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /** Display name for UI/logs. */
    public String name() {
        return name;
    }

    /** Short explanation shown in inventory or when picked up. */
    public String description() {
        return description;
    }

    public boolean onUse(Player player, Inventory inventory) {
        return false;
    }

    @Override public String toString() {
        return name;
    }
}
