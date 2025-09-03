package org.example.entities;

// Player som har namn, hp och en position på kartan (x, y)
public class Player {
    private String name;
    private int hp;
    private int x;
    private int y;
    private int level;

    public Player(String name) {
        this.name = name;
        this.hp = 100;
        this.x = 1;
        this.y = 1;
        this.level = 1;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public void moveTo(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
