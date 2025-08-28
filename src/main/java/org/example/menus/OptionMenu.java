package org.example.menus;

import java.util.Scanner;

public record OptionMenu(Scanner scanner, String[] OPTIONS) {

    public int getOption() {
        boolean mainMenuActive = true;
        int option = -1;

        while (mainMenuActive) {
            printOptions();
            System.out.print("Enter your choice: \n");

            if (scanner.hasNextInt()) {
                option = scanner.nextInt();

                if (option == 0) {
                    return 0;
                }

                if (option >= 1 && option <= OPTIONS.length) {
                    mainMenuActive = false;
                } else {
                    System.out.println("Invalid choice, must be between 1 and " + OPTIONS.length);
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and " + OPTIONS.length);
                scanner.next();
            }
        }

        return option;
    }

    private void printOptions() {
        System.out.println("0. Exit");
        for (int i = 0; i < OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + OPTIONS[i]);
        }
    }

    public void menuExit(int areaMenuChoice) {
        if (areaMenuChoice == 0) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }
}
