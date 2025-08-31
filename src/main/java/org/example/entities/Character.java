package org.example.entities;

public abstract class Character {
    protected String name;
    protected int maxHP;
    protected int hp;
    protected int baseDamage;
    protected Position position;

    public Character(String name, int maxHP, int baseDamage, Position position) {
        this.name = name;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.baseDamage = baseDamage;
        this.position = position;
    }
    public String getName() {
        return name;
    }
    public int getMaxHP() {
        return maxHP;
    }
    public int getHp() {
        return hp;
    }
    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position p) {
        this.position = p;
    }
    public boolean isAlive() {
        return hp > 0;
    }
    public void heal (int amount) {
        hp = Math.min(maxHP, hp + Math.max(0, amount));
    }
    public void takeDamage(int dmg) { hp = Math.max(0, hp - Math.max(0, dmg));
    }
}
