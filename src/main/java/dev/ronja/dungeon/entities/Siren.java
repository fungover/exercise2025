package dev.ronja.dungeon.entities;

public class Siren implements Enemy {
    private final String name = "Siren";
    private final int maxHp = 50;
    private final int damage = 8;
    private int hp = maxHp;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public int getMaxHp() {
        return maxHp;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    @Override
    public boolean isAlive() {
        return hp > 0;
    }


    @Override
    public void takeDamage(int amount) {
        hp = Math.max(0, hp - Math.max(0, amount));
    }


    @Override
    public void attack(Player player) {
        System.out.println(name + " hums a haunting melody and drains "
        + damage + " HP from " + player.getName() + "!");
        player.takeDamage(damage);
    }

    @Override
    public String toString() {
        return getName() + " (HP: " + getHp() + ", DMG: " + getDamage() + ")";
    }

}