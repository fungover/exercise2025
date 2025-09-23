package org.SpinalGlitter.exercise2.utils;

import java.util.Locale;

public class CommandUtils {

    private CommandUtils() {}

    public static void printHelp() {
        System.out.println("Available commands:");
        System.out.println(" options / o - show this list");
        System.out.println(" quit / q - exit game");
        System.out.println(" north / n - move north");
        System.out.println(" south / s - move south");
        System.out.println(" east / e - move east");
        System.out.println(" west / w - move west");
        System.out.println(" attack / a - attack enemy");
        System.out.println(" inventory / i - show inventory");
        System.out.println(" heal / h - use a potion to heal");
        System.out.println(" throw / t - throw away your weapon");
    }

    public static String normalize(String input) {

        if(input == null) return "";
        final String normalized = input.trim().toLowerCase(Locale.ROOT);
        return switch (normalized) {
            case "o" -> "options";
            case "q" -> "quit";
            case "n" -> "north";
            case "s" -> "south";
            case "e" -> "east";
            case "w" -> "west";
            case "a" -> "attack";
            case "i" -> "inventory";
            case "h" -> "heal";
            case "t" -> "throw";
            default -> normalized;
        };
    }

}
