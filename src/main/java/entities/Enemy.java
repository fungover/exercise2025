package entities;

import interfaces.Combatable;
import interfaces.Displayable;
import interfaces.Positionable;
import utils.Constants;
import utils.RandomGenerator;

/**
 * Basklass för alla fiender i spelet
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

    // Konstruktor med anpassade meddelanden
    public Enemy(String name, int maxHealth, int damage, char displaySymbol,
                 String attackMessage, String deathMessage) {
        super(name, maxHealth, damage);
        this.isAlive = true;
        this.displaySymbol = displaySymbol;
        this.attackMessage = attackMessage;
        this.deathMessage = deathMessage;
    }

    // Abstract metoder som subklasser måste implementera
    public abstract String getSpecialAttack(Player player);
    public abstract String getIdleMessage();

    // Implementerar abstract metod från Entity
    @Override
    public String getDescription() {
        if (!isAlive()) {
            return "En besegrad " + getName().toLowerCase() + " ligger livlös på marken.";
        }
        return "En hotfull " + getName().toLowerCase() + " med " +
                getCurrentHealth() + "/" + getMaxHealth() + " hälsa stirrar på dig.";
    }

    // Attackerar spelaren
    public String attack(Player player) {
        if (!isAlive()) {
            return getName() + " är redan besegrad och kan inte attackera.";
        }

        // Använd Constants för chans
        if (RandomGenerator.rollPercent(Constants.SPECIAL_ATTACK_CHANCE)) {
            return getSpecialAttack(player);
        }

        // Normal attack
        player.takeDamage(getDamage());
        return attackMessage + " Du tar " + getDamage() + " skada!";
    }

    // Ta skada och kolla om fienden dör
    public String takeDamageAndCheck(int damage) {
        takeDamage(damage);

        if (getCurrentHealth() <= 0 && isAlive) {
            isAlive = false;
            return deathMessage;
        }

        return getName() + " tar " + damage + " skada och har nu " +
                getCurrentHealth() + " hälsa kvar.";
    }

    // Override isAlive från Entity
    @Override
    public boolean isAlive() {
        return isAlive && getCurrentHealth() > 0;
    }

    // Implementerar Combatable interface
    public int attack() {
        if (!isAlive()) {
            return 0;
        }
        return getDamage();
    }

    // Implementerar Displayable interface
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

    // Metod för att få information om fienden
    public String examine() {
        if (!isAlive()) {
            return "En död " + getName().toLowerCase() + ". " + getDeathMessage();
        }
        return getDescription() + " " + getIdleMessage();
    }
}