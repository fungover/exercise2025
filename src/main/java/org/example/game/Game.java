package org.example.game;

import org.example.enemies.*;
import org.example.player.Lizard;
import org.example.rng.RandomGen;
import org.example.tree.Leaf;
import org.example.tree.Leaves;

public class Game {
	private RandomGen randomGen;

	private Leaf currentLeaf;
	private Leaves leaves = new Leaves();

	private Enemy eagle;
	private Enemy falcon;
	private Enemy phoenix;
	private Enemy enemy;

	private Lizard lizard;

	public Game() {
		leaves.createLeaves();
		currentLeaf = leaves.currentLeaf();
		randomGen = new RandomGen();
		enemy = new Enemy();
	}

	public void run() {
		System.out.println("🦎 Little Leaf Lizards 🍃");
		System.out.print("Your reptile name?: ");
		String name = System.console().readLine();
		lizard = new Lizard(name);
		System.out.println("Hello, mighty " + lizard.getName() + "!");


		boolean gameOn = true;
		while (gameOn) {
			leaves.move();
			currentLeaf = leaves.currentLeaf();
			// Place enemy spawner here
			String foundItem = enemy.lootDrop();
			if ( currentLeaf.getName().equals("Purple Vale")) {
				phoenix = new Phoenix();
				int phoenixFight = enemy.fight(phoenix, lizard);
				if (phoenixFight == 0) {
					System.out.println("Phoenix won!");
					gameOn = false;
				} else if (phoenixFight == 1) {
					System.out.println("Phoenix defeated and you gained " + phoenix.getRareFeathers() + "!");
					lizard.setInventory(phoenix.getRareFeathers());
				} else {
					System.out.println("You ran away!");
					leaves.move("d");
				}
			} else {
				if (randomGen.generateRandom(25)) {
					int fight;
					if (randomGen.generateRandom(50)) {
						falcon = new Falcon();
						fight = enemy.fight(falcon, lizard);
					} else {
						eagle = new Eagle();
						fight = enemy.fight(eagle, lizard);
					}
					if (fight == 0) {
						System.out.println("You died and lost the game!");
						gameOn = false;
					} else if (fight == -1) {
						System.out.println("You ran away from the threat!");
					} else {
						System.out.println("\nYou won and gained: " + foundItem + "!");
						lizard.setInventory(foundItem);
					}
				} else {
					System.out.println("\nYou found an item: " + foundItem + "!");
					lizard.setInventory(foundItem);
					lizard.setEquipment(foundItem);
				}

			}
		}
	}
}

