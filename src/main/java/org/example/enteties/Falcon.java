package org.example.enteties;

import java.util.Random;

public class Falcon extends Enemy {
	Random random = new Random();

	public int attack() {

		if (random.nextInt(20) + 1 == 11) {
			return 10;
		}
		return random.nextInt(8) + 1;
	}

	public String lootDrop() {
		if (random.nextInt(20) + 1 == 11) {
			return getRareFeathers();
		}
		if (random.nextInt(2) + 1 == 1) {
			return getLoot()[0];
		} else {
			return getLoot()[1];
		}
	}
}
