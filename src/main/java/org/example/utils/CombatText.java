package org.example.utils;

import static org.example.utils.Colors.*;

public class CombatText {
    // Thade special dialog
    public static final String THADE_FOUND = purple("\nThade: -%s! You found me!");
    public static final String THADE_WRONG = purple("Thade: -But... something is wrong... I can't control myself!");
    public static final String THADE_DARKNESS = purple("Thade: -The darkness... it's taking over! RUN!");
    public static final String THADE_DESTROY = purple("Thade: -I... must... DESTROY YOU!");

    // Combat messages
    public static final String COMBAT_START = bold("\nThe fight against %s begins!");
    public static final String PLAYER_ATTACK = green("\nYou attack for %d damage!");
    public static final String ENEMY_HEALTH_LEFT = "%s has %d health left.";
    public static final String ENEMY_ATTACK = red("\n%s attacks you for %d damage!");
    public static final String PLAYER_HEALTH_LEFT = "You have %d health left.";

    // Victory/Defeat
    public static final String COMBAT_VICTORY = bold(green("\nYou won the fight!"));
    public static final String PLAYER_HEALTH_AFTER = "You have %d health left.";
    public static final String COMBAT_DEFEAT = red("\nYou lost the fight and died!");

    // Thade death dialog
    public static final String THADE_WELL_PLAYED = purple("\nThade: -Well played, ");
    public static final String THADE_WELL_PLAYED_END = purple("....well played..my friend.. ");
    public static final String THADE_DIES = "*Thade slowly dies*";

    // Thade reveal dialog
    public static final String THADE_REVEAL_START = purple("\nThade: -Oh.. ");
    public static final String THADE_REVEAL_QUESTION = purple(".. don't you see it?");
    public static final String THADE_NOT_NAME = purple("\nMy real name is not Thade..");
    public static final String THADE_REVEAL_SEQUENCE = purple("\n..Thade.. dThad.. deTha.. Detah..");
    public static final String THADE_REVEAL_DEATH = bold(purple("it's.. DEATH!"));
}
