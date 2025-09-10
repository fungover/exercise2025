package org.example.entities;

public abstract class Player {
    private String name;
    private int damage;
    private int health;
    private int maxHealth;
    private int positionX;
    private int positionY;

    public Player (String name,int damage,  int health, int maxHealth) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.maxHealth = maxHealth;
        this.positionX = 0;
        this.positionY = 0;
    }
}
