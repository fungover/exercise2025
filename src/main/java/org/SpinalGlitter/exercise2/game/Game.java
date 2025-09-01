package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.Player;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.entities.Position;
import java.util.Scanner;

public final class Game {

    static void main() {

        Player player = new Player("Hero");
        DungeonMap map = new DungeonMap(10, 6);
        Position newPos = null;

        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        System.out.println("Kommandon: help, quit");


        Scanner scanner = new Scanner(System.in);


        do {
            newPos = null;
            map.printMap(player.getPosition());
            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "help":  System.out.println("Available commands: help, quit, north, east, west, south"); break;

                case "quit":  System.out.println("Exiting the game. Goodbye!"); return;

                case "north": newPos = player.getPosition().getAdjacent(0, 1); break;

                case "south": newPos = player.getPosition().getAdjacent(0, -1);  break;

                case "east": newPos = player.getPosition().getAdjacent(1, 0); break;

                case "west": newPos = player.getPosition().getAdjacent(-1, 0);  break;

                default:  System.out.println("Unknown command. Type 'help' for a list of commands."); break;
            }

            if (newPos != null && map.canMoveTo(newPos)) {
                int dx = newPos.x() - player.getPosition().x();
                int dy = newPos.y() - player.getPosition().y();
                player.move(dx, dy);
            } else if (newPos != null) {
                System.out.println("You hit a wall!");
            }


        } while (player.isAlive());
    }
}
