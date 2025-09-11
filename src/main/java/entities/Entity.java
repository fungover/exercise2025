package entities;

/**
 * Basklass för alla entiteter i spelet (spelare, fiender, etc.)
 */
public abstract class Entity {
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private int x, y; // Position på kartan

    public Entity(String name, int maxHealth, int damage) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Börjar vid full hälsa
        this.damage = damage;
        this.x = 0;
        this.y = 0;
    }

    // Abstract metod som alla subklasser måste implementera
    public abstract String getDescription();

    // Getters och setters
    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = Math.max(0, Math.min(health, maxHealth));
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Vanliga metoder
    public boolean isAlive() {
        return currentHealth > 0;
    }

    public void takeDamage(int damage) {
        setCurrentHealth(currentHealth - damage);
    }

    public void heal(int amount) {
        setCurrentHealth(currentHealth + amount);
    }
}