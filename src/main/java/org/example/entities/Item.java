package org.example.entities;

public abstract class Item {
    protected String name;
    protected String description;
    protected ItemType type;

    public enum ItemType {
        /* ItemType can have two possible values,
        enum defines a fixed set of constants
         */
        Potion, WEAPON
    }

    public Item(String name, String description, ItemType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    //getters

    //abstract method
    public abstract void use(Player player);
}
