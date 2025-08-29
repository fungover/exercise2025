package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private Position position;
    private List<Item> inventory;

    public Player(String name, int maxHealth, Position startPosition) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.position = startPosition;
        this.inventory = new ArrayList<>();
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, health - damage);
    }

    public void heal(int healAmount) {
        this.health = Math.min(maxHealth, health + healAmount);
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void removeFromInventory(Item item) {
        inventory.remove(item);
    }

    public Item getItemByName(String itemName) {
        return inventory.stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemName))
                .findFirst()
                .orElse(null);
    }

    public void useItem(String itemName) {
        Item item = getItemByName(itemName);
        if (item != null) {
            item.use(this);
            inventory.remove(item);
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Position getPosition() {
        return position;
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name + " [Health: " + health + "/" + maxHealth + ", Position: " + position + "]";
    }

}
