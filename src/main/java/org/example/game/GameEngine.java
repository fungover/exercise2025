package org.example.game;

import org.example.entities.characters.Player;
import org.example.game.core.GameContext;
import org.example.game.core.GameController;
import org.example.game.core.GameController.RunResult;
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

        try (Scanner in = new Scanner(System.in)) {

            // Ask once per session; keep the same name/class on restart
            System.out.print("Choose class [1=Adventurer, 2=Warrior]: ");
            String pick = in.nextLine().trim().toLowerCase();
            Choice choice = (pick.equals("2") || pick.startsWith("w") || pick.contains("war"))
                    ? Choice.WARRIOR : Choice.ADVENTURER;

            System.out.print("Enter your name: ");
            String name = in.nextLine().trim();
            if (name.isEmpty()) name = "Hero";

            // Game loop so we can restart after death or win
            while (true) {
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

                // Locate SPAWN for this map
                Position spawn = SpawnLocator.findSpawn(map);

                System.out.printf("Seed=%d | Map=%dx%d%n", seed, mapWidth, mapHeight);

                // Build a fresh player at SPAWN for this run
                Player player = PlayerFactory.create(choice, name, spawn.x(), spawn.y());

                System.out.println("Welcome " + player.archetype() + " " + player.getName() + "!\n" +
                        "Your mission is to navigate a very old and dark mineshaft and find the source of the evil that lurks down there!\n" +
                        "Press Enter to start the adventure!");
                in.nextLine();

                var controller = new GameController(
                        "Hint: type 'help' for commands.",
                        "This is dark... I need to be careful of enemies hiding in the shadows."
                );

                RunResult result = controller.run(new GameContext(map, player), in); // CHANGED

                if (result == RunResult.RESTART) {
                    seed = System.currentTimeMillis();
                    continue;
                }

                // VICTORY or QUIT -> end session
                System.out.println("Thanks for playing!");
                return;
            }
        }
    }
}
