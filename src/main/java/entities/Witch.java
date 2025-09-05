package entities;

import utils.Position;

public class Witch extends Enemy {

    public Witch(int health, int damage, Position position) {
        super("Witch", health, damage, position);
    }

    @Override
    public void attack(Player player) {
        System.out.println("The witch spawns mini pigs that attack you");
        player.takeDamage(getDamage());
    }
}
