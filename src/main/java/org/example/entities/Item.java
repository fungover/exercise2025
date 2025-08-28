package org.example.entities;

public abstract class Item {
    protected String name; //Using protected to allow access in subclasses
    protected String description;
    protected Position position;

    public Item(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    public abstract void use(Player player);

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Position getPosition() { return position; }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}
