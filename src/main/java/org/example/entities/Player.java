package org.example.entities;

import java.util.List;
import java.util.ArrayList;

import org.example.entities.Item;

public class Player {
    private String name;
    private int health;
    private int attack;
    private int x, y;
    private List<Item> inventory;


    //Constructor
    public Player(String name, int health, int attack, int startX, int startY) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.x = startX;
        this.y = startY;
        this.inventory = new ArrayList<>();
    }

    //Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() { return attack; }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setHealth(int health) {
        this.health = health;
    }
    public void setAttack(int attack) { this.attack = attack; }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

}
