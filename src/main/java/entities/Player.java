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

    public ArrayList<Item> getInventory() {
        return inventory;
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

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(int dx, int dy) {
        position.move(dx, dy);
    }

    public void addItem(Item item) {
        inventory.add(item);
        System.out.println("You just picked up " + item);
    }

    public void useItem(Item item) {
        if (item == null) {
            System.out.println("No such item.");
            return;
        }

        if (!inventory.contains(item)) {
            System.out.println("You don't have that item.");
            return;
        }

        item.applyEffect(this);

        if ("potion".equalsIgnoreCase(item.getType())) {
            inventory.remove(item);
        } else if ("weapon".equalsIgnoreCase(item.getType())) {
            currentItem = item;
            System.out.println("You equipped " + item.getName());
        }
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
        if (heal <= 0) {
            return;
        }
        health += heal;
    }

    public void attack(Enemy enemy) {
        if (enemy == null || !enemy.isAlive()) {
            System.out.println("There is no enemy to attack.");
            return;
        }
        int damage = 10;
        if (currentItem != null && "weapon".equalsIgnoreCase(currentItem.getType())) {
            damage += currentItem.getEffect();
        }

        System.out.printf("%s attacks %s for %d damage%n", name, enemy.getType(), damage);
        enemy.takeDamage(damage);
    }

    public int getHealth() {
        return health;
    }
}
