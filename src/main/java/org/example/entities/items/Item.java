package org.example.entities.items;

public class Item {
    private String name;
    private String type; // t.ex. "potion" eller "weapon"
    private int effect;

    public Item(String name, String type, int effect) {
        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }
}
