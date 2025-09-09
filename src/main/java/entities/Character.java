package entities;

public abstract class Character {

    private int health;
    private int attack;

    public Character(int health, int attack){
        this.health = health;
        this.attack = attack;
    }

    public int getHealth() { return health; }
    public int getAttack() { return attack; }

}
