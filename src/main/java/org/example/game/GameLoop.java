package org.example.game;

import java.util.Iterator;

import org.example.entities.*;
import org.example.entities.Character;
import org.example.service.Combat;
import org.example.map.DungeonGrid;
import org.example.service.GameLogic;
import org.example.utils.RandomGeneration;

import org.example.utils.ItemOnMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLoop {
    public void start() {
        System.out.println("Game starting!");

        Character player = new Player("Dragon Slayer", 100, 2, 2);


       // player.takeTurn(); Maybe implement this later

        DungeonGrid grid = new DungeonGrid(12, 8);

        List<Character> enemies = new ArrayList<>();
      /*  enemies.add(new Goblin(3,3));
        enemies.add(new Ghost(5,2));
        enemies.add(new Troll(7,4));
        enemies.add(new Dragon(9,1)); */

        //Loot on map
        List<ItemOnMap> itemsOnMap = new ArrayList<>();
       /* itemsOnMap.add(new ItemOnMap(new HealthPotion("Small Potion", 20), 3, 2));
        itemsOnMap.add(new ItemOnMap(new HealthPotion("Large Potion", 50), 4, 2)); */

        //Random enemies
    int [] pos;
    pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
    enemies.add(new Goblin(pos[0], pos[1]));
    pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
    enemies.add(new Ghost(pos[0], pos[1]));
    pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
    enemies.add(new Troll(pos[0], pos[1]));
    pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
    enemies.add(new Dragon(pos[0], pos[1]));

    //Random items
        pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
        itemsOnMap.add(new ItemOnMap(new HealthPotion("Small Potion", 20), pos[0], pos[1]));
        pos = RandomGeneration.getRandomPosition(grid, player, enemies, itemsOnMap);
        itemsOnMap.add(new ItemOnMap(new HealthPotion("Large Potion", 50), pos[0], pos[1]));

        //DEBUG
        System.out.println("Enemies;");
        for (Character e: enemies) {
            System.out.println(e.getName()+ " at (" + e.getX() + "," + e.getY() + ")");
        }
        System.out.println("Items on map;");
        for (ItemOnMap i: itemsOnMap) {
            System.out.println(i.item.getName()+ " at (" + i.x + "," + i.y + ")");
        }

        // Starting position
        //From Character abstract class
        //int[] playerPos = {2, 2};

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Status update
            System.out.println("HP: " + player.getHealth() +
                    " | Items: " + ((Player) player).getInventoryCount());

            grid.printMap(player.getX(), player.getY(), enemies, itemsOnMap);
            System.out.print("Where are you headed?: (north/south/east/west/quit): ");
            String command = scanner.nextLine().toLowerCase().trim();

            //Inventory command
            if (command.equals("inventory")) {
                ((Player) player).showInventory();
                continue;
            } else if (command.startsWith("use ")) {
                try {
                    int index = Integer.parseInt(command.split(" ")[1]) - 1;
                    ((Player) player).useItem(index);
                } catch (Exception e) {
                    System.out.println("Usage: use <item number>");
                }
                continue;
            }

            if (command.equals("quit")) {
                running = false;
            } else {
                GameLogic.handleCommand(command, grid, player);

                //Loot pickup
                Iterator<ItemOnMap> itemIt = itemsOnMap.iterator();
                while (itemIt.hasNext()) {
                    ItemOnMap iom = itemIt.next();
                    if (player.getX() == iom.x && player.getY() == iom.y) {
                        System.out.println("You found a " + iom.item.getName() + "!");
                        ((Player) player).addItem(iom.item);
                        System.out.println("Type 'inventory' to view your items or 'use <number>' to use one.");
                        itemIt.remove();
                    }
                }

                // Enemy encounter check
                Iterator<Character> it = enemies.iterator();
                while (it.hasNext()) {
                    Character enemy = it.next();
                    if (player.getX() == enemy.getX() && player.getY() == enemy.getY()) {
                        System.out.println("You encountered a " + enemy.getName() + "!");
                        Combat.start(player, enemy);
                        if (!enemy.isAlive()) {
                            it.remove(); // Remove defeated enemy

                        }
                        if (!player.isAlive()) {
                            System.out.println("You have been defeated!");
                            running = false;
                            break;
                        }
                    }
                }

            }
        }

    scanner.close();

        }
    }