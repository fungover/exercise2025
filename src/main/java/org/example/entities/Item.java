package org.example.entities;


//namn, typ (vapen, potion osv), effekt.
public class Item {
    private String name;
    private String type; // t.ex. "potion" eller "weapon"
    private int effect;

    public Item(String name, String type, int effect) {
        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    // Getters
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
