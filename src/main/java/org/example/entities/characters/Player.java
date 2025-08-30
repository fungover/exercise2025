package org.example.entities.characters;

import org.example.entities.enemies.Enemy;
import org.example.entities.enemies.Goblin;

public abstract class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y;
    private int damage;

    public Player(int damage, int y, int x, int maxHealth, int health, String name) {
        this.damage = damage;
        this.y = y;
        this.x = x;
        this.maxHealth = maxHealth;
        this.health = health;
        this.name = name;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDamage() {
        return damage;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    //Methods
    public String getAttackMessage(Enemy enemy) {
        return getName() + " attacks " + enemy.getName() + " for " + getDamage() + " damage!";
    }

    //Randomize all damage
    public String getDeathMessage(Enemy enemy) {
        return getName() + "dies after getting struck for" + enemy.getDamage() + " damage!";
    }

    public String getHealMessage() {
        return getName() + "heals for 10 health!";
    }

    public String getLoseHealthMessage(int amount) {
        return getName() + " loses " + amount + " health!";
    }
}