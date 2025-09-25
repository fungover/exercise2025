package org.example.entities;

public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion() {
        super("Health Potion", "Restores 25 health", ItemType.Potion);
        this.healAmount = 25;
    }

    @Override public void use(Player player) {
        int oldHealth = player.getHealth();
        player.heal(healAmount);
        int healed = player.getHealth() - oldHealth;
        System.out.println("You used a " + name + " and recovered " + healed + " health!");
    }
}
