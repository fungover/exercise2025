package dev.ronja.dungeon.entities;

/**
 * This abstract base class represents a generic enemy in the dungeon
 * <p>
 * Each enemy has a name, HP and a fixed damage value for its attacks
 * Class provides shared behavior for all enemy types
 * </p>
 * Subclasses (Siren, BlackWitch etc.) must extend this class to create specific types and custom traits
 **/
public abstract class Enemy {
    private final String name;
    private int hp;
    private final int damage;

    /**
     * Constructs a new enemy. Sets values, only available to subclasses - therefore protected, instead of private
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

    /**
     * Damage taken by enemy, negative values are ignored
     **/
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
        System.out.println(name + " took " + amount + " damage! " + " Remaining HP:" + hp);
    }

    /**
     * Attack performed on a player
     * <p>
     * Always deals the fixed {@code value} value.
     * </p>
     *
     * @param player is the target player to attack
     **/
    public void attack(Player player) {
        System.out.println(name + " attacks " + player.getName() + " for " + damage + " damage ");
        player.takeDamage(damage);
    }

    /// Returns a human-readable string
    @Override
    public String toString() {
        return name + " (HP: " + hp + ", DMG: " + damage + " ) ";
    }

}
