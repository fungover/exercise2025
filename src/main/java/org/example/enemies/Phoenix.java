package org.example.enemies;

import org.example.rng.RandomGen;

import java.util.Random;

public class Phoenix extends Enemy {
	public Phoenix() {
		randomGen = new RandomGen();
		random = new Random();
		super.health = 50;
		super.name = "Phoenix";
		super.emoji = "🐦🔥";
	}

	public int attack() {
		return 12;
	}
}
