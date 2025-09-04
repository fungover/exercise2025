package org.example.entities.enemies;

import org.example.utils.RandomUtils;

public abstract class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int minDamage;
    private int maxDamage;

    public Enemy(String name, int health, int maxHealth, int minDamage, int maxDamage) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0");
        }
        if (minDamage < 0 || maxDamage < minDamage) {
            throw new IllegalArgumentException("Min damage must be greater than 0 and max damage must be greater than or equal to min damage");
        }

        this.name = name;
        this.health = Math.max(0, Math.min(health, maxHealth));
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
        this.health = Math.max(0, Math.min(health, maxHealth));;
    }

    public void setMaxHealth(int maxHealth) {
        if (maxHealth <= 0) {
            throw new IllegalArgumentException("Max health must be greater than 0");
        }
        this.maxHealth = maxHealth;
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
    }

    public void setDamage(int minDamage, int maxDamage) {
        if (minDamage < 0 || maxDamage < minDamage) {
            throw new IllegalArgumentException("Min damage must be greater than 0 and max damage must be greater than or equal to min damage");
        }

        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    //Methods
    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
        }
    }

    public abstract String getAttackMessage();

    public abstract String getDeathMessage();

}
