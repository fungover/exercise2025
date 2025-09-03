package org.example.game;

import org.example.enteties.Eagle;
import org.example.enteties.Enemy;

import java.util.Scanner;

public class Game {
	public void run() {
		Scanner input = new Scanner(System.in);
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = input.nextLine();
		System.out.println("Hello, mighty " + name + "!");

		System.out.println("Killed Eagle");
		Enemy eagle = new Eagle();
		String eagleDrop = eagle.lootDrop();
		System.out.println("Eagle Drop: " + eagleDrop);
	}
}
