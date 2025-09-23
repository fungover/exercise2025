package org.example.entities;

public class Enemy {
    private final String type;
    private int health;
    private final int damage;
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

    public void setHealth(int health) {
        this.health = health;
    };

    public int getHealth() {
        return this.health;
    };

    public int getDamage() {
        return this.damage;
    };

    public void Attack(Player player) {
        player.setHealth(player.getHealth() - this.damage);
    }
}

