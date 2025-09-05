package entities;

import utils.Position;

public abstract class Enemy {
    private String type;
    private int health;
    private int damage;
    private Position position;

    public Enemy (String type, int health, int damage, Position position) {
        this.type = type;
        this.health = health;
        this.damage = damage;
        this.position = position;
    }

    public int getDamage() {
        return damage;
    }

    public abstract void attack(Player player);

    public void takeDamage(int damageAmount) {
        health -= damageAmount;
        if (!isAlive()) {
            System.out.println(type + " has been killed");
        }
    };

    public boolean isAlive() {
        return health > 0;
    }

}
