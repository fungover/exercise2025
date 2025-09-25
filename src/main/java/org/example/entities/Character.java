package org.example.entities;

public abstract class  Character {
    protected String name;
    protected int health;
    protected int x;
    protected int y;

    public Character(String name, int health, int x, int y) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
    }
    //Polymorphism: All characters have ability to give damage
    public abstract int getAttackDamage();

    // Common logic
    public boolean isAlive() {
        return health > 0;
    }

    public void receiveDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    // Getter & setter for position
    public String getName() {return name;}
    public int getHealth() {return health;}
    public void setHealth(int health) {this.health = Math.max(0, health);}
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public abstract void takeTurn();

}
