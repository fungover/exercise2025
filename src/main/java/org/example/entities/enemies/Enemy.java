package org.example.entities.enemies;

public abstract class Enemy {
    private final String name;
    private final int maxHealth;
    private final int baseDamage;
    private int health;

    protected Enemy(String name, int maxHealth, int baseDamage) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must be non-empty");
        if (maxHealth <= 0) throw new IllegalArgumentException("maxHealth must be > 0");
        if (baseDamage < 0) throw new IllegalArgumentException("baseDamage must be >= 0");
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseDamage = baseDamage;
    }

    public String name() {
        return name;
    }

    public int maxHealth() {
        return maxHealth;
    }

    public int health() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int attackDamage() {
        return baseDamage;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) return;
        health = Math.max(0, health - amount);
    }

    /**
     * Shown to the player when combat starts.
     */
    public abstract String archetype();
}
