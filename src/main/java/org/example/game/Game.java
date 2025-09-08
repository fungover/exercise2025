package org.example.game;

import org.example.entities.*;
import org.example.rng.RandomGen;
import org.example.tree.Leaf;

import java.util.Scanner;

public class Game {
	private Leaf currentLeaf;
	RandomGen randomGen;
	private Enemy eagle;
	private Enemy falcon;
	private Enemy phoenix;
	private Lizard lizard;
	private Enemy lootDrop;
	Scanner input = new Scanner(System.in);

	public Game() {
		createLeaves();
		randomGen = new RandomGen();
		lootDrop = new Enemy();
	}

	public void createLeaves() {
		Leaf greenLeaf = new Leaf("Greenwood", "[U]p or [L]eft");
		Leaf redLeaf = new Leaf("Red Hollow", "[R]ight or [D]own");
		Leaf orangeLeaf = new Leaf("Amberfield", "[R]ight or [L]eft");
		Leaf yellowLeaf = new Leaf("Yellow Meadow", "[U]p or [L]eft");
		Leaf goldenLeaf = new Leaf("Goldgrove", "[R]ight");
		Leaf purpleLeaf = new Leaf("Purple Vale", "[D]own or [H]ome");

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
		System.out.println("A wild " + entity.getName() + " appeared " + entity.getEmoji());
		do {
			System.out.print("What will you do? ([R]un or [F]ight): ");
			String choice = input.nextLine().toLowerCase().trim();
			switch (choice) {
				case "r":
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
									return 1;
								}
								player.setAttackedHealth(entity.attack() - player.getDefense());
								if (player.getHealth() <= 0) {
									return 0;
								}
								break;
							case "h":
								if (player.getInventory().contains("Ashes")) {
									System.out.println("You healed!");
									player.setHealth(30);
									player.getInventory().remove("Ashes");
								}
								break;
							case "r":
								return -1;
							default:
								System.out.println("Invalid choice!");
								break;
						}
					} while (true);
				default:
					System.out.println("Invalid choice!");
					break;
			}
		} while (true);
	}

	public void run() {
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = input.nextLine();
		lizard = new Lizard(name);
		System.out.println("Hello, mighty " + lizard.getName() + "!");


		boolean gameOn = true;
		while (gameOn) {
			System.out.println("Your current leaf: " + currentLeaf.getName());
			System.out.print("Your move (" + currentLeaf.getDescription() + "): ");
			String move = input.nextLine().toLowerCase().trim();
			if (currentLeaf.getNextLeaf(move) != null) {
				currentLeaf = currentLeaf.getNextLeaf(move);
			} else {
				System.out.println("No leaf there");
			}
			// Place enemy spawner here
			String foundItem = lootDrop.lootDrop();
			if (currentLeaf.getName().equals("Purple Leaf")) {
				phoenix = new Phoenix();
				int phoenixFight = fight(phoenix, lizard);
				if (phoenixFight == 0) {
					System.out.println("Phoenix won!");
					gameOn = false;
				} else if (phoenixFight == 1) {
					System.out.println("Phoenix defeated and you gained " + phoenix.getRareFeathers() + "!");
					lizard.setInventory(phoenix.getRareFeathers());
				} else {
					System.out.println("You ran away!");
				}
			} else {
				if (randomGen.generateRandom(25)) {
					int fight;
					if (randomGen.generateRandom(50)) {
						falcon = new Falcon();
						fight = fight(falcon, lizard);
					} else {
						eagle = new Eagle();
						fight = fight(eagle, lizard);
					}
					if (fight == 0) {
						System.out.println("You died and lost the game!");
						gameOn = false;
					} else if (fight == -1) {
						System.out.println("You ran away from the threat!");
					} else {
						System.out.println("You won and gained: " + foundItem + "!");
						lizard.setInventory(foundItem);
					}
				} else {
					System.out.println("You found an item: " + foundItem + "!");
					lizard.setInventory(foundItem);
					lizard.setEquipment(foundItem);
				}

			}
		}
	}
}

