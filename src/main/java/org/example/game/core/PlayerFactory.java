package org.example.game.core;

import org.example.entities.characters.*;
import static org.example.utils.Guard.notNull;
import static org.example.utils.Guard.notBlank;

public final class PlayerFactory {
    private PlayerFactory() {}

    public enum Choice { ADVENTURER, WARRIOR }

    public static Player create(Choice choice, String name, int startX, int startY) {
        notNull(choice, "choice");
        notBlank(name, "name");

        return switch (choice) {
            case WARRIOR    -> new Warrior(name, startX, startY);
            case ADVENTURER -> new Adventurer(name, startX, startY);
        };
    }
}
