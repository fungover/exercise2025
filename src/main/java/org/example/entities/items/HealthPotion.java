package org.example.entities.items;

public class HealthPotion extends Item {

    public HealthPotion(String name, int quantity) {
        super(name, quantity);
    }

    public int restoreHealth() {
        return 20;
    }
}
