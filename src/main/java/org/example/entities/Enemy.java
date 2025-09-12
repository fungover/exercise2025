package org.example.entities;

public class Enemy {
    private String type;
    private int health;
    private int damage;
    private int positionX;
    private int positionY;

    public Enemy (String type, int health, int damage, int positionX, int positionY) {
        this.type = type;
        this.health = health;
        this.damage = damage;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getType() {
      return this.type;
    };
}

