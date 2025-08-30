package org.example.entities;

public abstract class Enemy {
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private Position position;

    public Enemy(String name, int health, int damage, Position position) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.damage = damage;
        this.position = position;
    }

    public abstract void attack(Player player);

    public abstract String getAttackMessage();

    protected void setHealth(int health) {
        this.health = Math.max(0, Math.min(maxHealth, health));
    }

    protected void modifyDamage(int newDamage) {
        this.damage = Math.max(0, newDamage);
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        System.out.println(name + " takes " + damage + " damage!");
    }

    public boolean isDead() {
        return health <= 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return name + " [Health: " + health + "/" + maxHealth + "]";
    }

}
