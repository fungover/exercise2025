package org.example.service;

import org.example.entities.characters.Player;
import org.example.entities.items.Item;
import org.example.entities.items.Potion;
import org.example.entities.items.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {
    private ItemService itemService;
    private Player player;
    private Weapon sword;
    private Weapon axe;
    private Potion potion;
    private InputService mockInputService;

    @BeforeEach
    void setUp() {
        itemService = new ItemService();
        sword = new Weapon("Test Sword", "Test Description", 10, 15);
        player = new Player("TestPlayer", 100, 100, 2, 2, sword);

        mockInputService = mock(InputService.class);
    }

    @Test
    @DisplayName("Test that old weapon is added to inventory when equipping a new weapon")
    void testEquipWeaponFromInventory() {
        axe = new Weapon("Test Axe", "Test Description", 15, 10);
        player.getInventory().addItem(axe);

        when(mockInputService.readLine()).thenReturn("1", "1");

        itemService.openInventoryMenu(player, mockInputService);

        System.out.println("Inventory contains:");
        for (Item item : player.getInventory().getItems()) {
            System.out.println("- " + item.getName());
        }

        assertEquals(axe, player.getEquippedWeapon());
        assertTrue(player.getInventory().getItems().contains(sword));
        assertEquals(1, player.getInventory().getItems().size());

    }

    @Test
    @DisplayName("Test that potion is removed after using it")
    void testUsePotionFromInventory() {
        potion = new Potion("Test Potion", "Test Description", 10, 20);
        player.getInventory().addItem(potion);

        when(mockInputService.readLine()).thenReturn("2");

        itemService.openInventoryMenu(player, mockInputService);

        assertEquals(0, player.getInventory().getItems().size());
    }
}
