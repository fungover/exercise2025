package org.example.entities.enemies;

import org.example.utils.RandomUtils;

public abstract class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int minDamage;
    private int maxDamage;

    public Enemy(String name, int health, int maxHealth, int minDamage, int maxDamage) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return RandomUtils.getRandomNumber(minDamage, maxDamage);
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setDamage(int minDamage, int maxDamage) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    //Methods
    public abstract String getAttackMessage();

    public abstract String getDeathMessage();

}
