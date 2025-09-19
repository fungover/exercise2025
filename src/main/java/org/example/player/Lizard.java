// Lizard.java
package org.example.player;

import org.example.player.equipment.Equipment;
import org.example.player.equipment.Inventory;
import org.example.rng.RandomGen;

public class Lizard {
	private int health;
	private int defense;
	private final String name;
	private final RandomGen randomGen;

	public Lizard(String name) {
		this.name = name;
		health = 30;
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

	public void setHealth(int health, Inventory inventory) {
	 if (inventory != null && inventory.getInventory().containsKey("Ashes")) {
		 this.health = health;
		 int count = inventory.getInventory().get("Ashes");
		 if (count > 1) {
			 inventory.getInventory().put("Ashes", count - 1);
		 } else {
			 inventory.getInventory().remove("Ashes");
		 }
	 }
	}

	public void setAttackedHealth(int health) {
		this.health -= health;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(Equipment equipment) {
		defense = 0;
		for (String equipped : equipment.getEquipment()) {
			this.defense += 1;
		}
	}

	public String getName() {
		return name;
	}

	public void equipItem(String item, Inventory inventory,  Equipment equipment) {
		if (inventory.getInventory().containsKey(item)) {
			if (equipment.getEquipment().contains(item)) {
				System.out.println("Item already equipped");
			} else {
				equipment.setEquipment(item);
				int count = inventory.getInventory().get(item);
				if (count > 1) {
					inventory.getInventory().put(item, count - 1);
				} else {
					inventory.getInventory().remove(item);
				}
			}
			setDefense(equipment);
		} else {
			System.out.println("Item " + item + " not found");
		}
	}

	public void equippedItems(Equipment equipment) {
		equipment.printEquipment();
	}
}
