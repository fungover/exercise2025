package entities;

public class Enemy {
    private String name;
    private int health;
    private int damage;

    public Enemy(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage(){
        return damage;
    }

    public void takeDamage(int dmg) {
        this.health -= dmg;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
