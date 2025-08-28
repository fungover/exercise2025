package org.example;

import org.example.entities.Player;
import org.example.entities.Position;

public class App {
    public static void main(String[] args) {


        Player player = new Player("Hero", 100, new Position(1,1));
        System.out.println(player);
        player.moveWest();
        System.out.println("After moving: " + player);
    }
}
