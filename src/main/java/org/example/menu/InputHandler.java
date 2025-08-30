package org.example.menu;

import java.util.Scanner;

public class InputHandler {

        private final Scanner scanner;
        public InputHandler() {
            this(new Scanner(System.in));
        }
        public InputHandler(Scanner scanner) {
            this.scanner = java.util.Objects.requireNonNull(scanner, "scanner");
        }

        public int getUserChoice(int maxChoice) {
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("No input available");
                    return -1;
                }
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {
            } catch (IllegalStateException | java.util.NoSuchElementException e) {
            System.out.println("Input stream closed");
            return -1;
            }
            System.out.println("Please enter a number between 1 and " + maxChoice);
            return -1;
        }

        public String getStringInput(String prompt) {
        if (prompt != null && !prompt.isEmpty()) {
                System.out.println(prompt);
            }
            if (!scanner.hasNextLine()) {
                System.out.println("No input available");
                return "";
            }
            return scanner.nextLine().trim();
        }

        public double getDoubleInput(String prompt) {
            if (!prompt.isEmpty()) {
                System.out.println(prompt);
            }
            while (true) {
                try {
                    if (!scanner.hasNextLine()) {
                        System.out.println("No input available");
                        return -1;
                        }
                    String line = scanner.nextLine().trim();
                    return Double.parseDouble(line);
                    } catch (NumberFormatException e) {
                    System.out.println("Invalid number, please try again");
                    } catch (IllegalStateException | java.util.NoSuchElementException e) {
                    System.out.println("Input stream closed");
                    return -1;
                    }
            }
        }
}
