package org.example.entities;

public class HealthPotion extends Item {

    private final int healAmount;

    public HealthPotion(Position position) {
        super("Health Potion", "Restores 30 health points.", position);
        this.healAmount = 30;
    }

    public HealthPotion(Position position, int healAmount) {
        super("Health Potion", "Restores " + healAmount + " Health points", position);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Player player) {
        System.out.println("You drink the " + name + "!");
        player.heal(healAmount);
        System.out.println("You feel better! Restored " + healAmount + "health.");
    }
}
