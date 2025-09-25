package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int x, y; //position
    private int baseDamage;
    private int weaponDamage;
    private List<Item> inventory;

    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.baseDamage = 10;
        this.weaponDamage = 0;
        this.inventory = new ArrayList<>();

    }

    //getters

    public String getName() {return name;}

    public int getHealth() {return health;}

    public int getMaxHealth() {return maxHealth;}

    public int getX() {return x;}

    public int getY() {return y;}

    public int getBaseDamage() {return baseDamage;}

    public int getWeaponDamage() {return weaponDamage;}

    public int getDamage() {return baseDamage + weaponDamage;}

    public List<Item> getInventory() {return inventory;}

    //setters
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setHealth(int amount) {
        this.health = Math.max(0, Math.min(maxHealth, amount));
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
        //if it's a weapon, equip straight away
        if (item instanceof Weapon) {
            Weapon weapon = (Weapon) item;
            weaponDamage += weapon.getDamageBonus();
            System.out.println("You equipped the " + weapon.getName() +
              "! Your baseDamage increased by " + weapon.getDamageBonus() + "!");

        }
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
        System.out.println("Damage:  " + baseDamage);
        if (weaponDamage > 0) {
            System.out.println("Weapon Damage: +" + weaponDamage);
            System.out.println("Total Damage: " + getDamage());
        }
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void displayInventory() {
        System.out.println("=== Inventory ===");
        if (inventory.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (int i = 0; i < inventory.size(); i++) {
                System.out.println((i + 1) + ", " + inventory.get(i)
                                                             .getName() + " - " +
                  inventory.get(i)
                           .getDescription());
            }
        }
    }

}
