package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Enemy {
	protected Random random = new Random();
	protected RandomGen randomGen = new RandomGen();
	protected int health;
	protected String name;
	public Enemy() {
		this.name = "lootDrop";
	}

	private final String[] loot = {"Talon Dagger", "Beak Helmet", "Ashes", "Uncommon Feathers"};

	public String[] getLoot() {
		return loot;
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
		return name;
	}

	public String lootDrop() {
		int droppedLoot = random.nextInt(4);
		return getLoot()[droppedLoot];
	}

	public int attack() {
		System.out.println("attack");
		return 0;
	}
}

