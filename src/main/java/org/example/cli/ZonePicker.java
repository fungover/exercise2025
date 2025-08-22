package org.example.cli;

import java.util.Scanner;

public class ZonePicker {
    public String getZone() {
        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("""
                               Please pick the Zone (1 - 4) 
                               1. SE1 = Luleå / Norra Sverige
                               2. SE2 = Sundsvall / Norra Mellansverige
                               3. SE3 = Stockholm / Södra Mellansverige
                               4. SE4 = Malmö / Södra Sverige
                               Enter the Number of your choice: 
                               """);

            if (sc.hasNextInt()) { //check if input is integer
                choice = sc.nextInt(); //reads integer input
                switch (choice) {
                    case 1 -> {
                        return "SE1";
                    }
                    case 2 -> {
                        return "SE2";
                    }
                    case 3 -> {
                        return "SE3";
                    }
                    case 4 -> {
                        return "SE4";
                    }
                    default -> System.out.println("Invalid choice, please " +
                      "input a number between 1 and 4."); //if the input is
                    // not a number between 1-4
                }
            } else {
                System.out.println("Invalid choice, please input a number between 1 and 4.");
                sc.next();
            }
            
        }

    }

}
