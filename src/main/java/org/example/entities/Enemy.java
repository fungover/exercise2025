package org.example.entities;

class Enemy implements Health {
    private int health;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}

