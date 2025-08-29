package org.example;

import java.util.Scanner;

public class Menu {
    /**
     * Prompts the user on standard output to select a region, reads one line from standard input,
     * and returns the corresponding region code.
     *
     * The method maps input strings "1".."4" to region codes "SE1".."SE4" respectively.
     * Any other input (including "0" or non-numeric input) results in a null return.
     *
     * @return the selected region code ("SE1", "SE2", "SE3", or "SE4"), or {@code null} if the input is not one of "1".."4".
     */
    public static String askForRegion() {
        System.out.println("-------------------------------");
        System.out.println("Please enter the region number: ");
        System.out.println("-------------------------------");
        System.out.println("[1] Luleå / Norra Sverige");
        System.out.println("[2] Sundsvall / Norra Mellansverige");
        System.out.println("[3] Stockholm / Södra Mellansverige");
        System.out.println("[4] Malmö / Södra Sverige");
        System.out.println("[0] Exit");

        System.out.println("Enter Region Number: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        return switch (input) {
            case "1" -> "SE1";
            case "2" -> "SE2";
            case "3" -> "SE3";
            case "4" -> "SE4";
            default -> null;
        };
    }
}
