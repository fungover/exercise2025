package org.example.entities.items;

import org.example.utils.RandomUtils;

public class Potion extends Item{
    private int minValue;
    private int maxValue;

    public Potion(String name, String description, int minHealth, int maxHealth) {
        super(name, description, ItemType.Potion);
        this.minValue = minHealth;
        this.maxValue = maxHealth;
    }

    //Getters
    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    //Setters
    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    //Methods
    public int getValue() {
        return RandomUtils.getRandomNumber(minValue, maxValue);
    }

    //Override Methods
    @Override
    public String getUseMessage() {
        return "> You use a " + getName() + " and restore " + getValue() + " health!";
    }
}
