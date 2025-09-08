package dev.ronja.dungeon.game;

import dev.ronja.dungeon.entities.Player;

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
        System.out.println(" You have " + player.getHp() + " Health points ");
    }
}