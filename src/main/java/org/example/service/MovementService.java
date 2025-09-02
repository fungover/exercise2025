package org.example.service;

import org.example.entities.Player;
import org.example.map.Dungeon;

// Ansvarar för logiken när Player flyttar sig i Dungeon
public class MovementService {

    // Försöker flytta spelaren i en riktning
    // Returnerar true om flytten lyckas, annars false
    public boolean movePlayer(Player player, Dungeon dungeon, String direction) {
        int newX = player.getX();
        int newY = player.getY();

        // Ändrar koordinaterna enligt riktningen
        switch (direction.toLowerCase()) {
            case "north":
                newY -= 1;
                break;
            case "south":
                newY += 1;
                break;
            case "east":
                newX += 1;
                break;
            case "west":
                newX -= 1;
                break;
            default:
                return false;
        }

        // Kollar med Dungeon om rutan går att gå på
        if (dungeon.isWalkable(newX, newY)) {
            player.moveTo(newX, newY);
            return true;
        } else {
            return false;
        }
    }
}
