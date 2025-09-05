package Entities;

import Entities.Player;
public abstract class Character {
    protected String name;
    protected int health;
    protected int attack;
    protected int x;
    protected int y;

    public Character(String name, int health, int attack, int x, int y) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void attack(Character target) {
        int dmg = getAttackPower();
        System.out.println(name + " attacks " + target.getName() + " for " + dmg + "!");
        target.takeDamage(dmg);
    }

    public int getAttackPower() {
        return this.attack;
    }


    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttack() { return attack; }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
