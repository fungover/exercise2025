package org.example.entities.enemies;

//fiendens typ, hälsa, damage, position (använd arv för olika fiendetyper).
public class Enemy {
    String type;
    int health;
    int damage;
    int row;
    int col;

    public Enemy(String type, int health, int damage, int row, int col) {
        this.type = type;
        this.damage = damage;
        this.health = health;
        this.row = row;
        this.col = col;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void takeDamage(int amount) {
        this.health -= amount;
        if (health <= 0) {
            health = 0;
        }
    }

    public boolean isAlive() {
        if (health > 0) {
            return true;
        } else {
            return false;
        }
    }


}
