package org.SpinalGlitter.exercise2.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private List<ArrayList> inventory;
    private int InventorySize;
    private Position position = new Position(0, 0);

    public Player (String name) {
        this.name = name;
        this.maxHealth = 100;
        this.damage = 10;
        this.InventorySize = 20;
        this.currentHealth = maxHealth;
        this.position = new Position(1, 1);

    }

    public String getName() {
        return  name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void takeDamage(int amount) {
        currentHealth -= amount;
        if(currentHealth <= 0) {
            currentHealth = 0;
        }

    }
        public boolean isAlive() {
            return currentHealth > 0;
        }

        public Position getPosition() {
            return position;
        }

        public void move(int dx, int dy) {
            this.position = position.getAdjacent(dx, dy);
        }

    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }


    // take damage method
    // heal method
    //pick up item method
    // drop item method
    // use item method
    // get inventory method
    // attack method
    // move location method
    // take damage method
    //
}
