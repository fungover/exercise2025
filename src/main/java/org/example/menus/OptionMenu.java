package org.example.menus;

import java.util.Objects;
import java.util.Scanner;

public record OptionMenu(Scanner scanner, String[] options) {
    public OptionMenu {
        Objects.requireNonNull(scanner, "scanner");
        options = options == null ? new String[0] : options.clone();
    }

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

                if (option >= 1 && option <= options.length) {
                    mainMenuActive = false;
                } else {
                    System.out.println("Invalid choice, must be between 1 and " + options.length);
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and " + options.length);
                scanner.next();
            }
        }

        return option;
    }

    private void printOptions() {
        System.out.println("0. Exit");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public void menuExit(int areaMenuChoice) {
        if (areaMenuChoice == 0) {
            System.out.println("Goodbye!");
            System.exit(0);
        }
    }
}
