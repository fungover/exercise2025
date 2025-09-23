package org.example.entities;

import org.example.entities.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int row;
    private int col;
    private List<Item> inventory = new ArrayList<>();


    public Player(String name) {
        this.name = name; //player name
        this.health = 100; // start health 100
        this.row = 0; // start row
        this.col = 0; // start column
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void moveTo(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public boolean hasWeapon() {
        for (Item item : inventory) {
            if (item.getType().equals("weapon")) return true;
        }
        return false;
    }
}

