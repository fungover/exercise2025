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

    public abstract void attack();

}
