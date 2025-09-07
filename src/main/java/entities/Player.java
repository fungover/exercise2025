package entities;

import utils.Position;

import java.util.ArrayList;

public class Player {
    private String name = "Unknown Slayer";
    private int health = 100;
    private Position position;
    private ArrayList<Item> inventory = new ArrayList<>();
    private Item currentItem;

    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (isAlive()) {
            System.out.printf("You lost %d in health, now your hp is: %d\n", damage, health);
        } else {
            System.out.println("You were killed");
        }
    }

    public Position getPosition() {
        return position;
    }

    public void move(int dx, int dy) {
        position.move(dx, dy);
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.printf("You just picked up " + item);
    }

    public void useItem(Item item) {
        currentItem = item;
    }

    public void listInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You have no items");
        } else {
            for (Item item : inventory) {
                System.out.println(item);
            }
        }
    }

    public void heal(int heal) {
        health += heal;
    }

    public void attack(Enemy enemy) {
        // TODO
    }

}
