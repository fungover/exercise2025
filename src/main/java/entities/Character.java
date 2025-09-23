package entities;

import map.Dungeon;

public abstract class Character {

    private int health;
    private int damage;
    private int x;
    private int y;

    public Character(int x, int y, int health, int damage) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.damage = damage;
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return this.damage;
    }

    public void increaseDamage(int amount) {
        this.damage += amount;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy, Dungeon dungeon) {
        int newX = getX() + dx;
        int newY = getY() + dy;

        if (newX < 0 || newX >= dungeon.getWidth() || newY < 0 || newY >= dungeon.getHeight()) {
            System.out.println("You can't move outside the dungeon!");
            return;
        }
        if (dungeon.isWalkable(newX, newY)) {
            setPosition(newX, newY);
        } else {
            System.out.println("You bump into a wall. Ouch!");
        }
    }

}
