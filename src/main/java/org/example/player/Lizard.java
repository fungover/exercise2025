package org.example.player;

import org.example.rng.RandomGen;

import java.util.ArrayList;
import java.util.List;

public class Lizard {
	private int health;
	private int defense;
	private final List<String> equipment;
	private final List<String> inventory;
	private final String name;
	private final RandomGen randomGen;

	public Lizard(String name) {
		this.name = name;
		health = 30;
		defense = 0;
		equipment = new ArrayList<>();
		inventory = new ArrayList<>();
		randomGen = new RandomGen();
	}

	public int attack() {
		if (randomGen.generateRandom(5)) {
			return 20;
		} else {
			return 10; // Default damage for player
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setAttackedHealth(int health) {
		this.health -= health;
	}

	public int getDefense() {
		return defense;
	}

	public String getName() {
		return name;
	}

	public void setDefense(int defenceBuff) {
		this.defense += defenceBuff;
	}

	public void setEquipment(String item) {
		if (item != null) {
			if (!this.equipment.contains(item)) {
				this.equipment.add(item);
				setDefense(1);
			}
		}
	}

	public List<String> getInventory() {
		return inventory;
	}

	public void setInventory(String item) {
		this.inventory.add(item);
	}
}
