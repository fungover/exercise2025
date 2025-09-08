package dev.ronja.dungeon.entities;

public class MotherInLaw implements Enemy, PsychologicalAttack {

    private final String name = "Mother-in-Law";
    private int hp = 70;
    private final int damage = 5;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHp() {
        return hp;
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
        System.out.println(name + " Nags " + player.getName() + " and delivers " + damage + " in damage! ");
        player.takeDamage(damage);
    }

    @Override
    public void talkToDeath(Player player) {
        System.out.println(name + " Unleashes an endless stream of mental clutter..."
                + player.getName() + " loses " + damage + " in HP from mental exhaustion!");
        player.takeDamage(damage * 2);
    }
}
