package org.example.entities;

public class Player extends Character {
    private int baseDamage = 6; // Hardcoded value for now

    public Player(String name, int health, int x, int y) {
        super(name, health, x, y);
    }
    @Override
    public int getAttackDamage(){
        return baseDamage;
    }

    @Override
    public void takeTurn() {
        // Player takes turn logic
        System.out.println(name + " waits for your command...");
    }
}
