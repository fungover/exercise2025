package org.example;

import org.example.util.MenuUtil;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Electricity Price Tool Menu ===");

        Scanner scanner = new Scanner(System.in);
        String area = MenuUtil.getArea(scanner);
        int choice = MenuUtil.getChoice(scanner);

        MenuUtil.handleChoice(choice, area);

    }
}