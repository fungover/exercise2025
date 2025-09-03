package org.example.enteties;

public abstract class Enemy {
	private final String[] loot = {"Talon Dagger", "Beak Helmet"};

	public String[] getLoot() {
		return loot;
	}

	public String getRareFeathers() {
		String rareFeathers = "Phoenix Feathers";
		return rareFeathers;
	}

	abstract public int attack();
	abstract public String lootDrop();
}

