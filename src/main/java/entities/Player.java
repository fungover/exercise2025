package entities;

import utils.Position;

import java.util.ArrayList;

public class Player {
    private String name = "Unknown Slayer";
    private int health = 100;
    private Position position;
    private ArrayList<Item> inventory = new ArrayList<>()

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (isAlive()) {
            System.out.printf("You lost %d in health, now your hp is: %d\n", damage, health);
        } else {
            System.out.println("You were killed");
        }
    }

    public void attack(Enemy enemy) {
        //TODO
    }

}
