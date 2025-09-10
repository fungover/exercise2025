package entities;

public abstract class Character {

    private int health;
    private final int damage;
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

    public int attack() {
        return this.damage;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void move(int dx, int dy){
        this.x += dx;
        this.y += dy;
    }

}
