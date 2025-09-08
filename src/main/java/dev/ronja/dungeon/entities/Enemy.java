package dev.ronja.dungeon.entities;

public abstract class Enemy {
    private final String name;
    private int hp;
    private final int damage;

    /**
     * Constructor to set values, only available to subclasses - therefore protected, instead of private
     **/
    protected Enemy(String name, int hp, int damage) {
        this.name = name;
        this.hp = Math.max(0, hp);
        this.damage = Math.max(0, damage);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
        System.out.println(name + " took " + amount + " damage! " + " Remaining HP:" + hp);
    }

    public void attack(Player player) {
        System.out.println(name + " attacks " + player.getName() + " for " + damage + " damage ");
        player.takeDamage(damage);
    }

    @Override
    public String toString() {
        return name + " (HP: " + hp + ", DMG: " + damage + " ) ";
    }

}
