package org.example.rng;

import java.util.Random;

public class RandomGen {
	private Random random;
	public RandomGen() {
		random = new Random();
	}

	public boolean generateRandom(int chance) {
		if (random.nextInt(100) <= chance) {
			return true;
		} else {
			return false;
		}
	}
}
