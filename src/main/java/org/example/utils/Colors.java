package org.example.utils;

public class Colors {
    // Text colors
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";

    // Text style
    public static final String BOLD = "\u001B[1m";

    // Reset to normal
    public static final String RESET = "\u001B[0m";

    // Helper methods
    public static String red(String text) {
        return RED + text + RESET;
    }

    public static String green(String text) {
        return GREEN + text + RESET;
    }

    public static String yellow(String text) {
        return YELLOW + text + RESET;
    }

    public static String purple(String text) {
        return PURPLE + text + RESET;
    }

    public static String cyan(String text) {
        return CYAN + text + RESET;
    }

    public static String bold(String text) {
        return BOLD + text + RESET;
    }
}