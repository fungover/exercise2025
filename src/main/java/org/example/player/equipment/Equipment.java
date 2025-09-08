package org.example.player.equipment;

import java.util.ArrayList;
import java.util.List;

public class Equipment {
	 private final List<String> equipment = new ArrayList<String>();

	public void setEquipment(String item) {
		if (equipment.contains(item)) {
			System.out.println(item + " is already equipped");
		} else  {
			equipment.add(item);
		}
	}
	public void printEquipment() {
		for (String item : equipment) {
			System.out.println(item);
		}
	}

}
