package org.example.entities;

public class HealingMilk extends Item {
    private final int healAmount;

    public HealingMilk(int x, int y) {
        super("Healing Milk", x, y);
        this.healAmount = 20;
    }

    @Override
    public void use(Player player) {
        player.heal(healAmount);
        System.out.println("🥛 You drink the Healing Milk and recover " + healAmount + " HP!");
        System.out.println("Your current health: " + player.getHealth() + " HP");
    }
}
