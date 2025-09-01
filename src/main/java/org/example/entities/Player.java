package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y; //position
    private int damage;
    private List<Item> inventory;

    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.damage = 10;
        this.inventory = new ArrayList<>();

    }

    //getters

    public String getName() {return name;}

    public int getHealth() {return health;}

    public int getMaxHealth() {return maxHealth;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int getDamage() {return damage;}

    public List<Item> getInventory() {return inventory;}

    //setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, this.health - damage);
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }


    //methods
    public void heal(int amount) {
        //to not heal the player for more then the max hp
        this.health = Math.min(maxHealth, this.health + amount);
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void displayStats() {
        System.out.println("=== " + name + " ===");
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Damage:  " + damage);
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void displayInventory() {
        System.out.println("=== Inventory ===");
        if (inventory.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ", " + inventory.get(i).getName() + " - " +
                  inventory.get(i).getDescription());
            }
        }
    }

}
