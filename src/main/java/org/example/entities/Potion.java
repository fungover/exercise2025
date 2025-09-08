package org.example.entities;

public class Potion extends Item {

    public Potion(String name, int healAmount) {
        super(name, "potion", healAmount);
    }

    public int getHealAmount() {
        return getEffect();
    }
}
// potion är en subklass av Item