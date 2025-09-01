package org.example.game;

import java.util.Random;
import org.example.map.DungeonMap;
import org.example.map.MapGenerator;

/** Small runner that shows the current state of the RNG room generator. */
public final class MapGeneratorPreviewApp {
    private static int between(Random rng, int minInclusive, int maxInclusive) {
        return minInclusive + rng.nextInt(maxInclusive - minInclusive + 1);
    }

    public static void main(String[] args) {
        // Seed strategy:
        // - Pass a number: reproducible (example: "java org.example.game.PreviewMain 12345")
        // - Pass nothing or "random": new layout each run
        final long seed = (args.length > 0 && !args[0].equalsIgnoreCase("random"))
                ? Long.parseLong(args[0])
                : System.currentTimeMillis();

        final int mapWidth = 40;
        final int mapHeight = 15;

        Random parameterRng = new Random(seed ^ 0x9E3779B97F4A7C15L);
        final int minRoomSize = 4;
        final int maxRoomSize = between(parameterRng, 8, 10);
        final int desiredRoomCount = between(parameterRng, 5, 9);
        final int roomPadding = 1;
        final int maxPlacementAttempts = 1000;

        var generator = new MapGenerator(
                seed,
                minRoomSize,
                maxRoomSize,
                desiredRoomCount,
                roomPadding,
                maxPlacementAttempts
        );

        System.out.printf("Seed=%d | Rooms=%d | Size=%d..%d | Map=%dx%d%n",
                seed, desiredRoomCount, minRoomSize, maxRoomSize, mapWidth, mapHeight);

        DungeonMap map = generator.generate(mapWidth, mapHeight);
        ConsoleMapPrinter.print(map);
    }
}
