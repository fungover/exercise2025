package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;

public class App {
    public static void main(String[] args) {


        Player player = new Player("Hero", 100, new Position(1,1));
        Dungeon dungeon = new Dungeon(8, 10);

        System.out.println("Starting position");
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving east");
        boolean moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving south");
        moved = player.moveSouth(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Try moving into wall (East)");
        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving south");
        moved = player.moveSouth(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving south");
        moved = player.moveSouth(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving south");
        moved = player.moveSouth(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving east");
        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving east");
        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving east");
        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("Moving east");
        moved = player.moveEast(dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);



    }
}
