package org.example.entities;

import org.example.rng.RandomGen;

import java.util.ArrayList;
import java.util.List;

public class Lizard {
	private int health;
	private double defense;
	private List<String> equipment;
	private List<String> inventory;
	private String name;
	private RandomGen randomGen;

	public Lizard(String name) {
		this.name = name;
		health = 30;
		defense = 1;
		equipment = new ArrayList<String>();
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
		this.health = health;
	}

	public void setAttackedHealth(int health) {
		this.health -= health;
	}

	public double getDefense() {
		return defense;
	}

	public String getName() {
		return name;
	}

	public void setDefense(double defenceBuff) {
		this.defense -= defenceBuff;
	}

	public void setEquipment(String item) {
		if (item != null) {
			if (!this.equipment.contains(item)) {
				this.equipment.add(item);
				setDefense(0.2);
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
