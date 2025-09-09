package dev.ronja.dungeon.game;

import dev.ronja.dungeon.entities.*;

import java.util.Random;
import java.util.Scanner;

/**
 * The {@code Game} class represents the main game loop for my dungeon crawler
 * <p>It manages:
 * <ul>
 *     <li>reading user input from console</li>
 *     <li>creating and keeping track of the player</li>
 *     <li>starting and running the game logic</li>
 * </ul>
 * at the moment, the game only welcomes the player and shows their health, but it will later be extended
 * with commands, combats and exploration.
 * </p>
 **/
public class Game {
    private final Scanner in = new Scanner(System.in);
    private final Player player;
    private Enemy enemy;
    private final Random rng = new Random();

    /**
     * Creates a new {@code Game} instance.
     * <p>
     * A default player named "Ronja" is created when the game starts.
     * </p>
     **/
    public Game() {
        this.player = new Player(" Ronja ");
        this.enemy = null;
    }

    /**
     * Runs the main loop of the game
     **/
    public void run() {
        System.out.println("Welcome to the Dungeon of mysteries, " + player.getName() + " ! ");
        printHelp();

        while (player.isAlive()) {
            System.out.print("> ");
            String line = in.nextLine().trim().toLowerCase();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0];

            switch (cmd) {

                // Info
                case "help" -> printHelp();
                case "status" -> printStatus();
                case "who" -> showEnemy();

                // Healers
                case "potion" -> {
                    int amount = parseAmount(parts, 30);
                    Healer h = new Potion(amount);
                    h.heal(player);
                    printStatus();
                }
                case "mother" -> {
                    int amount = parseAmount(parts, 20);
                    Healer h = new Mother(amount);
                    h.heal(player);
                    printStatus();
                }

                // Enemies rendered
                case "spawn" -> {
                    if (parts.length < 2) {
                        enemy = spawnRandomEnemy();
                    } else {
                        enemy = spawnByName(parts[1]);
                    }
                    showEnemy();
                }
                case "next" -> {
                    enemy = spawnRandomEnemy();
                    showEnemy();
                }

                // Combat between player and enemy
                case "attack" -> {
                    ensureEnemy();
                    if (!player.isAlive()) break;
                    int strike = 10; //Todo: connect to weapon
                    enemy.takeDamage(strike);
                    System.out.println(player.getName() + " strikes " + enemy.getName() + " for " + strike + " damage ");

                    if (!enemy.isAlive()) {
                        System.out.println(" You defeated the " + enemy.getName() + " use 'next or 'spawn to continue ");
                        enemy = null;
                        printStatus();
                    }

                    enemy.attack(player);
                    if (!player.isAlive()) {
                        System.out.println("☠️ You got defeated by the " + enemy.getName() + "...");
                        break;
                    }
                    printStatus();
                }

                case "damage" -> { player.takeDamage(10); printStatus();}
                case "heal" -> { player.heal(5); printStatus(); }
                case "quit" -> { System.out.println(" Goodbye "); return; }

                default -> System.out.println(" Unknown command. Try: 'status', 'attack' or 'help'! ");
            }
        }
        System.out.println(" GAME OVER! You will now be buried in the darkest depths of the dungeon ");
    }

    // Helpers, a list of cmd`s
    private void printHelp() {
        System.out.println("""
                Commands:
                help - Show the commands of this Game
                status - Show player + enemy health status
                who - Show your current enemy
                next - Spawn a new random enemy
                attack - Strike (10 in DMG), then enemy counterattacks you
                potion - Use a healing potion
                mother - Call your mother for healing
                COMMANDS FOR TESTING:
                spawn <type|random> - Spawn a specific enemy (Siren, MotherInLaw, DarkWitch or Random)
                damage / heal - Debug HP
                Q - QUIT the Game
                """);
    }

    private void printStatus() {
        System.out.println(player + (enemy == null ? "" : " | Enemy: " + enemy));
    }

    private void showEnemy() {
        if (enemy == null) {
            System.out.println(" No enemy present. Use command: 'next' or 'spawn' ");
        } else {
            System.out.println(" You face: " + enemy);
        }
    }

    private void ensureEnemy() {
        if (enemy == null) {
            enemy = spawnRandomEnemy();
            System.out.println(" A wild " + enemy.getName() + " appears! ");
        }
    }

    private Enemy spawnRandomEnemy() {
        return switch (rng.nextInt(3)) {
            case 0 -> new Siren();
            case 1 -> new MotherInLaw();
            default -> new DarkWitch();
        };
    }

    private Enemy spawnByName(String name) {
        return switch (name) {
            case "Siren" -> new Siren();
            case "MotherInLaw" -> new MotherInLaw();
            case "DarkWitch" -> new DarkWitch();
            case "Random" -> spawnRandomEnemy();
            default -> {
                System.out.println(" Unknown enemy type. Spawning random! ");
                yield spawnRandomEnemy();
            }
        };
    }

    private static int parseAmount(String[] parts, int fallback) {
        if (parts.length < 2) return fallback;
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println(" Not a number. Using default: " + fallback);
            return fallback;
        }
    }
}