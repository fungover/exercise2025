package Entities;

import Weapons.Weapon;
import java.util.ArrayList;
import java.util.List;

public class Player extends Character {
    private List<Weapon> inventory = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private Weapon equipped;

    public Player(String name, int health, int attack, int x, int y) {
        super(name, health, attack, x, y);
    }
    public void heal(int amount) {
        health += amount;
        System.out.println(name + " healed " + amount + " HP. Current HP: " + health);
    }
    public void addWeapon(Weapon w) {
        inventory.add(w);
        if (equipped == null) equipped = w;
        System.out.println("Picked up " + w.getName());
    }
    public void equip(Weapon w) {
        if (inventory.contains(w)) {
            equipped = w;
            System.out.println("Equipped " + w.getName());
        }
    }
    public void addItem(Item item) {
        items.add(item);
        System.out.println("Picked up: " + item);
    }

    public void printItems() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        System.out.println("Items:");
        for (Item i : items) {
            System.out.println(" - " + i);
        }
    }

    @Override
    public int getAttackPower() {
        int weaponDamage = (equipped != null) ? equipped.getDamage() : 0;
        return this.attack + weaponDamage;
    }


    public void useFirstPotion() {
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            if (it.getType() == ItemType.POTION) {
                heal(it.getValue());
                items.remove(i);
                System.out.println("You used a potion and healed " + it.getValue() + " HP!");
                return;
            }
        }
        System.out.println("You don't have any potions!");
    }

    public void printInventory() {
        boolean empty = true;

        if (!items.isEmpty()) {
            System.out.println("Items:");
            for (Item i : items) {
                System.out.println(" - " + i);
            }
            empty = false;
        }

        if (!inventory.isEmpty()) {
            System.out.println("Weapons:");
            for (Weapon w : inventory) {
                System.out.println(" - " + w);
            }
            empty = false;
        }

        if (empty) {
            System.out.println("Inventory is empty.");
        }
    }
}
