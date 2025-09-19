package org.example.enemies;

import org.example.player.Lizard;
import org.example.player.equipment.Inventory;
import org.example.rng.RandomGen;

import java.util.Random;

public class Enemy {
	protected Random random = new Random();
	protected RandomGen randomGen = new RandomGen();
	protected int health;
	protected String name;
	protected String emoji;


	private final String[] loot = {"Talon Dagger", "Beak Helmet", "Ashes", "Hawk's Sight", "Scales"};

	public String[] getLoot() {
		return this.loot;
	}

	public String getRareFeathers() {
		return "Phoenix Feathers";
	}

	public int getHealth() {
		return this.health;
	}

	public void setHealth(int attack) {
		this.health += attack;
	}

	public String getName() {
		return this.name;
	}

	public String lootDrop() {
		int droppedLoot = random.nextInt(loot.length);
		return getLoot()[droppedLoot];
	}

	public int attack() {
		return 0;
	}

	public String getEmoji() {
		return this.emoji;
	}

	public int fight(Enemy entity, Lizard player, Inventory playerInventory) {
		System.out.println("\nA wild " + entity.getName() + " appeared " + entity.getEmoji());
		do {
			System.out.print("What will you do? ([R]un or [F]ight): ");
			String choice = System.console().readLine();
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
						switch (System.console().readLine()) {
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
								// Add heal logic here
								player.setHealth(30, playerInventory);
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
}

