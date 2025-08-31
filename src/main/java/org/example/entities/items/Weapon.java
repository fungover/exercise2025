package org.example.entities.items;

import org.example.utils.RandomUtils;

public class Weapon extends Item {
    private int minDamage;
    private int maxDamage;

    public Weapon(String name, String description, ItemType type) {
        super(name, description, type);
        this.minDamage = 0;
        this.maxDamage = 0;
    }

    //Getters
    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    //Setters
    public void setMinDamage(int minDamage) {
        this.minDamage = minDamage;
    }

    public void setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
    }

    //Methods
    public int getDamage() {
        return RandomUtils.getRandomNumber( minDamage, maxDamage );
    }

    //Override Methods
    @Override
    public String getEquipMessage() {
        return "You equip the " + getName();
    }

    @Override
    public String getUnequipMessage() {
        return "You unequip the " + getName();
    }

    @Override
    public String getUseMessage() {
        return "You use the " + getName() + "for " + getDamage() + " damage";
    }

}
