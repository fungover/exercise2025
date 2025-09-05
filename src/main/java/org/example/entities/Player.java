package org.example.entities;

import java.util.ArrayList;
import java.util.List;

// Player som har namn, hp, attackDamage, defense och en position på kartan (x, y)
public class Player {
    private final String name;
    private int hp;
    private int maxHp;
    private int x;
    private int y;
    private int level;
    private int attackDamage;
    private int defense;
    private int xp;
    private int xpToLevelUp;

    private Item equippedWeapon;
    private Item equippedArmor;

    private final List<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.maxHp = 100;
        this.hp = maxHp;
        this.x = 1;
        this.y = 1;
        this.level = 1;
        this.attackDamage = 20;
        this.defense = 0;
        this.xp = 0;
        this.xpToLevelUp = 100;
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
                String stats = "";
                switch (item.getType()) {
                    case WEAPON -> stats = "Damage +" + item.getValue();
                    case ARMOR -> stats = "Defense +" + item.getValue();
                    case CONSUMABLE -> stats = "Heals " + item.getValue() + " HP";
                }
                System.out.println("- " + item.getName() + " (" + item.getType() + ", " + stats + ")");
            }
        }

        System.out.println("\nEquipped:");
        if (equippedWeapon != null) {
            System.out.println("Weapon: " + equippedWeapon.getName() + " (Damage +" + equippedWeapon.getValue() + ")");
        } else {
            System.out.println("Weapon: None");
        }

        if (equippedArmor != null) {
            System.out.println("Armor : " + equippedArmor.getName() + " (Defense +" + equippedArmor.getValue() + ")");
        } else {
            System.out.println("Armor : None");
        }
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

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp);
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
    public int takeDamage(int damage) {
        int reduced = Math.max(0, damage - defense);
        this.hp -= reduced;
        if (hp < 0) {
            hp = 0;
        }
        return reduced;
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

    // === XP ===
    public void gainXp(int amount) {
        xp += amount;
        System.out.println(getName() + " gained " + amount + " XP!");
        if (xp >= xpToLevelUp) {
            levelUp();
        }
    }

    private void levelUp() {
        xp -= xpToLevelUp;
        level++;
        xpToLevelUp += 50;
        attackDamage += 5;
        defense += 2;
        maxHp += 20;
        hp = maxHp;
        System.out.println("🎉 " + getName() + " leveled up to level " + level + "!");
        System.out.println("HP increased to " + maxHp + ", Attack +5, Defense +2");
    }

    public int getXp() {
        return xp;
    }

    public int getXpToNextLevel() {
        return xpToLevelUp;
    }
}
