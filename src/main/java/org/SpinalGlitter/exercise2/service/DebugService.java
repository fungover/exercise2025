package org.SpinalGlitter.exercise2.service;

import org.SpinalGlitter.exercise2.entities.*;

import java.util.Map;

public final class DebugService {

    private DebugService() {}

    public static void printPotions(Map<Position, Potion> potions) {
        System.out.println("Potions:");
        if (potions.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Map.Entry<Position, Potion> entry : potions.entrySet()) {
            System.out.println("  - " + entry.getValue() + " at " + entry.getKey());
        }
    }

    public static void printEnemies(Map<Position, Enemy> enemies) {
        System.out.println("Enemies:");
        if (enemies.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Map.Entry<Position, Enemy> entry : enemies.entrySet()) {
            System.out.println("  - " + entry.getValue().getName() + " at " + entry.getKey());
        }
    }

    public static void printSwords(Map<Position, Sword> swords) {
        System.out.println("Swords:");
        if (swords.isEmpty()) {
            System.out.println("  (none)");
        }
        for (Map.Entry<Position, Sword> entry : swords.entrySet()) {
            System.out.println("  - " + entry.getValue() + " at " + entry.getKey());
        }
    }
}
