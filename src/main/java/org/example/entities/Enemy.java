package org.example.entities;

public abstract class Enemy {
	private final String[] loot = {"Talon Dagger", "Beak Helmet", "Ashes", "Uncommon Feathers"};

	public String[] getLoot() {
		return loot;
	}

	public String getRareFeathers() {
		return "Phoenix Feathers";
	}

	abstract public int attack();
	abstract public String lootDrop();
	abstract public int getHealth();
}

