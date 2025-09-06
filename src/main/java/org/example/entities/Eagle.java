package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Eagle extends Enemy {
	RandomGen random;
	Random randomItem;
	int health;

	public Eagle() {
		random = new RandomGen();
		randomItem = new Random();
		health = 20;
	}

	public int attack() {

		if (random.generateRandom(5)) {
			return 15; // Chance at critical hit
		} else {
			return 5; // Default damage for Eagles
		}
	}

	public int getHealth() {
		return health;
	}

	public String lootDrop() {
		int droppedLoot = randomItem.nextInt(4) + 1;
		if (random.generateRandom(75)) {
			return getLoot()[droppedLoot];
		} else {
			return "No loot";
		}
	}
}
