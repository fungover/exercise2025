package org.example.entities.items;

import org.example.utils.RandomUtils;

public class Weapon extends Item {
    private int minDamage;
    private int maxDamage;

    public Weapon(String name, String description, int minDamage, int maxDamage) {
        super(name, description, ItemType.Weapon);
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
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
    public String getEquipMessage() {
        return "You equip the " + getName();
    }
    public String getUnequipMessage() {
        return "You unequip the " + getName();
    }

}
