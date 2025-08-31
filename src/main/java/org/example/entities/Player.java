package org.example.entities;

public class Player extends Character {
    private Inventory inventory;

    public Player(String name, int maxHP, int baseDamage, Position position) {
        super(name, maxHP, baseDamage, position);
        this.inventory = new Inventory();
    }

    public void attack(Enemy enemy) {
        System.out.println(name + " attacks " + enemy.getName() + " for " + baseDamage + " damage!");
        enemy.takeDamage(baseDamage);
        System.out.println(enemy.getName() + " now has " + enemy.getHp() + " HP left.");
    }

    public void pickUpItem(Item item) {
        inventory.addItem(item);
    }

    public void useItem(int index) {
        inventory.useItem(index, this);
    }

    public void showInventory() {
        inventory.showItems();
    }
}
