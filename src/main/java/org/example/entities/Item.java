package org.example.entities;

import org.example.utils.RNG;

public abstract class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void use(Character character);

    public static Item getRandomItem() {
        int rand = RNG.randomInt(0, 3);
        return switch(rand) {
            case 0 -> new Potion("Weak Potion", 5);
            case 1 -> new Potion("Strong Potion", 20);
            case 2 -> new Weapon("Rusty Sword", 2);
            case 3 -> new Weapon("Iron Sword", 5);
            default -> null;
        };
    }
}
