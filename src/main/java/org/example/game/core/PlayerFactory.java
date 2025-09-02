package org.example.game.core;

import org.example.entities.characters.*;

public final class PlayerFactory {
    private PlayerFactory() {}

    public enum Choice { ADVENTURER, WARRIOR }

    public static Player create(Choice choice, String name, int startX, int startY) {
        return switch (choice) {
            case WARRIOR -> new Warrior(name, startX, startY);
            case ADVENTURER -> new Adventurer(name, startX, startY);
        };
    }
}
