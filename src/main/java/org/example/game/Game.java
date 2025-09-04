package org.example.game;

import org.example.entities.Eagle;
import org.example.entities.Enemy;
import org.example.entities.Falcon;
import org.example.rng.RandomGen;
import org.example.tree.Leaf;

import java.util.Scanner;

public class Game {
	private Leaf currentLeaf;
	RandomGen randomGen;
	private Enemy eagle;
	private Enemy falcon;

	public Game() {
		createLeaves();
		randomGen = new RandomGen();
		eagle = new Eagle();
		falcon = new Falcon();
	}

	public void createLeaves() {
		Leaf greenLeaf = new Leaf("Green Leaf", "[U]p or [L]eft");
		Leaf redLeaf = new Leaf("Red Leaf", "[R]ight or [D]own");
		Leaf orangeLeaf = new Leaf("Orange Leaf", "[R]ight or [L]eft");
		Leaf yellowLeaf = new Leaf("Yellow Leaf", "[U]p or [L]eft");
		Leaf purpleLeaf = new Leaf("Purple Leaf", "[Down] or [H]ome");
		Leaf goldenLeaf = new Leaf("Golden Leaf", "[R]ight");

		greenLeaf.setNextLeaf("u", redLeaf);
		greenLeaf.setNextLeaf("l", orangeLeaf);

		redLeaf.setNextLeaf("r", yellowLeaf);
		redLeaf.setNextLeaf("d", greenLeaf);

		orangeLeaf.setNextLeaf("r", greenLeaf);
		orangeLeaf.setNextLeaf("l", goldenLeaf);

		goldenLeaf.setNextLeaf("r", orangeLeaf);

		yellowLeaf.setNextLeaf("u", purpleLeaf);
		yellowLeaf.setNextLeaf("l", redLeaf);

		purpleLeaf.setNextLeaf("d", yellowLeaf);
		purpleLeaf.setNextLeaf("h", greenLeaf);

		currentLeaf = greenLeaf;
	}

	public void run() {
		Scanner input = new Scanner(System.in);
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = input.nextLine();
		System.out.println("Hello, mighty " + name + "!");

		System.out.println("Your current leaf " + currentLeaf.getName());

		boolean gameOn = true;
		while (gameOn) {
			// Place enemy spawner here
			if (randomGen.generateRandom(33)) {
				if (randomGen.generateRandom(50)) {
					System.out.println("Loot dropped: " + falcon.lootDrop());
				} else {
					System.out.println("Loot dropped: " + eagle.lootDrop());
				}
			}
			System.out.print("Your move (" + currentLeaf.getDescription() + "): ");
			String move = input.nextLine().toLowerCase().trim();
			if (currentLeaf.getNextLeaf(move) != null) {
				currentLeaf = currentLeaf.getNextLeaf(move);
				System.out.println(currentLeaf.getName());
			} else {
				System.out.println("No leaf there");
			}
		}
	}
}
