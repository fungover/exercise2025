package org.example.entities;

public class HealthPotion extends Item {
    private int healAmount;

    public HealthPotion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }
    @Override
    public void use(Player player) {
       int before = player.getHealth();
       int after = Math.min(before + healAmount, player.getMaxHealth());
        player.setHealth(after);

        int healed = after - before; // Actual healed amount
        System.out.println("You drank " + getName() + " and healed " + healed + " HP!");
    }
}
