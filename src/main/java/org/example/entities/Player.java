package org.example.entities;


public class Player extends Character {
    private Inventory inventory;
    private Weapon equippedWeapon;

    public Player(String name, int maxHP, int baseDamage, Position position) {
        super(name, maxHP, baseDamage, position);
        this.inventory = new Inventory();
    }

    public int getDamage() {
        int bonus = (equippedWeapon != null) ? equippedWeapon.getDamageBonus() : 0;
        return getBaseDamage() + bonus;
    }

    public void attack(Enemy enemy) {
        int damage = getDamage();
        System.out.println(getName() + " attacks " + enemy.getName() + " for " + damage + " damage!" +
                (equippedWeapon != null ? " (" + getBaseDamage() + " + " + equippedWeapon.getDamageBonus() + ")" : ""));
        enemy.takeDamage(damage);
        System.out.println(enemy.getName() + " now has " + enemy.getHp() + " HP left.");
    }

    public Inventory getInventory() {
        return inventory;
    }
}
