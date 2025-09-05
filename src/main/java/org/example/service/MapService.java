package org.example.service;

import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;

public class MapService {
    public void look(Player player, FarmageddonMap map) {
        int x = player.getX();
        int y = player.getY();
        if (x < 0 || y < 0 || x >= map.getWidth() || y >= map.getHeight()) {
            System.out.println("Watch out! You are at the edge of the map.");
            return;
        }
        Tile tile = map.getTile(x, y);
        System.out.println("You are standing on " + tile.getType().name().toLowerCase());
    }
}
