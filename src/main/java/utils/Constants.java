package utils;

/**
 * Konstanter för spelet
 */
public class Constants {  // Lägg till 'public' här
    // Spelarkonstanter
    public static final int PLAYER_STARTING_HEALTH = 100;
    public static final int PLAYER_STARTING_DAMAGE = 15;
    public static final int PLAYER_MAX_INVENTORY = 10;

    // Stridsystemet
    public static final int FLEE_SUCCESS_CHANCE = 70;
    public static final int SPECIAL_ATTACK_CHANCE = 30;

    // Kartkonstanter
    public static final int DEFAULT_MAP_WIDTH = 5;
    public static final int DEFAULT_MAP_HEIGHT = 4;

    // Symboler för kartan
    public static final char PLAYER_SYMBOL = '@';
    public static final char WALL_SYMBOL = '█';
    public static final char EMPTY_SYMBOL = ' ';
    public static final char DEAD_ENEMY_SYMBOL = 'x';

    // Fiendesymboler
    public static final char SKELETON_SYMBOL = 'S';
    public static final char SPIDER_SYMBOL = 's';
    public static final char PIRATE_SYMBOL = 'P';
    public static final char BAT_SYMBOL = 'B';

    // Föremålssymboler
    public static final char GOLD_SYMBOL = '$';
    public static final char KEY_SYMBOL = '♦';
    public static final char POTION_SYMBOL = '!';
    public static final char TREASURE_SYMBOL = '☆';
    public static final char SWORD_SYMBOL = '†';
}