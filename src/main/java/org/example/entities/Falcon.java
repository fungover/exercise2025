package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class Falcon extends Enemy {
	public Falcon() {
		randomGen = new RandomGen();
		random = new Random();
		super.health = 20;
		super.name = "Falcon";
	}

	public int attack() {
		if (randomGen.generateRandom(5)) {
			return 12;
		} else {
			return 5; // Default damage for Falcons
		}
	}


	public String lootDrop() {
		return super.lootDrop();
	}

}
