package org.example.menus;
import java.util.Scanner;

public record AreaMenu(Scanner scanner, String[] AREAS) {

    public String getArea() {
        boolean areaMenuActive = true;
        int area = -1;

        while (areaMenuActive) {
            printOptions();
            System.out.print("Enter your choice: \n");

            if (scanner.hasNextInt()) {
                area = scanner.nextInt();

                if (area >= 1 && area <= AREAS.length) {
                    areaMenuActive = false;
                } else {
                    System.out.println("Invalid choice, must be between 1 and " + AREAS.length);
                }
            } else {
                System.out.println("Invalid choice, must be a number between 1 and " + AREAS.length);
                scanner.next();
            }
        }

        return "SE" + area;
    }

    private void printOptions() {
        for (int i = 0; i < AREAS.length; i++) {
            System.out.println((i + 1) + ". " + AREAS[i]);
        }
    }
}
