package org.SpinalGlitter.exercise2.utils;

public class CommandUtils {

    private CommandUtils() {}

    public static void printHelp() {
        System.out.println("Available commands:");
        System.out.println(" help / h - show this list");
        System.out.println(" quit / q - exit game");
        System.out.println(" north / n - move north");
        System.out.println(" south / s - move south");
        System.out.println(" east / e - move east");
        System.out.println(" west / w - move west");
        System.out.println(" attack / a - attack enemy");
        System.out.println(" inventory / i - show inventory");
    }

    public static String normalize(String input) {
        return switch (input) {
            case "h" -> "help";
            case "q" -> "quit";
            case "n" -> "north";
            case "s" -> "south";
            case "e" -> "east";
            case "w" -> "west";
            case "a" -> "attack";
            case "i" -> "inventory";
            default -> input;
        };
    }

}
