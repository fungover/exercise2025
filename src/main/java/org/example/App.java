package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
			Scanner input = new Scanner(System.in);
			System.out.println("🦎 Little Leaf Lizards 🍃");
			System.out.print("Your reptile name?: ");
			String name = input.nextLine();
			System.out.println("Hello, mighty " + name + "!");
    }
}
