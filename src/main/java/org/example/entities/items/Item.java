package org.example.entities.items;

public abstract class Item {
    private String name;
    private String description;
    private ItemType type;

    public enum ItemType {
        Potion, Weapon
    }

    public Item(String name, String description, ItemType type) {
        this.name = name;
        this.description = description;
        this.type = type;
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

    //Method
    public abstract String getEquipMessage();
    public abstract String getUnequipMessage();
    public abstract String getUseMessage();


}
