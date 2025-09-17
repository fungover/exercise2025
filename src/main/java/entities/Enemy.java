package entities;

import interfaces.Combatable;
import interfaces.Displayable;
import interfaces.Positionable;
import utils.Constants;
import utils.RandomGenerator;

/**
 * Base class for all enemies in the game
 */
public abstract class Enemy extends Entity implements Combatable, Displayable, Positionable {
    private boolean isAlive;
    private char displaySymbol;
    private String attackMessage;
    private String deathMessage;

    public Enemy(String name, int maxHealth, int damage, char displaySymbol) {
        super(name, maxHealth, damage);
        this.isAlive = true;
        this.displaySymbol = displaySymbol;
        this.attackMessage = name + " attackerar dig!";
        this.deathMessage = name + " faller till marken besegrad.";
    }

    // Base class for all enemies in the game
    public Enemy(String name, int maxHealth, int damage, char displaySymbol,
                 String attackMessage, String deathMessage) {
        super(name, maxHealth, damage);
        this.isAlive = true;
        this.displaySymbol = displaySymbol;
        this.attackMessage = attackMessage;
        this.deathMessage = deathMessage;
    }

    // Abstract methods that subclasses must implement
    public abstract String getSpecialAttack(Player player);
    public abstract String getIdleMessage();

    // Implements abstract method from Entity
    @Override
    public String getDescription() {
        if (!isAlive()) {
            return "En besegrad " + getName().toLowerCase() + " ligger livlös på marken.";
        }
        return "En hotfull " + getName().toLowerCase() + " med " +
                getCurrentHealth() + "/" + getMaxHealth() + " hälsa stirrar på dig.";
    }

    // Attacks the player
    public String attack(Player player) {
        if (!isAlive()) {
            return getName() + " är redan besegrad och kan inte attackera.";
        }

        // Use Constants for Chance
        if (RandomGenerator.rollPercent(Constants.SPECIAL_ATTACK_CHANCE)) {
            return getSpecialAttack(player);
        }

        // Normal attack
        player.takeDamage(getDamage());
        return attackMessage + " Du tar " + getDamage() + " skada!";
    }

    // Take damage and check if the enemy dies
    public String takeDamageAndCheck(int damage) {
        takeDamage(damage);

        if (getCurrentHealth() <= 0 && isAlive) {
            isAlive = false;
            return deathMessage;
        }

        return getName() + " tar " + damage + " skada och har nu " +
                getCurrentHealth() + " hälsa kvar.";
    }

    // Override isAlive from Entity
    @Override
    public boolean isAlive() {
        return isAlive && getCurrentHealth() > 0;
    }

    // Implement Combatable interface
    public int attack() {
        if (!isAlive()) {
            return 0;
        }
        return getDamage();
    }

    // Implement Displayable interface
    @Override
    public char getSymbol() {
        return isAlive() ? displaySymbol : Constants.DEAD_ENEMY_SYMBOL;
    }

    public String getAttackMessage() {
        return attackMessage;
    }

    public String getDeathMessage() {
        return deathMessage;
    }

    // Method of obtaining information about the enemy
    public String examine() {
        if (!isAlive()) {
            return "En död " + getName().toLowerCase() + ". " + getDeathMessage();
        }
        return getDescription() + " " + getIdleMessage();
    }
}