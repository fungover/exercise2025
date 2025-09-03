package org.example.enteties;

import java.util.Random;

public class Eagle extends Enemy {
	Random random = new Random();

	public int attack() {

		if (random.nextInt(20) + 1 == 11) {
			return 15;
		}
		return random.nextInt(10) + 1;
	}

	public String lootDrop() {
		if (random.nextInt(2) + 1 == 1) {
			return getLoot()[0];
		} else {
			return getLoot()[1];
		}
	}
}
