package dev.ronja.dungeon.game;

import dev.ronja.dungeon.entities.Healer;
import dev.ronja.dungeon.entities.Mother;
import dev.ronja.dungeon.entities.Player;
import dev.ronja.dungeon.entities.Potion;

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

    /**
     * Creates a new {@code Game} instance.
     * <p>
     * A default player named "Ronja" is created when the game starts.
     * </p>
     **/
    public Game() {
        this.player = new Player("Ronja");
    }

    /**
     * Runs the main loop of the game
     * <p>
     * For now, it only prints a welcome message and the player´s health.
     * Method will soon handle player commands and progression.
     * </p>
     **/
    public void run() {
        System.out.println(" Welcome to the Dungeon of mysteries, " + player.getName() + " ! ");
        System.out.println(" You have " + player.getHp() + " Health points (HP) ");
        System.out.println(" Commands: status, damage, heal, potion <n>, mother <n>, quit ");

        while (player.isAlive()) {
            System.out.println(" < ");
            String line = in.nextLine().trim().toLowerCase();

            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+");
            String cmd = parts[0];

            switch (cmd) {
                case "status" -> System.out.println(player);
                case "damage" -> player.takeDamage(10);
                case "heal" -> player.heal(5);
                case "potion" -> {
                    int amount = parseAmount(parts, 30);
                    Healer h = new Potion(amount);
                    h.heal(player);
                }
                case "mother" -> {
                    int amount = parseAmount(parts, 20);
                    Healer h = new Mother(amount);
                    h.heal(player);
                }
                case "quit" -> {
                    System.out.println(" Goodbye ");
                    return;
                }
                default -> System.out.println( "Unknown command. Try: status, damage, heal, quit!" );
            }
        }
        System.out.println("GAME OVER! You will now be buried in the darkest depths of the dungeon");
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