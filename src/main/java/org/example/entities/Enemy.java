package org.example.entities;

public class Enemy extends Character {
    private final int damage;

    public Enemy(String name, int health, int x, int y, int damage) {
        super(name, health, x, y);
        this.damage = damage;
    }

    @Override
    public int getAttackDamage(){
        return damage;
    }
    @Override
    public void takeTurn() {
        System.out.println(getName() + " attacks for " + damage + " damage!");

    }
}
