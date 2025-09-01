package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.Player;
import org.SpinalGlitter.exercise2.entities.Potion;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.entities.Position;
import org.SpinalGlitter.exercise2.utils.CommandUtils;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public final class Game {

    static void main() {

        Player player = new Player("Hero");
        DungeonMap map = new DungeonMap(10, 10);
        Position newPos = null;
        Random rng = new Random();
        Map<Position, Potion> potions = RandomGeneration.placePotions(map, 5, player.getPosition(), rng);


        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        CommandUtils.printHelp();


        Scanner scanner = new Scanner(System.in);


        do {
            newPos = null;
            map.printMap(player.getPosition(), potions);
            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
            System.out.print("> ");
            String input = CommandUtils.normalize(scanner.nextLine().trim().toLowerCase());

            switch (input) {
                case "help", "h":  CommandUtils.printHelp(); break;

                case "quit", "q":  System.out.println("Exiting the game. Goodbye!"); return;

                case "north", "n": newPos = player.getPosition().getAdjacent(0, 1); break;

                case "south", "s": newPos = player.getPosition().getAdjacent(0, -1);  break;

                case "east", "e": newPos = player.getPosition().getAdjacent(1, 0); break;

                case "west", "w": newPos = player.getPosition().getAdjacent(-1, 0);  break;

                default:  System.out.println("Unknown command. Type 'help' for a list of commands."); break;
            }

            if (newPos != null && map.canMoveTo(newPos)) {
                int dx = newPos.x() - player.getPosition().x();
                int dy = newPos.y() - player.getPosition().y();
                player.move(dx, dy);
                Potion found = potions.remove(newPos);
                if (found != null) {
                    player.heal(found.getHeal());
                    System.out.println("You found a " + found.getName() +
                            " (+ " + found.getHeal() + " HP). Current HP: " + player.getCurrentHealth());
                }
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
