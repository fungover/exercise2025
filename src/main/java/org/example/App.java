package org.example;

import org.example.entities.Player;
import org.example.entities.Enemy;
import org.example.entities.Character;

public class App {
    public static void main(String[] args) {
        System.out.println("Game starting!");

        Character player = new Player("Dragon Slayer", 100, 0, 0);
        Character dragon = new Enemy("Dragon", 100, 0, 0, 10);

    player.takeTurn();
    dragon.takeTurn();
    }
}
