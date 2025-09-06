package org.example.game;

import org.example.entities.characters.Player;
import org.example.game.core.GameContext;
import org.example.game.core.GameController;
import org.example.game.core.PlayerFactory;
import org.example.game.core.PlayerFactory.Choice;
import org.example.map.DungeonMap;
import org.example.map.MapGenerator;
import org.example.utils.Position;
import org.example.utils.SpawnLocator;

import java.util.Random;
import java.util.Scanner;

public final class GameEngine {

    private static int between(Random rng, int minInclusive, int maxInclusive) {
        return minInclusive + rng.nextInt(maxInclusive - minInclusive + 1);
    }

    public void start() {
        start(System.currentTimeMillis());
    }

    public void start(long seed) {
        final int mapWidth = 40, mapHeight = 15;

        Random rootRng = new Random(seed);
        final int minRoomSize = 4;
        final int maxRoomSize = between(rootRng, 8, 10);
        final int desiredRoomCount = between(rootRng, 5, 9);
        final int roomPadding = 1;
        final int maxPlacementAttempts = 1000;

        var generator = new MapGenerator(
                seed, minRoomSize, maxRoomSize, desiredRoomCount, roomPadding, maxPlacementAttempts
        );
        DungeonMap map = generator.generate(mapWidth, mapHeight);

        // Locate SPAWN
        Position spawn = SpawnLocator.findSpawn(map);

        System.out.printf("Seed=%d | Map=%dx%d%n", seed, mapWidth, mapHeight);

        try (Scanner in = new Scanner(System.in)) {
            // Choose class
            System.out.print("Choose class [1=Adventurer, 2=Warrior]: ");
            String pick = in.nextLine().trim().toLowerCase();
            Choice choice = (pick.equals("2") || pick.startsWith("w") || pick.contains("war"))
                    ? Choice.WARRIOR : Choice.ADVENTURER;

            // Choose name
            System.out.print("Enter your name: ");
            String name = in.nextLine().trim();
            if (name.isEmpty()) name = "Hero";

            // Build the player via factory (spawns at SPAWN)
            Player player = PlayerFactory.create(choice, name, spawn.x(), spawn.y());

            System.out.println("Welcome " + player.archetype() + " " + player.getName() + "!\n" +
                    "Your mission is to navigate a very old and dark mineshaft and find the source of the evil that lurks down there!\n" +
                    "Press Enter to start the adventure!");
            in.nextLine();

            // Hand off to controller (movement + feedback already implemented there)
            var controller = new GameController(
                    "Hint: type 'help' for commands.",
                    "This is dark... I need to be careful of enemies hiding in the shadows.");
            controller.run(new GameContext(map, player), in);
        }
    }
}
