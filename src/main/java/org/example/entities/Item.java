package org.example.entities;

public abstract class Item {
    protected String name;
    protected String description;
    protected ItemType type;

    public enum ItemType {
        /* ItemType can have two possible values,
        enum defines a fixed set of constants
         */
        Potion, Weapon
    }

    public Item(String name, String description, ItemType type) {
        this.name = name; this.description = description; this.type = type;
    }

    //getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    //abstract method
    public abstract void use(Player player);
}
