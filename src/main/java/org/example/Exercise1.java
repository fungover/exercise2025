package org.example;

import java.util.Scanner;

public class Exercise1 {
    static void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Your name: ");
        String name = System.console().readLine();
        System.out.println("Hej " + name);
    }
}



