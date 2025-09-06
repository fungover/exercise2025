package org.example.entities;

import org.example.rng.RandomGen;

import java.util.Random;

public class FinalBossPhoenix extends Enemy {
	int health;
	RandomGen randomGen;
	Random random;

	public FinalBossPhoenix() {
		health = 50;
		randomGen = new  RandomGen();
		random = new  Random();
	}

	public int attack() {
		if (randomGen.generateRandom(25)) {
			return 20;
		} else
			return 15; //Default damage for FinalBossPhoenix
	}

	public String lootDrop() {
		return getRareFeathers();
	}
	public int  getHealth() {
		return health;
	}
}
