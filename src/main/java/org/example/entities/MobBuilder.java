package org.example.entities;

public class MobBuilder {
    private String name;
    private Health health = new Health(10);
    private int strength = 10;
    private int x = 0;
    private int y = 0;

    public MobBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MobBuilder setHealth(Health health) {
        this.health = health;
        return this;
    }

    public MobBuilder setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public MobBuilder setX(int x) {
        this.x = x;
        return this;
    }

    public MobBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public MobBuilder setPos(Pos pos) {
        this.x = pos.x();
        this.y = pos.y();
        return this;
    }

    public Mob createMob() {
        return new Mob(name, health, strength, x, y);
    }
}