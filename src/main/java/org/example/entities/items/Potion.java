package org.example.entities.items;

import org.example.utils.RandomUtils;

public class Potion extends Item{
    private int minValue;
    private int maxValue;

    public Potion(String name, String description, int minHealth, int maxHealth) {
        super(name, description, ItemType.Potion);
        if(minHealth < 0 || maxHealth < minHealth) {
            throw new IllegalArgumentException("Min health must be greater than 0 and max health must be greater than or equal to min health");
        }
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
        if(minValue < 0 || this.minValue > maxValue) {
            throw new IllegalArgumentException("Min value must be greater than 0 and min value must be less than or equal to max value");
        }
        this.minValue = minValue;
    }

    public void setMaxValue(int maxValue) {
        if(maxValue < this.minValue){
            throw new IllegalArgumentException("Max value must be greater than or equal to min value");
        }
        this.maxValue = maxValue;
    }

    //Methods
    public int getValue() {
        return RandomUtils.getRandomNumber(minValue, maxValue);
    }

}
