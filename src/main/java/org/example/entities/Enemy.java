package org.example.entities;

public abstract class Enemy {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int damage;
    protected int x, y;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.damage = damage;
    }

    //Getters

    public String getName() {return name;}

    public int getHealth() {return health;}

    public int getMaxHealth() {return maxHealth;}

    public int getDamage() {return damage;}

    public int getX() {return x;}

    public int getY() {return y;}


    //Setters

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    public boolean isAlive() {return health > 0;}

    //methods

    public abstract String getAttackMessage();

    public abstract String getDeathMessage();


}
