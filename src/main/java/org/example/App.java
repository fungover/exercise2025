package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;

public class App {
    public static void main(String[] args) {


        Player player = new Player("Hero", 100, new Position(1,1));
        Dungeon dungeon = new Dungeon(8, 10);

        System.out.println("Current position (1,1): " + dungeon.getTile(1, 1).getType());
        System.out.println("North of player (1, 0): " + dungeon.getTile(1, 0).getType());

        dungeon.printMap();

        boolean moved = player.moveNorth(dungeon);
        System.out.println("Move successful: " + moved);
        System.out.println("after north: " + player.getPosition());

        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        System.out.println("After east: " + player.getPosition());

        moved = player.moveWest(dungeon);
        System.out.println("Move successful: " + moved);
        System.out.println("After west" + player.getPosition());

    }
}
