package org.example.entities.items;

public class HealthPotion extends Item {

    public HealthPotion(int quantity) {
        super("Health Potion", quantity);
    }

    public int restoreHealth() {
        return 20;
    }

    @Override
    public String getType() {
        return "Health Potion";
    }
}
