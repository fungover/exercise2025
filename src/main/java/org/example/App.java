package org.example;

//System.console().readLine()

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String zone = zoneSelection();
        System.out.println("Selected zone: " + zone);
    }

    public static String zoneSelection() {
        System.out.println("Welcome! Please select what zone you would like to see the electrical prices in.");
        System.out.println("(1) - Luleå / Norra Sverige");
        System.out.println("(2) - Sundsvall / Norra Mellansverige");
        System.out.println("(3) - Stockholm / Södra Mellansverige");
        System.out.println("(4) - Malmö / Södra Sverige");
        System.out.println("Please select what zone you would like to see with the corresponding number");

        Scanner scanner = new Scanner(System.in);
        String selection = scanner.nextLine();

        return "SE" + selection;
    }
}
