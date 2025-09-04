package org.example.entities;

public class Healing extends Item implements Usable {
    private int healingValue;

    public Healing(String name, String description, String type, int quantity, int healingValue) {
        super(name, description, type, quantity);
        this.healingValue = healingValue;
    }

    public int getHealingValue() {
        return healingValue;
    }

    @Override
    public void displayInfo() {
        System.out.println(getName() + " - " + getDescription() + " (Heals for +" +
                getHealingValue() + " HP)");
    }

    @Override
    public String toString() {
        return getName() + " (Healing: " + healingValue + " HP)";
    }

    @Override
    public void use(Player player) {
        player.heal(healingValue);
        System.out.println("You used " + getName() + " and healed " + healingValue + " HP!");
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
