package org.example.entities;

import java.util.ArrayList;
import java.util.List;

public class Lizard {
	private int health;
	private int defense;
	private String[] equipment;
	private List<String> inventory;
	private String name;

	public Lizard(String name) {
		this.name = name;
		health = 20;
		defense = 0;
		equipment = new String[4];
		inventory = new ArrayList<String>();
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	public int getDefense() {
		return defense;
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
