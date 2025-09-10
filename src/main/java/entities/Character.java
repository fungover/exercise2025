package entities;

public abstract class Character {

    private int health;
    private final int damage;

    public Character(int health, int attack){
        this.health = health;
        this.damage = attack;
    }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int attack() { return damage; }

}
