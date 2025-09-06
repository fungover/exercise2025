package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Falcon extends Enemy {
	private final Random random;
	private final RandomGen randomGen;
	private int health;

	public Falcon() {
		this.randomGen = new  RandomGen();
		this.random = new  Random();
		health = 20;
	}

	public int attack() {
    if (randomGen.generateRandom(5)) {
			return 18;
		} else {
			return 8; // Default damage for Falcons
		}
	}
	public int getHealth() {
		return health;
	}

	public String lootDrop() {
		int droppedLoot = random.nextInt(4) + 1;
		if (randomGen.generateRandom(95)) {
			return getLoot()[droppedLoot];
		} else {
			return "No loot";
		}
	}
}
