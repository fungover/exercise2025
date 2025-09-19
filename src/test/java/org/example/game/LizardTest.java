package org.example.game;

import org.example.enemies.Enemy;
import org.example.enemies.Falcon;
import org.example.player.Lizard;
import org.example.player.equipment.Equipment;
import org.example.player.equipment.Inventory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LizardTest {
	@Test
	void testLizard() {
		Lizard lizard = new Lizard("Lashy");
	}

	@Test
	void testInventory() {
		Inventory inventory = new Inventory();
	}

	@Test
	void testEquipment() {
		Equipment equipment = new Equipment();
	}

	@Test
	void testAddingItemToInventory() {
		Inventory inventory = new Inventory();
		inventory.addItem("Sword");

		assertTrue(inventory.getInventory().containsKey("Sword"));
		assertEquals(1, inventory.getInventory().get("Sword"));
	}

	@Test
	void testEquippingItem() {
		Lizard lizard = new Lizard("Lashy");
		Inventory inventory = new Inventory();
		Equipment equipment = new Equipment();

		inventory.addItem("Sword");
		lizard.equipItem("Sword", inventory, equipment);

		assertTrue(equipment.getEquipment().contains("Sword"));
	}

	@Test
	void testEquippingAnItemIncreasesDefense() {
		Lizard lizard = new Lizard("Lashy");
		Inventory inventory = new Inventory();
		Equipment equipment = new Equipment();

		inventory.addItem("Sword");
		lizard.equipItem("Sword", inventory, equipment);
		assertEquals(1, lizard.getDefense());
	}

	@Test
	void testEquippingMultipleItemsIncreasesDefense() {
		Lizard lizard = new Lizard("Lashy");
		Inventory inventory = new Inventory();
		Equipment equipment = new Equipment();

		inventory.addItem("Sword");
		inventory.addItem("Shield");
		inventory.addItem("Axe");
		lizard.equipItem("Sword", inventory, equipment);
		lizard.equipItem("Shield", inventory, equipment);
		lizard.equipItem("Axe", inventory, equipment);
		assertEquals(3, lizard.getDefense());
	}

	@Test
	void testFalconAttackingLizard() {
		Lizard lizard = new Lizard("Lashy");
		Enemy falcon = new Falcon();

		// Might be false because of 5% chance at critical hit from falcon
		lizard.setAttackedHealth(falcon.attack());
		assertEquals(25, lizard.getHealth());
	}

	@Test
	void testFalconAttackingLizardWithDefense() {
		Lizard lizard = new Lizard("Lashy");
		Enemy falcon = new Falcon();
		Inventory inventory = new Inventory();
		Equipment equipment = new Equipment();

		inventory.addItem("Sword");
		inventory.addItem("Shield");
		inventory.addItem("Axe");
		lizard.equipItem("Sword", inventory, equipment);
		lizard.equipItem("Shield", inventory, equipment);
		lizard.equipItem("Axe", inventory, equipment);

		lizard.setAttackedHealth(falcon.attack() - lizard.getDefense());
		assertEquals(28, lizard.getHealth());
		assertEquals(3, lizard.getDefense());
	}
}
