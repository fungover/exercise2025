package org.example.game;

import java.util.Iterator;
import org.example.service.Combat;
import org.example.entities.Goblin;
import org.example.entities.Ghost;
import org.example.entities.Troll;
import org.example.entities.Dragon;
import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Character;
import org.example.map.DungeonGrid;
import org.example.service.GameLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLoop {
    public void start() {
        System.out.println("Game starting!");

        Character player = new Player("Dragon Slayer", 100, 2, 2);


       // player.takeTurn(); Maybe implement this later

        DungeonGrid grid = new DungeonGrid(10, 5);

        List<Character> enemies = new ArrayList<>();
        enemies.add(new Goblin(3,3));
        enemies.add(new Ghost(5,2));
        enemies.add(new Troll(7,4));
        enemies.add(new Dragon(9,1));

        // Starting position
        //From Character abstract class
        //int[] playerPos = {2, 2};

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            grid.printMap(player.getX(), player.getY());
            System.out.print("Where are you headed?: (north/south/east/west/quit): ");
            String command = scanner.nextLine().toLowerCase().trim();

            if (command.equals("quit")) {
                running = false;
            } else {
                GameLogic.handleCommand(command, grid, player);

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