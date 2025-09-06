package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Enemy {
	protected Random random = new Random();
	protected RandomGen randomGen = new RandomGen();
	protected int health;
	protected String name;
	protected String emoji;
	public Enemy() {
		this.name = "lootDrop";
	}

	private final String[] loot = {"Talon Dagger", "Beak Helmet", "Ashes", "Uncommon Feathers", "Hawk's Sight"};

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
		int droppedLoot = random.nextInt(5);
		return getLoot()[droppedLoot];
	}

	public int attack() {
		return 0;
	}

	public String getEmoji() {
		return this.emoji;
	}
}

