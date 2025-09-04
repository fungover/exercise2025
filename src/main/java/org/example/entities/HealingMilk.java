package org.example.entities;

public class HealingMilk extends Item {
    private final int healAmount;

    public HealingMilk(int x, int y) {
        super("Healing Milk", x, y);
        this.healAmount = 20;
    }

    @Override
    public void use(Player player) {
        if (player.getHealth() == player.getMaxHealth()) {
            System.out.println("You're already at full health. No need to drink the Healing Milk.");
            return;
        }

        int healed = Math.min(healAmount, player.getMaxHealth() - player.getHealth());
        player.heal(healed);
        System.out.println("🥛 You drink the Healing Milk and recover " + healed + " HP!");
        System.out.println("Your current health: " + player.getHealth() + " HP");
    }
}
