package org.example.entities;

public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }
    @Override
    public void use(Player player) {
        player.setHealth(player.getHealth() + healAmount);
        System.out.println("You drank " + name + " and healed " + healAmount + " HP!");
    }
}
