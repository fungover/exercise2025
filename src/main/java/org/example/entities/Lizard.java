package org.example.entities;

import org.example.rng.RandomGen;

import java.util.ArrayList;
import java.util.List;

public class Lizard {
	private int health;
	private int defense;
	private String[] equipment;
	private List<String> inventory;
	private String name;
	private RandomGen randomGen;

	public Lizard(String name) {
		this.name = name;
		health = 30;
		defense = 0;
		equipment = new String[4];
		inventory = new ArrayList<String>();
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
		this.health += health;
	}

	public int getDefense() {
		return defense;
	}

	public String getName() {
		return name;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public String[] getEquipment() {
		return equipment;
	}

	public void setEquipment(String[] equipment) {
		this.equipment = equipment;
	}

	public List<String> getInventory() {
		return inventory;
	}

	public void setInventory(String item) {
		this.inventory.add(item);
	}
}
