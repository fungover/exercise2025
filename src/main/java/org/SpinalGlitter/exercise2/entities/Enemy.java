package org.SpinalGlitter.exercise2.entities;


public class Enemy {
    private final String name;
    private int hp;
    private final int damage;
    private final Position position;

    public Enemy(String name, int hp, int damage, Position position) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int damage) {
        this.hp -= damage;
    }

    // enemies attack damage
   public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;

    }

    public Position getPosition() {
        return position;
    }
}

