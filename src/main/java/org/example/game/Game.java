package org.example.game;

import org.example.enemies.*;
import org.example.player.Lizard;
import org.example.player.equipment.Equipment;
import org.example.player.equipment.Inventory;
import org.example.rng.RandomGen;
import org.example.tree.Leaf;
import org.example.tree.Leaves;

public class Game {
	private final RandomGen randomGen;

	private final Leaves leaves;

	private final Enemy enemy;

	private final Inventory inventory;
	private final Equipment equipment;

	public Game() {
		leaves = new Leaves();
		leaves.createLeaves();
		randomGen = new RandomGen();
		enemy = new Enemy();
		inventory = new Inventory();
		equipment = new Equipment();
	}

	public void run() {
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = System.console().readLine();
		Lizard lizard = new Lizard(name);
		System.out.println("Hello, mighty " + lizard.getName() + "!");


		boolean gameOn = true;
		while (gameOn) {
			Leaf currentLeaf = leaves.getCurrentLeaf();
			System.out.println("Your current leaf: " + currentLeaf.getName());
			System.out.println("Movement: " + currentLeaf.getDescription() + ":");
			System.out.println("Actions: [I]nventory, [E]quip Item [EQ]uipped:");
			System.out.print("Your choice: ");
			String move = System.console().readLine().toLowerCase();
			if (move.equals("i") || move.equals("e") || move.equals("eq")) {
				if (move.equals("i")) {
					System.out.println("Inventory:");
					inventory.printItems();
				} else if (move.equals("e")) {
					System.out.println("""
									[1] Talon Dagger
									[2] Beak Helmet
									[3] Ashes
									[4] Hawk's Sight
									[5] Scales
									""");
					System.out.print("What do you want to equip?: ");
					String item = System.console().readLine().toLowerCase();
					switch (item) {
						case "1":
							lizard.equipItem(enemy.getLoot()[0], inventory, equipment);
							lizard.setDefense(equipment);
							break;
						case "2":
							lizard.equipItem(enemy.getLoot()[1], inventory, equipment);
							lizard.setDefense(equipment);
							break;
						case "3":
							lizard.equipItem(enemy.getLoot()[2], inventory, equipment);
							lizard.setDefense(equipment);
							break;
						case "4":
							lizard.equipItem(enemy.getLoot()[3], inventory, equipment);
							lizard.setDefense(equipment);
							break;
						case "5":
							lizard.equipItem(enemy.getLoot()[4], inventory, equipment);
							lizard.setDefense(equipment);

						default:
							System.out.println("Invalid Input");
					}
				} else {
					lizard.equippedItems(equipment);
				}
			} else {
				currentLeaf = leaves.move(currentLeaf, move);// Place enemy spawner here
				String foundItem = enemy.lootDrop();
				if (currentLeaf.getName().equals("Purple Vale")) {
					Enemy phoenix = new Phoenix();
					int phoenixFight = enemy.fight(phoenix, lizard, inventory);
					if (phoenixFight == 0) {
						System.out.println("Phoenix won!");
						gameOn = false;
					} else if (phoenixFight == 1) {
						System.out.println("Phoenix defeated and you gained " + phoenix.getRareFeathers() + "!");
						// Put rare feathers in inventory
						inventory.addItem(phoenix.getRareFeathers());
					} else {
						System.out.println("You ran away!");
						leaves.move(currentLeaf, "d");
					}
				} else {
					if (randomGen.generateRandom(25)) {
						int fight;
						if (randomGen.generateRandom(50)) {
							Enemy falcon = new Falcon();
							fight = enemy.fight(falcon, lizard, inventory);
						} else {
							Enemy eagle = new Eagle();
							fight = enemy.fight(eagle, lizard, inventory);
						}
						if (fight == 0) {
							System.out.println("You died and lost the game!");
							gameOn = false;
						} else if (fight == -1) {
							System.out.println("You ran away from the threat!");
						} else {
							System.out.println("\nYou won and gained: " + foundItem + "!");
							// Place the item in the inventory
							inventory.addItem(foundItem);
						}
					} else {
						System.out.println("\nYou found an item: " + foundItem + "!");
						// Place the item in the inventory
						inventory.addItem(foundItem);
					}
				}
			}
		}
	}
}

