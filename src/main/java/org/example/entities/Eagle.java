package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Eagle extends Enemy {
	public Eagle() {
		randomGen = new RandomGen();
		random = new Random();
		super.health = 20;
		super.name = "Eagle";
	}

	public int attack() {
		if (randomGen.generateRandom(5)) {
			return 10; // Chance at critical hit
		} else {
			return 5; // Default damage for Eagles
		}
	}

	public String lootDrop() {
		return super.lootDrop();
	}
}
