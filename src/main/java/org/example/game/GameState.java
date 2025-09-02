package org.example.game;

import org.example.entities.items.Weapon;
import org.example.map.DungeonGrid;

public class GameState {
    public String name;
    public String difficulty;
    public int health;
    public int maxHealth;
    public Weapon weapon;
    public DungeonGrid grid;
    public boolean gameOver;
}
