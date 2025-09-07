package org.example;

import org.example.entities.Player;
import org.example.entities.Tile;
import org.example.entities.behaviors.BasicCombat;
import org.example.entities.enemies.Troll;
import org.example.game.CommandParser;
import org.example.game.Game;
import org.example.map.BasicMapGenerator;
import org.example.map.Dungeon;
import org.example.service.CombatService;
import org.example.service.ItemService;
import org.example.service.MovementService;
import org.example.utils.InputValidator;
import org.example.utils.RandomGenerator;

public class App {
    public static void main(String[] args) {
        boolean isTestMode = "true".equals(System.getProperty("test.mode", "false"));

        Player player = new Player("Hero", 100, 10, new BasicCombat(10));
        Dungeon dungeon;
        if (isTestMode) {
            // TESTING ONLY: Uses seeded RandomGenerator and overrides map generation
            RandomGenerator rng = new RandomGenerator(12345L);
            dungeon = new Dungeon(5, 5, player, new BasicMapGenerator() {
                @Override
                public Tile[][] generate(int width, int height) {
                    Tile[][] tiles = super.generate(width, height);
                    tiles[2][1] = new Tile(new Troll()); // Enemy at (1, 2)
                    tiles[1][1] = new Tile(new Troll()); // Enemy at (1, 1)
                    return tiles;
                }
            });
        } else {
            dungeon = new Dungeon(5, 5, player, new BasicMapGenerator());
        }
        InputValidator validator = new InputValidator(dungeon);
        CommandParser parser = new CommandParser(dungeon,
                new MovementService(dungeon, validator),
                new CombatService(dungeon, validator),
                new ItemService(dungeon, validator));
        Game game = new Game(dungeon, parser);
        game.run();
    }
}

/*

Design and implement a turn-based Dungeon Crawler game in Java using Object-Oriented Programming (OOP) principles. The game will run in the command-line interface (CLI), and user input will be processed line-by-line (i.e. after pressing Enter). Players explore a dungeon, encounter enemies, collect items, and manage their health and inventory.

    Project Structure

    Organize your code into logical packages:

        entities: Player, Enemy, Item, etc.

        map: Dungeon grid, rooms, tiles

        game: Game loop, input handling

        service: Game logic, combat, movement

        utils: Helpers, random generation

    ## Entities

    ## Implement the following core classes:

        Player: Attributes like name, health, position, inventory

        Enemy: Type, health, damage, position

        Item: Name, type (e.g. weapon, potion), effect

        Tile: Represents a cell in the dungeon (can be empty, wall, enemy, item)

        Use encapsulation, inheritance, and polymorphism where appropriate. For example: Create different enemy types.

    ## Dungeon Map

        Represent the dungeon as a 2D grid of Tile objects or a linked graph.

        Generate a simple map with walls, paths, and random item/enemy placement

        Ensure the player starts at a valid position

    ## Game Mechanics (Turn-Based)

        Since real-time key detection isn't feasible in CLI, implement a turn-based command system:

        Accept commands like move north, attack, use potion, inventory, look, etc.

        Parse user input using Scanner or similar

        After each command, update the game state and display the result

        Example turn:

            > move east
            You moved to a new tile. There's an enemy here!
            > attack
            You dealt 5 damage. Enemy HP: 10
            > attack
            Enemy defeated!

     ## Testing

        Use JUnit 5 to write unit tests for:

        Movement logic

        Combat calculations

        Item effects

        Map generation (basic validation)*/
