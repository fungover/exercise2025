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
    // Getter & setter for position
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
