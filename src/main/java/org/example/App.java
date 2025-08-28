package org.example;

import org.example.entities.Player;
import org.example.entities.Position;
import org.example.map.Dungeon;
import org.example.service.Direction;
import org.example.service.MovementService;

public class App {
    public static void main(String[] args) {


        Player player = new Player("Hero", 100, new Position(1,1));
        Dungeon dungeon = new Dungeon(8, 10);
        MovementService movementService = new MovementService();

        System.out.println("STARTING HERE");
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("\nMOVE EAST");
        boolean moved = movementService.movePlayer(player, Direction.EAST, dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("\nMOVE EAST");
        moved = movementService.movePlayer(player, Direction.EAST, dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);

        System.out.println("\nMOVE SOUTH");
        moved = movementService.movePlayer(player, Direction.SOUTH, dungeon);
        System.out.println("Move successful: " + moved);
        dungeon.printMapWithPlayer(player);
        System.out.println(player);




    }
}
