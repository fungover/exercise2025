// Inventory.java
package org.example.player.equipment;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
	private final Map<String, Integer> inventory;

	public Inventory() {
		inventory = new HashMap<String, Integer>();
	}

	public Map<String, Integer> getInventory() {
		return inventory;
	}

	public void addItem(String item) {
		if (inventory.containsKey(item)) {
			int count = inventory.get(item);
			inventory.put(item, count + 1);
		} else {
			inventory.put(item, 1);
		}
	}

	public void printItems() {
		for (String item : inventory.keySet()) {
			System.out.println(item + " " + inventory.get(item));
		}
	}
}
