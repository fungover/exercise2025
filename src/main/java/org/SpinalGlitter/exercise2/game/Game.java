// Game.java
package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.utils.CommandUtils;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public final class Game {

    static void main() {

        Player player = new Player("Hero");
        DungeonMap map = new DungeonMap(10, 10);
        Random rng = new Random();

        player.getInventory().addItem(new Potion(null));
        player.getInventory().addItem(new Potion(null));

        player.getInventory().printItems();


        // 1) Lägg ut slumpmässiga väggar (t.ex. 10 hinder – justera efter smak)
        RandomGeneration.placeWalls(map, 10, player.getPosition(), rng);

        // 2) Lägg ut potions och fiender
        Map<Position, Potion> potions = RandomGeneration.placePotions(map, 5, player.getPosition(), rng);
        Map<Position, Enemy> enemies = RandomGeneration.placeEnemies(map, 4, player.getPosition(), rng);

        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        CommandUtils.printHelp();

        Scanner scanner = new Scanner(System.in);

        while (player.isAlive()) {
            map.printMap(player.getPosition(), potions, enemies);
            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
            System.out.print("> ");
            String input = CommandUtils.normalize(scanner.nextLine().trim().toLowerCase());

            Position newPos = null;
            switch (input) {
                case "options" -> CommandUtils.printHelp();

                case "quit" -> { System.out.println("Exiting the game. Goodbye!"); return; }
                case "north" -> newPos = player.getPosition().getAdjacent(0, 1);
                case "south" -> newPos = player.getPosition().getAdjacent(0, -1);
                case "east"  -> newPos = player.getPosition().getAdjacent(1, 0);
                case "west"  -> newPos = player.getPosition().getAdjacent(-1, 0);
                case "inventory"-> player.getInventory().printItems();
                case "heal"-> player.heal(20);
                default -> System.out.println("Unknown command. Type 'help' for a list of commands.");
            }

            if (newPos == null) continue;

            // 3) Tillåt inte att gå in i vägg eller fiende (fiender som hinder tills combat finns)
            if (!map.canMoveTo(newPos)) {
                System.out.println("You hit a wall!");
                continue;
            }
            if (enemies.containsKey(newPos)) {
                Enemy e = enemies.get(newPos);
                System.out.println("An enemy blocks your path: " + e.getName() + " at " + newPos + ".");
                // Här kan du senare trigga combat i stället för att blockera
                continue;
            }

            // 4) Flytta spelaren
            int dx = newPos.x() - player.getPosition().x();
            int dy = newPos.y() - player.getPosition().y();
            player.move(dx, dy);

            // 5) Plocka upp potion om du klev på en
            Potion found = potions.remove(newPos);
            if (found != null) {
                boolean added = player.getInventory().addItem(found);
                if(added) {
                    System.out.println("You found a potion and added it to your inventory.");
                } else {
                    System.out.println("You found a potion but your inventory is full. You leave it behind.");
                    potions.put(newPos, found); // Lägg tillbaka potions om inte plats
                }
            }
        }
    }
}

// TODO: implementera combat, inventory, och andra funktioner enligt behov
// TODO: 1 Implement pick up item.

