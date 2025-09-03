package org.example.entities;

import java.util.ArrayList;
import java.util.List;

// Player som har namn, hp, attackDamage, defense och en position på kartan (x, y)
public class Player {
    private final String name;
    private int hp;
    private int x;
    private int y;
    private int level;
    private int attackDamage;
    private int defense;

    private Item equippedWeapon;
    private Item equippedArmor;

    private final List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.hp = 100;
        this.x = 1;
        this.y = 1;
        this.level = 1;
        this.attackDamage = 20;
        this.defense = 0;
        this.equippedWeapon = new Item("Bare Hands", Item.ItemType.WEAPON, 0);
        this.equippedArmor = new Item("Cloth Armor", Item.ItemType.ARMOR, 0);
    }

    // === Inventory ===
    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void printInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty");
        } else {
            System.out.println("=== Inventory ===");
            for (Item item : inventory) {
                System.out.println("- " + item.getName() + " (" + item.getType() + ")");
            }
        }

        System.out.println("\nEquipped:");
        System.out.println("Weapon: " + (equippedWeapon != null ? equippedWeapon.getName() : "None"));
        System.out.println("Armor : " + (equippedArmor != null ? equippedArmor.getName() : "None"));
    }

    // Använd ett item från inventory
    public void useItem(Item item) {
        switch (item.getType()) {
            case CONSUMABLE -> {
                setHp(getHp() + item.getValue());
                inventory.remove(item);
                System.out.println(getName() + " used " + item.getName() + " and healed " + item.getValue() + " HP!");
            }
            case WEAPON -> {
                if (equippedWeapon != null && !"Bare Hands".equals(equippedWeapon.getName())) {
                    inventory.add(equippedWeapon); // Lägg tillbaka gamla vapnet
                    System.out.println("Unequipped " + equippedWeapon.getName() + " and returned it to inventory.");
                }
                equippedWeapon = item;
                setAttackDamage(20 + item.getValue()); // Standard damage + vapnets värde
                inventory.remove(item);
                System.out.println(getName() + " equipped weapon: " + item.getName());
            }
            case ARMOR -> {
                if (equippedArmor != null && !"Cloth Armor".equals(equippedArmor.getName())) {
                    inventory.add(equippedArmor); // Lägg tillbaka gamla rustningen
                    System.out.println("Unequipped " + equippedArmor.getName() + " and returned it to inventory.");
                }
                equippedArmor = item;
                setDefense(item.getValue());
                inventory.remove(item);
                System.out.println(getName() + " equipped armor: " + item.getName());
            }
        }
    }

    // === Stats ===
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, 100); // HP kan inte gå över 100
    }

    public int getLevel() {
        return level;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }

    // === Position ===
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // === Combat ===
    public void takeDamage(int damage) {
        int reduced = Math.max(0, damage - defense);
        this.hp -= reduced;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }

    // === Equipment ===
    public Item getEquippedWeapon() {
        return equippedWeapon;
    }

    public Item getEquippedArmor() {
        return equippedArmor;
    }
}
