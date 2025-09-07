package entities;

import utils.Position;

public class Goblin extends Enemy {

    public Goblin(int health, int damage, Position position) {
        super("Goblin", health, damage, position);
    }

    @Override
    public void attack(Player player) {
        System.out.println("The Goblin cuts you with its rusty dagger!");
        player.takeDamage(getDamage());
    }
}
