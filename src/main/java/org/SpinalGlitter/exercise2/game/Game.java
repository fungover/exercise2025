// Game.java
package org.SpinalGlitter.exercise2.game;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.map.DungeonMap;
import org.SpinalGlitter.exercise2.utils.CommandUtils;
import org.SpinalGlitter.exercise2.utils.RandomGeneration;

import java.util.*;

import static org.SpinalGlitter.exercise2.service.PickupService.tryPickup;

public final class Game {

    static void main() {

        Player player = new Player("Hero");
        DungeonMap map = new DungeonMap(10, 10);
        Random rng = new Random();

        // Startitems för test
        player.getInventory().addItem(new Potion(null));
        player.getInventory().addItem(new Potion(null));
        player.getInventory().printItems();

        // 1) Väggar först
        RandomGeneration.placeWalls(map, 10, player.getPosition(), rng);

        // 2) Placera potions & enemies (utan occupied-argument)
        Map<Position, Potion> potions = RandomGeneration.placePotions(map, 5, player.getPosition(), rng);
        Map<Position, Enemy>  enemies = RandomGeneration.placeEnemies(map, 4, player.getPosition(), rng);

        // 3) Bygg occupied EFTER att potions/enemies är kända
        Set<Position> occupied = new HashSet<>();
        occupied.add(player.getPosition());
        occupied.addAll(potions.keySet());
        occupied.addAll(enemies.keySet());

        // 4) Placera svärd med occupied
        Map<Position, Sword> swords = RandomGeneration.placeSwords(map, 1, player.getPosition(), rng, occupied);


        System.out.println("Dungeon Crawler Game Started!");
        System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP.");
        CommandUtils.printHelp();

        Scanner scanner = new Scanner(System.in);

        while (player.isAlive()) {
            map.printMap(player.getPosition(), potions, enemies, swords);

            // add damage if player have weapon else remove extra damage
            if (player.haveWeapon()) {
                if (player.getDamage() == 10) {
                    player.setDamage(10);
                }
            } else if (player.getDamage() == 20 && !player.haveWeapon()) {
                player.setDamage(-10);
            }

            System.out.println("You are at " + player.getPosition() + " with health " + player.getCurrentHealth() + "HP." + " " + player.getDamage() + "DMG");
            System.out.print("> ");
            String input = CommandUtils.normalize(scanner.nextLine().trim().toLowerCase());

            Position newPos = null;
            switch (input) {
                case "options" -> CommandUtils.printHelp();

                case "quit" -> {
                    System.out.println("Exiting the game. Goodbye!");
                    return;
                }
                case "north" -> newPos = player.getPosition().getAdjacent(0, 1);
                case "south" -> newPos = player.getPosition().getAdjacent(0, -1);
                case "east" -> newPos = player.getPosition().getAdjacent(1, 0);
                case "west" -> newPos = player.getPosition().getAdjacent(-1, 0);
                case "inventory" -> player.getInventory().printItems();
                case "heal" -> player.heal(20);
                case "throw" -> player.getInventory().removeWeapon();
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


            tryPickup(potions, player.getPosition(), player);
            tryPickup(swords, player.getPosition(), player);

        }
    }
}

// TODO: implementera combat, inventory, och andra funktioner enligt behov


