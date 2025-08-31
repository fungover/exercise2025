package org.example.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private int defense = 0;
    private Position position;
    private List<Item> inventory;
    private Map<EquipmentSlot, Equippable> equippedItems;

    public Player(String name, int maxHealth, Position startPosition) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = 10; // Default damage
        this.position = startPosition;
        this.inventory = new ArrayList<>();
        this.equippedItems = new HashMap<>();
    }

    public void attack(Enemy enemy) {
        enemy.takeDamage(damage);
        System.out.println(">You attack " + enemy.getName() + " for " + damage + " damage!");
    }

    public void takeDamage(int actualDamage, int originalDamage) {
        this.health = Math.max(0, health - actualDamage);

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
        if (item != null && item instanceof Usable usable) {
            usable.use(this);
            inventory.remove(item);
        }
    }

    public void equipItem(Equippable equipment) {

        if (equippedItems.containsKey(equipment.getSlot())) {
            unequipItem(equipment.getSlot());
        }

        equippedItems.put(equipment.getSlot(), equipment);
        equipment.equip(this);
    }

    public Equippable unequipItem(EquipmentSlot slot) {
        Equippable item = equippedItems.remove(slot);
        if (item != null) {
            item.unequip(this);
        }
        return item;
    }

    public Equippable getEquippedItem(EquipmentSlot slot) {
        return equippedItems.get(slot);
    }

    public Map<EquipmentSlot, Equippable> getEquippedItems() {
        return new HashMap<>(equippedItems);
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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isDead() {
        return health <= 0;
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

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    @Override
    public String toString() {
        return name + " [Health: " + health + "/" + maxHealth + ", Damage: " + damage + ", Defense: " + defense + ", Position: " + position + "]";
    }

}
