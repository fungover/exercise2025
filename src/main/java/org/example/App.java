package org.example;

import org.example.entities.characters.Player;
import org.example.entities.enemies.Goblin;
import org.example.entities.items.Inventory;
import org.example.entities.items.Item;
import org.example.entities.items.Weapon;
import org.example.entities.items.potions.HealthPotion;
import org.example.game.GameLogic;
import org.example.map.DungeonGrid;
import org.example.map.Tile;
import org.example.service.SpawnService;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        gameLogic.initializeGame();
    }
}
