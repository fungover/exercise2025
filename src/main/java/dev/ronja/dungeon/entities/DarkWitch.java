package dev.ronja.dungeon.entities;

public class DarkWitch implements Enemy {

    private final String name = "Dark Witch";
    private final int maxHp = 60;
    private final int damage = 10;
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
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    @Override
    public int getDamage() {
        return damage;
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
        System.out.println(name + " casts a Shadow curse on "
                + player.getName() + " and deals " + damage + " in dark damage! ");
        player.takeDamage(damage);
    }

}
