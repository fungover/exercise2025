package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int x;
    private int y;
    private int health;
    private int damage;
    private boolean isAlive;
    private List<Item> inventory;
    private Item equippedWeapon;

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.health = 100;
        this.damage = 10;
        this.isAlive = true;
        this.inventory = new ArrayList<>();
        this.equippedWeapon = null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveUp() {
        y--;
    }

    public void moveDown() {
        y++;
    }

    public void moveLeft() {
        x--;
    }

    public void moveRight() {
        x++;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // --Combat methods--
    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            this.isAlive = false;
        }
    }

    public int attack() {
        return this.damage;
    }

    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) {
            this.health = 100;
        }
    }

    // --New getters--
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return isAlive;
    }

    // --Inventory methods--
    public void addItem(Item item) {
        inventory.add(item);

        // -- Auto-equip weapons if none equipped --
        if (item.isWeapon() && equippedWeapon == null) {
            equippedWeapon = item;
        }
    }

    public void removeItem(Item item) {
        inventory.remove(item);
        if (item.equals(equippedWeapon)) {
            equippedWeapon = null;
        }
    }

    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public void equipWeapon(Item weapon) {
        if (weapon.isWeapon() && inventory.contains(weapon)) {
            this.equippedWeapon = weapon;
        }
    }

    public boolean hasPotion() {
        return inventory.stream().anyMatch(Item::isPotion);
    }

    public Item getPotion() {
        return inventory.stream()
                .filter(Item::isPotion)
                .findFirst()
                .orElse(null);
    }

    public boolean hasKey() {
        return inventory.stream().anyMatch(Item::isKey);
    }

    public Item getKey() {
        return inventory.stream()
                .filter(Item::isKey)
                .findFirst()
                .orElse(null);
    }

    public int getInventorySize() {
        return inventory.size();
    }

    public String getInventoryDisplay() {
        if (inventory.isEmpty()) {
            return "🎒Inventory: Empty";
        }

        StringBuilder sb = new StringBuilder("🎒Inventory: ");
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            sb.append(item.getName());
            if (i < inventory.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}