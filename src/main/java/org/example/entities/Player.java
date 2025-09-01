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
        System.out.println(name + " attacks " + enemy.getName() + " for " + damage + " damage!" +
                (equippedWeapon != null ? " (" + getBaseDamage() + " + " + equippedWeapon.getDamageBonus() + ")" : ""));
        enemy.takeDamage(damage);
        System.out.println(enemy.getName() + " now has " + enemy.getHp() + " HP left.");
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        System.out.println(name + " equips " + weapon.getName() + " (+" + weapon.getDamageBonus() + " damage)!");
    }

    public void pickUpItem(Item item) {
        inventory.addItem(item);
    }

    public void useItem(int index) {
        Item item = inventory.getItem(index);
        if (item instanceof Weapon weapon) {
            equipWeapon(weapon);
        } else {
            inventory.useItem(index, this);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }
}
