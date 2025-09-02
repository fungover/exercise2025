package org.example.service;

import org.example.entities.Player;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;

public class MapService {
    public void look(Player player, FarmageddonMap map) {
        Tile tile = map.getTile(player.getX(), player.getY());
        System.out.println("You are standing on a " + tile.getType());
    }
}
