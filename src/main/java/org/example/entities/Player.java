package org.example.entities;

public class Player extends Character {
    public Player(String name, int health, int x, int y) {
        super(name, health, x, y);
    }
    @Override
    public void takeTurn() {
        // Player takes turn logic
        System.out.println(name + " waits for your command...");
    }
}
