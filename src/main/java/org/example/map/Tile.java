package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Player;
import org.example.service.Combat;

public class Tile {
    private TileEnum type;
    private final Enemy enemy;

    public Tile(TileEnum type, Enemy enemy) {
        this.type = type;
        this.enemy = enemy;
    }

    public void onEnter(Player player, Tile tile) {
        switch(type) {
            case ENEMY:
                if (enemy != null) {
                    System.out.println("You stepped up on a " + enemy.getType() + " prepare to fight!");
                    Combat.Fight(player, enemy, tile);
                } else {
                    System.out.println("Found enemy without a type");
                }

                break;
            case CHEST:
                System.out.println("Found tile with chest");
                break;
            case DOOR:
                System.out.println("Found tile with door");
                break;
            default:
                System.out.println("Found empty tile");

        }
    }

    public void setType(TileEnum type) {
        this.type = type;
    }
}
