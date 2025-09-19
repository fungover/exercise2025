package org.example.entities;

public class Item {
    private String name;
    private String type;
    private int effect;

    // Constructor
    public Item(String name, String type, int effect) {
        this.name = name;
        this.type = type;
        this.effect = effect;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getEffect() {
        return effect;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }
}
