package entities;

/**
 * Basklass för alla fiender i spelet
 */
public abstract class Enemy extends Entity {
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

        // 30% chans för specialattack
        if (Math.random() < 0.3) {
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

    // Getters
    public char getDisplaySymbol() {
        return displaySymbol;
    }

    public char getSymbol() {
        return isAlive() ? displaySymbol : 'x'; // x för död fiende
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
