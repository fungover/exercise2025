package org.example.entities.items;

public abstract class Item {
    private String name;
    private String description;
    private ItemType type;

    public enum ItemType {
        Potion, Weapon
    }

    public Item(String name, String description, ItemType type) {
        this.name = java.util.Objects.requireNonNull(name, "name");
        if (this.name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        this.description = java.util.Objects.requireNonNull(description, "description");
        if (this.description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }

        this.type = java.util.Objects.requireNonNull(type, "type");
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getType() {
        return type;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

}
