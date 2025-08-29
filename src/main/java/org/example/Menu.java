package org.example;

import java.util.Scanner;

public class Menu {
    public static String askForRegion() {
        System.out.println("-------------------------------");
        System.out.println("Please enter the region name: ");
        System.out.println("-------------------------------");
        System.out.println("[1] Luleå / Norra Sverige");
        System.out.println("[2] Sundsvall / Norra Mellansverige");
        System.out.println("[3] Stockholm / Södra Mellansverige");
        System.out.println("[4] Malmö / Södra Sverige");
        System.out.println("Enter Price Class: ");

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
