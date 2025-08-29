package org.example.menu;

public class InputHandler {
    public int getUserChoice(int maxChoice) {
        try {
            String input = System.console().readLine();
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
        System.out.println(prompt);
        return System.console().readLine();
    }

    public double getDoubleInput(String prompt) {
        System.out.println(prompt);
        try {
            return Double.parseDouble(System.console().readLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return -1;
        }
    }
}
