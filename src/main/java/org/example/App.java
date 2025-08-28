package org.example;

import org.example.util.MenuUtil;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Electricity Price Tool Menu ===");

        try (Scanner scanner = new Scanner(System.in)) {
            String area = MenuUtil.getArea(scanner);
            int choice;
            if (!area.equals("Exit")) {
                choice = MenuUtil.getChoice(scanner);
                MenuUtil.handleChoice(choice, area);
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

    }
}