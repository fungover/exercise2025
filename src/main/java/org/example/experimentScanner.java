package org.example;

import java.util.Scanner;

public class experimentScanner {
    public static void main(String[] args) {

        System.out.println("Skriv in ditt namn:");
        String name = readTextFromUser();
        System.out.println("Skriv in din ålder:");
        int age = readIntFromUser();
        System.out.println("Hej " + name + " du är " + age + " år gammal");
        scanner.close();
    }

    public static Scanner scanner = new Scanner(System.in);

    public static int readIntFromUser() {
        String numberText = scanner.nextLine();
        int number = Integer.parseInt(numberText);
        return number;
    }

    public static String readTextFromUser() {
        String text = scanner.nextLine();
        return text;
    }
}
