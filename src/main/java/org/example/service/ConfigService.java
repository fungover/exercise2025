package org.example.service;

import org.example.entities.items.Weapon;
import org.example.map.DungeonGrid;

public class ConfigService {

    public DifficultyConfig chooseDifficulty(int choice) {
        switch (choice) {
            case 1: return new DifficultyConfig("easy", 150, 150,
                    new Weapon("The Unlikely Mop", "Turns out this mop can clean up more than just floors.", 20, 30));
            case 2: return new DifficultyConfig("medium", 100, 100,
                    new Weapon("The Rusty Spoon of Destiny", "It's not what you'd expect, but it's what you've got.", 10, 20));
            case 3: return new DifficultyConfig("hard", 50, 50,
                    new Weapon("The Pebble of Regret", "A simple rock. You've clearly made some poor life choices.", 1, 5));
            default: return null;
        }
    }

    public DungeonGrid chooseMapSize(int choice) {
        switch (choice) {
            case 1: return new DungeonGrid(10, 10);
            case 2: return new DungeonGrid(15, 15);
            case 3: return new DungeonGrid(20, 20);
            default: return null;
        }
    }
}
