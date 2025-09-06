package org.example.game;

import org.example.entities.Eagle;
import org.example.entities.Enemy;
import org.example.entities.Falcon;
import org.example.entities.Lizard;
import org.example.rng.RandomGen;
import org.example.tree.Leaf;

import java.util.Scanner;

public class Game {
	private Leaf currentLeaf;
	RandomGen randomGen;
	private Enemy eagle;
	private Enemy falcon;
	private Lizard lizard;
	private Enemy lootDrop;
	Scanner input = new Scanner(System.in);

	public Game() {
		createLeaves();
		randomGen = new RandomGen();
		lootDrop = new Enemy();
	}

	public void createLeaves() {
		Leaf greenLeaf = new Leaf("Green Leaf", "[U]p or [L]eft");
		Leaf redLeaf = new Leaf("Red Leaf", "[R]ight or [D]own");
		Leaf orangeLeaf = new Leaf("Orange Leaf", "[R]ight or [L]eft");
		Leaf yellowLeaf = new Leaf("Yellow Leaf", "[U]p or [L]eft");
		Leaf purpleLeaf = new Leaf("Purple Leaf", "[D]own or [H]ome");
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

	public int fight(Enemy entity, Lizard player) {
		System.out.println("A wild " + entity.getName() + " appeared 🐦");
		System.out.print("What will you do? ([R]un or [F]ight): ");
		String choice = input.nextLine().toLowerCase().trim();
		boolean enemyMenu = true;
		while (enemyMenu) {
			switch (choice) {
				case "r":
					System.out.println("You run away from the threat!");
					return -1;
				case "f":
					do {
						System.out.println(player.getName() + " health: " + player.getHealth());
						System.out.println(entity.getName() + " health: " + entity.getHealth());
						System.out.println("What will you do!");
						System.out.println("[A]ttack");
						System.out.println("[H]eal");
						System.out.println("[R]un");
						switch (input.nextLine().toLowerCase().trim()) {
							case "a":
								entity.setHealth(-player.attack());
								if (entity.getHealth() <= 0) {
									String loot = entity.lootDrop();
									player.getInventory().add(loot);
									System.out.println("You won and gained: " + loot  + "!");
									return 1;
								}
								player.setHealth(-entity.attack());
								if  (player.getHealth() <= 0) {
									System.out.println("You lost!");
									return 0;
								}
								break;
							case "h":
								if (player.getInventory().contains("Ashes")) {
									System.out.println("You healed!");
									player.setHealth(10);
								}
								break;
							case "r":
								System.out.println("You run away from the threat!");
								return -1;
							default:
								System.out.println("Invalid choice!");
								break;
						}
					} while (true);
				default:
					System.out.println("Invalid choice");
					break;
			}
		}
		if (player.getHealth() <= 0) {
			return 0;
		} else return 1;
	}

	public void run() {
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = input.nextLine();
		lizard = new Lizard(name);
		System.out.println("Hello, mighty " + lizard.getName() + "!");

		System.out.println("Your current leaf " + currentLeaf.getName());

		boolean gameOn = true;
		while (gameOn) {
			// Place enemy spawner here
			if (randomGen.generateRandom(50)) {
				if (randomGen.generateRandom(50)) {
					falcon = new Falcon();
					fight(falcon, lizard);
				}
				 else {
					eagle = new Eagle();
					fight(eagle, lizard);
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

