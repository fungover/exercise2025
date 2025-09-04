package org.example.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> exits; // Direction -> room
    private List<Item> items;
    private List<Enemy> enemies;
    private boolean visited;

    // Constructor
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.visited = false;
    }

    //Exit
    public void addExit(String direction, Room room) {
        exits.put(direction.toLowerCase(), room);
    }
    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }
    public boolean hasExit(String direction) {
        return exits.containsKey(direction.toLowerCase());
    }

    // Item management
    public void addItem(Item item) {
        items.add(item);
    }
    public boolean removeItem(Item item) {
        return items.remove(item);
    }
    public Item findItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    // Enemy management
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }
    public Enemy getAliveEnemy() {
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                return enemy;
            }
        }
        return null;
    }
    public boolean hasAliveEnemies() {
        return getAliveEnemy() != null;
    }

    // Display methods
    public void look() {
        System.out.println("\nYou are here:");
        System.out.println("=== " + name + " ===");
        System.out.println(description);

        //Show exits
        if (!exits.isEmpty()) {
            System.out.println("\nExits: " + String.join(", ", exits.keySet()));
        }

        //Show items
        if (!items.isEmpty()) {
            System.out.println("\nItems here: ");
            for (Item item : items) {
                System.out.println("- " + item.getName() + " (" + item.getDescription() + ")");
            }
        } else {
            System.out.println("\nNo items in here.");
        }

        //Show enemies
        if (!enemies.isEmpty()) {
            System.out.println("\nEnemies here: ");
            for (Enemy enemy : enemies) {
                if (enemy.isAlive()) {
                    System.out.println("- " + enemy.getName() + " - " + enemy.getDescription() + " (" + enemy.getHealth() + " HP)");
                }
            }
        }

        visited = true;
    }

    public boolean removeEnemy(Enemy enemy) {
        return enemies.remove(enemy);
    }

    public void shortDescription() {
        if (visited) {
            System.out.println(name);
        } else {
            look();
        }
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Map<String, Room> getExits() {
        return exits;
    }
    public List<Item> getItems() {
        return items;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
    public boolean isVisited() {
        return visited;
    }

    @Override
    public String toString() {
        return name;
    }
}

