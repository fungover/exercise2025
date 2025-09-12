package org.example.map;

import org.example.entities.Enemy;
import org.example.entities.Player;

public class Tile {
    private final TileEnum type;
    private final Enemy enemy;

    public Tile(TileEnum type, Enemy enemy) {
        this.type = type;
        this.enemy = enemy;
    }

    public void onEnter(Player player) {
        switch(type) {
            case ENEMY:
                if (enemy != null) {
                    System.out.println("Found " + enemy.getType() + " on tile");
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
}
