package service;

import entities.*;
import map.Dungeon;
import map.Tile;
import map.TileType;
import utils.Rng;

/**
 * Enkel generator för Dungeon-karta.
 * - Skapar 2D-grid med väggar runt kanterna
 * - Lägger till slumpmässiga hinder (WALL) inuti
 * - Markerar START och placerar spelaren
 * - Lägger ut fiender och items på walkable rutor (inte START)
 * - (Valfritt) skapar EXIT
 */
public class MapGenerator {

    /** Skapar en dungeon och placerar allt grundinnehåll. */
    public static Dungeon createInitialWorld(int width, int height, Player player, Rng rng) {
        Dungeon d = new Dungeon(width, height);

        // 1) Slumpmässiga hinder inuti (10–15% av inre rutor)
        sprinkleInnerWalls(d, rng, 12); // procent

        // 2) START
        int[] start = findRandomEmpty(d, rng);
        d.markStart(start[0], start[1]);
        // placera spelaren
        setPlayerPosition(player, start[0], start[1]);

        // 3) ENEMIES
        // några enkla standardantal
        placeEnemies(d, rng, 3, 2, 1);

        // 4) ITEMS
        placeItems(d, rng, 3, 1); // 3 potions, 1 weapon

        // 5) EXIT (valfritt) – placera så långt bort som möjligt från start, om möjligt
        placeExitFarFromStart(d, start[0], start[1], rng);

        return d;
    }

    /** Lägger slumpmässiga väggar i den inre delen av kartan (ej kanter). */
    private static void sprinkleInnerWalls(Dungeon d, Rng rng, int percent) {
        for (int y = 1; y < d.getHeight() - 1; y++) {
            for (int x = 1; x < d.getWidth() - 1; x++) {
                if (rng.chance(percent)) {
                    d.setTileType(x, y, TileType.WALL);
                }
            }
        }
    }

    /** Hittar en slumpmässig tom och walkable ruta (EMPTY), inte vägg, inte enemy/item. */
    private static int[] findRandomEmpty(Dungeon d, Rng rng) {
        // Enkel retry-loop (begränsad för nybörjare)
        for (int attempts = 0; attempts < 10_000; attempts++) {
            int x = 1 + rng.nextInt(d.getWidth() - 2);
            int y = 1 + rng.nextInt(d.getHeight() - 2);
            Tile t = d.getTile(x, y);
            if (t != null
                    && d.isWalkable(x, y)
                    && TileType.EMPTY.equals(t.getType())
                    && t.getEnemy() == null
                    && t.getItem() == null) {
                return new int[]{x, y};
            }
        }
        // fallback (borde ej hända om kartan inte är för blockerad)
        return new int[]{1, 1};
    }

    private static void setPlayerPosition(Player p, int x, int y) {
        // Flytta spelaren till (x,y) relativt nuvarande pos
        int dx = x - p.getX();
        int dy = y - p.getY();
        p.move(dx, dy);
    }

    /** Lägg ut fiender: gobs, skeletons, orcs på tomma rutor. */
    private static void placeEnemies(Dungeon d, Rng rng, int goblins, int skeletons, int orcs) {
        for (int i = 0; i < goblins; i++) {
            int[] pos = findRandomEmpty(d, rng);
            d.placeEnemy(pos[0], pos[1], new Goblin(pos[0], pos[1]));
        }
        for (int i = 0; i < skeletons; i++) {
            int[] pos = findRandomEmpty(d, rng);
            d.placeEnemy(pos[0], pos[1], new Skeleton(pos[0], pos[1]));
        }
        for (int i = 0; i < orcs; i++) {
            int[] pos = findRandomEmpty(d, rng);
            d.placeEnemy(pos[0], pos[1], new Orc(pos[0], pos[1]));
        }
    }

    /** Lägg ut items: X potions, Y weapons. */
    private static void placeItems(Dungeon d, Rng rng, int potions, int weapons) {
        for (int i = 0; i < potions; i++) {
            int[] pos = findRandomEmpty(d, rng);
            d.placeItem(pos[0], pos[1], new HealingPotion(10)); // +10 HP
        }
        for (int i = 0; i < weapons; i++) {
            int[] pos = findRandomEmpty(d, rng);
            d.placeItem(pos[0], pos[1], new Weapon("Rusty Sword", 2)); // +2 dmg
        }
    }

    /** Placera en EXIT, gärna långt bort från start. */
    private static void placeExitFarFromStart(Dungeon d, int sx, int sy, Rng rng) {
        int bestX = -1, bestY = -1, bestScore = -1;
        for (int y = 1; y < d.getHeight() - 1; y++) {
            for (int x = 1; x < d.getWidth() - 1; x++) {
                Tile t = d.getTile(x, y);
                if (t == null) continue;
                if (!d.isWalkable(x, y)) continue;
                if (!TileType.EMPTY.equals(t.getType())) continue; // måste vara tom
                int dist = Math.abs(x - sx) + Math.abs(y - sy); // Manhattan-distans
                if (dist > bestScore) {
                    bestScore = dist;
                    bestX = x;
                    bestY = y;
                }
            }
        }
        if (bestX >= 0) {
            d.markExit(bestX, bestY);
        } else {
            // fallback: slumpa en tom ruta
            int[] pos = findRandomEmpty(d, rng);
            d.markExit(pos[0], pos[1]);
        }
    }
}
