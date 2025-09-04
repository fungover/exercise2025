package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Falcon extends Enemy {
	Random random;
	RandomGen randomGen;

	public Falcon() {
		this.randomGen = new  RandomGen();
		this.random = new  Random();
	}

	public int attack() {
    if (randomGen.generateRandom(5)) {
			return 18;
		} else {
			return 8; // Standard hit damage for Falcons
		}
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
