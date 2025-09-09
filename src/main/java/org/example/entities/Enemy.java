package org.example.entities;

public class Enemy {
    private String name;
    private String description;
    private int health;
    private int maxHealth;
    private int attackDamage;
    private boolean isAlive;

    // Constructor
    public Enemy(String name, String description, int health, int attackDamage) {
        if (name == null || description == null) {
            throw new IllegalArgumentException("Name and description cannot be null");
        }
        if (health <= 0) throw new IllegalArgumentException("Health must be > 0");
        if (attackDamage < 0) throw new IllegalArgumentException("attackDamage must be >= 0");
        this.name = name;
        this.description = description;
        this.maxHealth = health;
        this.health = health;
        this.attackDamage = attackDamage;
        this.isAlive = true;
    }

    // Combat methods
    public int attack() {
        if (isAlive()) {
            return Math.max(0, attackDamage);
        }
        return 0;
    }

    public void takeDamage(int damage) {
        int dmg = Math.max(0, damage);
        this.health = Math.max(0, this.health - dmg);
        if (this.health == 0) {
            this.isAlive = false;
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    // Display methods
    public void showStats() {
        System.out.println("=== " + name + " ===");
        System.out.println("Description: " + description);
        System.out.println("Health: " + health + "/" + maxHealth);
        System.out.println("Attack: " + attackDamage);
        System.out.println("Alive: " + (isAlive() ? "Alive" : "Dead"));
    }

    //Getters
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getHealth() {
        return health;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    @Override
    public String toString() {
        return name + " (" + health + "/" + maxHealth + " HP)";
    }
}
