package dev.ronja.dungeon.entities;

/**
 * This Class represents a player in the dungeon game
 * <p>Player has</p>
 * <ul>
 *     <li> a name (String) </li>
 *     <li> current HP (int) </li>
 *     <li> maximum HP </li>
 * </ul>
 *
 * <p>The class provides methods to:</p>
 * <ul>
 *     <li>take damage (reduces HP)</li>
 *     <li>heal (increases HP, up to max)</li>
 *     <li>check if the player is still alive</li>
 * </ul>
 *
 **/

public class Player {
    private final String name;
    // Players current Health Point
    private int hp;
    private final int maxHp;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.maxHp = 100;
        this.hp = maxHp;
    }

    // Getters - Info about player
    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    // Check if player is alive
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Sets the players HP directly, but restricted between 0 and maxHp
     *
     * @param newHp the new HP value
     **/
    public void setHp(int newHp) {
        if (newHp < 0) {
            this.hp = 0; // Never negative
        } else if (newHp > maxHp) {
            this.hp = maxHp;
        } else {
            this.hp = newHp;
        }
    }

    /**
     * Method: Applies damage to the player
     *
     * @param amount how much HP to subtract (must be >=0 )
     *               if amount is larger than current HP, HP is set to 0
     **/
    public void takeDamage(int amount) {
        setHp(this.hp - amount); //Reusing setHp
        if (!isAlive()) {
            System.out.println(name + " has fallen in battle ! ");
        }
        System.out.println(name + " Took " + amount + " damage! HP: " + hp + " / " + maxHp);
    }

    /**
     * Method: Heals the player
     *
     * @param amount how much HP to restore (must be >= 0)
     *               healing cannot raise HP above maxHp
     **/
    public void heal(int amount) {
        setHp(this.hp + amount); //Reusing setHp
        System.out.println(name + " Healed for " + amount + " HP: " + hp + " / " + maxHp);
    }

    @Override
    public String toString() {
        return name + " HP: " + hp + " / " + maxHp + " / ";
    }
}

