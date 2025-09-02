package org.SpinalGlitter.exercise2.utils;

import org.SpinalGlitter.exercise2.entities.*;
import org.SpinalGlitter.exercise2.utils.WorldObject;
import org.SpinalGlitter.exercise2.map.DungeonMap;

import java.util.*;
import java.util.function.Function;
// TODO: Maybe make a constructor for this class?
// TODO: Maybe remove static methods from this class?
public final class RandomGeneration {
    private RandomGeneration() {
    }

    /**
     * Places a number of objects on the map, avoiding a specific position.
     *
     * @param map     The dungeon map
     * @param count   The number of objects to place
     * @param avoid   The position to avoid
     * @param rng     Random number generator
     * @param factory A function that takes a Position and returns an object of type T
     * @param <T>     The type of object to place (must extend WorldObject)
     * @return A map of positions to objects
     */
    public static <T extends WorldObject> Map<Position, T> placeObjects(
            DungeonMap map,
            int count,
            Position avoid,
            Random rng,
            Function<Position, T> factory) {

        // Build a list of all possible positions
        List<Position> candidates = new ArrayList<>();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Position p = new Position(x, y);
                if (!p.equals(avoid) && map.canMoveTo(p)) {
                    candidates.add(p);
                }
            }
        }

        // If no candidates, return an empty map
        if (candidates.isEmpty()) {
            return Collections.emptyMap();
        }

        // Shuffle the candidates on the map
        Collections.shuffle(candidates, rng);

        // 3) Välj upp till 'count' första platserna Denna förstår jag inte riktigt än CHATGPT
        int n = Math.min(count, candidates.size());
        Map<Position, T> out = new HashMap<>(n * 2);
        for (int i = 0; i < n; i++) {
            Position p = candidates.get(i);
            out.put(p, factory.apply(p));
        }

        return out;
    }

    /**
     * Function to generate a map of weapons, enemies, potions, etc.
     *
     * @param map
     * @param avoid
     * @param rng
     * @param potionCount
     * @param goblinCount
     * @param skeletonCount
     * @param weaponCount
     * @return
     */
    public static Map<Position, WorldObject> generateObjects(
            DungeonMap map,
            Position avoid,
            Random rng,
            int potionCount,
            int goblinCount,
            int skeletonCount,
            int weaponCount) {

        Map<Position, WorldObject> objects = new HashMap<>();

        // Potions
        objects.putAll(placeObjects(map, potionCount, avoid, rng, Potion::new));

        // Goblins
        objects.putAll(placeObjects(map, goblinCount, avoid, rng, Goblin::new));

        // Skeletons
        objects.putAll(placeObjects(map, skeletonCount, avoid, rng, Skeleton::new));

        // Weapons
        objects.putAll(placeObjects(map, weaponCount, avoid, rng,
                pos -> new Weapon("Sword", 5, pos)));

        return objects;
    }
}

