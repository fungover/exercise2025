package entities;

import java.util.ArrayList;
import java.util.List;


public class Player {
    private String name;
    private int health;
    private List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.health = 20;
        this.inventory = new ArrayList<>();
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

    public void addItem(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }
}
