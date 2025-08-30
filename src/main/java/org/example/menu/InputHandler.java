package org.example.menu;

import java.util.Scanner;

public class InputHandler {

        public final Scanner scanner;
        public InputHandler() {
            this.scanner = new Scanner(System.in);
        }

        public int getUserChoice(int maxChoice) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println("Please enter a number between 1 and " + maxChoice);
            return -1;
        }

        public String getStringInput(String prompt) {
            if (!prompt.isEmpty()) {
                System.out.println(prompt);
            }
            return scanner.nextLine().trim();
        }

        public double getDoubleInput(String prompt) {
            if (!prompt.isEmpty()) {
                System.out.println(prompt);
            }
            while (true) {
                try {
                    return Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number, please try again");
                }
            }
        }
}
