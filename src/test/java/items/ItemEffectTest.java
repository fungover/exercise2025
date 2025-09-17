package items;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import entities.Player;
import utils.Constants;

@DisplayName("Item Effects Tests")
class ItemEffectsTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Test Player", Constants.PLAYER_STARTING_HEALTH, Constants.PLAYER_STARTING_DAMAGE);
    }

    @Test
    @DisplayName("RumBottle should heal player")
    void testRumBottleHealing() {
        // Arrange
        player.takeDamage(50); // Reduce player health
        int healthBefore = player.getCurrentHealth();
        RumBottle rum = new RumBottle("stark", 30);

        // Act
        String result = rum.use(player);

        // Assert
        assertEquals(healthBefore + 30, player.getCurrentHealth(), "Player should be healed by 30 HP");
        assertTrue(result.contains("återfår"), "Should show healing message");
        assertTrue(result.contains("30"), "Should show amount healed");
    }

    @Test
    @DisplayName("RumBottle should not overheal")
    void testRumBottleNoOverheal() {
        // Arrange
        player.takeDamage(10); // Player has 90 HP
        RumBottle rum = new RumBottle("stark", 50); // Would heal to 140, but max is 100

        // Act
        String result = rum.use(player);

        // Assert
        assertEquals(Constants.PLAYER_STARTING_HEALTH, player.getCurrentHealth(),
                "Player should not exceed max health");
        assertTrue(result.contains("återfår"), "Should show healing message");
    }

    @Test
    @DisplayName("RumBottle should indicate if player is at full health")
    void testRumBottleFullHealth() {
        // Arrange - Player is already at full health
        RumBottle rum = new RumBottle("svag", 20);

        // Act
        String result = rum.use(player);

        // Assert
        assertEquals(Constants.PLAYER_STARTING_HEALTH, player.getCurrentHealth(),
                "Player health should remain at max");
        assertTrue(result.contains("full styrka"), "Should indicate player is at full health");
    }

    @Test
    @DisplayName("OldSword should increase player damage when equipped")
    void testOldSwordEquip() {
        // Arrange
        int baseDamage = player.getTotalDamage();
        OldSword sword = new OldSword("Testsvärd", 10);

        // Act
        String result = sword.use(player);

        // Assert
        assertEquals(baseDamage + 10, player.getTotalDamage(), "Player damage should increase by 10");
        assertTrue(result.contains("beväpnad"), "Should show equipped message");
        assertTrue(sword.isEquipped(), "Sword should be marked as equipped");
    }

    @Test
    @DisplayName("OldSword should decrease player damage when unequipped")
    void testOldSwordUnequip() {
        // Arrange
        OldSword sword = new OldSword("Testsvärd", 10);
        sword.use(player); // Equip first
        int damageWithSword = player.getTotalDamage();

        // Act
        String result = sword.use(player); // Unequip

        // Assert
        assertEquals(damageWithSword - 10, player.getTotalDamage(), "Player damage should decrease by 10");
        assertTrue(result.contains("stoppar undan"), "Should show unequipped message");
        assertFalse(sword.isEquipped(), "Sword should not be marked as equipped");
    }

    @Test
    @DisplayName("GoldCoin should not affect player stats")
    void testGoldCoinUse() {
        // Arrange
        int healthBefore = player.getCurrentHealth();
        int damageBefore = player.getTotalDamage();
        GoldCoin coin = new GoldCoin(25);

        // Act
        String result = coin.use(player);

        // Assert
        assertEquals(healthBefore, player.getCurrentHealth(), "Health should not change");
        assertEquals(damageBefore, player.getTotalDamage(), "Damage should not change");
        assertTrue(result.contains("25 guld"), "Should show coin value");
        assertTrue(result.contains("inte använda"), "Should indicate cannot be used");
    }

    @Test
    @DisplayName("MagicKey should provide usage instructions")
    void testMagicKeyUse() {
        // Arrange
        MagicKey key = new MagicKey();

        // Act
        String result = key.use(player);

        // Assert
        assertTrue(result.contains("blå energi"), "Should describe magical properties");
        assertTrue(result.contains("unlock"), "Should mention unlock command");
        assertFalse(key.isConsumable(), "Magic key should not be consumable");
    }

    @Test
    @DisplayName("PirateTreasure should show value and description")
    void testPirateTreasureUse() {
        // Arrange
        PirateTreasure treasure = new PirateTreasure("Juvelskrin", "Fyllt med ädelstenar", 100);

        // Act
        String result = treasure.use(player);

        // Assert
        assertTrue(result.contains("Juvelskrin"), "Should show treasure type");
        assertTrue(result.contains("100 guld"), "Should show value");
        assertTrue(result.contains("ädelstenar"), "Should show description");
    }

    @Test
    @DisplayName("Items should have correct consumable properties")
    void testItemConsumableProperties() {
        // Arrange & Assert
        assertTrue(new RumBottle("test", 10).isConsumable(), "Rum should be consumable");
        assertFalse(new OldSword("test", 5).isConsumable(), "Sword should not be consumable");
        assertFalse(new GoldCoin(10).isConsumable(), "Gold coin should not be consumable");
        assertFalse(new MagicKey().isConsumable(), "Magic key should not be consumable");
        assertFalse(new PirateTreasure("test", "desc", 50).isConsumable(), "Treasure should not be consumable");
    }
}
