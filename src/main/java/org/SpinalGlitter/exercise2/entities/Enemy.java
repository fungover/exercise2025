package org.SpinalGlitter.exercise2.entities;

import org.SpinalGlitter.exercise2.utils.WorldObject;

public class Enemy implements WorldObject {
    private final String name;
    private int hp;
    private int damage;
    private final Position position;

    public Enemy(String name, int hp, int damage, Position position) {
        this.name = name;
        this.hp = hp;
        this.damage = damage;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    int getDamage() {
        return damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getSymbol() {
        return switch (name) {
            case "Goblin" -> "👺";
            case "Skeleton" -> "💀";
            default -> "❓";
        };
    }
}

// Type
