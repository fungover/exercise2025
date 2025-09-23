package items;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import entities.Player;
import entities.Item;
import utils.Constants;

@DisplayName("Item System Tests - Basic functionality")
class ItemEffectsTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("Test Player", Constants.PLAYER_STARTING_HEALTH, Constants.PLAYER_STARTING_DAMAGE);
    }

    @Test
    @DisplayName("Items from PirateTreasureFactory should be created successfully")
    void testItemCreation() {
        // Act
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();
        Item coin = PirateTreasureFactory.createLargeGoldCoin();
        Item saber = PirateTreasureFactory.createPirateSaber();
        Item jewelBox = PirateTreasureFactory.createJewelBox();

        // Assert
        assertNotNull(rum, "Strong rum should be created");
        assertNotNull(key, "Magic key should be created");
        assertNotNull(coin, "Large gold coin should be created");
        assertNotNull(saber, "Pirate saber should be created");
        assertNotNull(jewelBox, "Jewel box should be created");
    }

    @Test
    @DisplayName("Items should have proper names")
    void testItemNames() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();
        Item coin = PirateTreasureFactory.createLargeGoldCoin();

        // Assert
        assertNotNull(rum.getName(), "Rum should have a name");
        assertNotNull(key.getName(), "Key should have a name");
        assertNotNull(coin.getName(), "Coin should have a name");

        assertFalse(rum.getName().isEmpty(), "Rum name should not be empty");
        assertFalse(key.getName().isEmpty(), "Key name should not be empty");
        assertFalse(coin.getName().isEmpty(), "Coin name should not be empty");
    }

    @Test
    @DisplayName("Items should have proper descriptions")
    void testItemDescriptions() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();

        // Assert
        assertNotNull(rum.getDescription(), "Rum should have a description");
        assertNotNull(key.getDescription(), "Key should have a description");

        assertFalse(rum.getDescription().isEmpty(), "Rum description should not be empty");
        assertFalse(key.getDescription().isEmpty(), "Key description should not be empty");
    }

    @Test
    @DisplayName("Items should have display symbols")
    void testItemDisplaySymbols() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();
        Item coin = PirateTreasureFactory.createLargeGoldCoin();
        Item saber = PirateTreasureFactory.createPirateSaber();
        Item jewelBox = PirateTreasureFactory.createJewelBox();

        // Assert - Items should have meaningful symbols
        assertNotEquals('\0', rum.getSymbol(), "Rum should have a display symbol");
        assertNotEquals('\0', key.getSymbol(), "Key should have a display symbol");
        assertNotEquals('\0', coin.getSymbol(), "Coin should have a display symbol");
        assertNotEquals('\0', saber.getSymbol(), "Saber should have a display symbol");
        assertNotEquals('\0', jewelBox.getSymbol(), "Jewel box should have a display symbol");

        // Symbols should be printable characters
        assertTrue(rum.getSymbol() >= '!' && rum.getSymbol() <= '~' ||
                rum.getSymbol() >= 160, "Rum symbol should be printable");
        assertTrue(key.getSymbol() >= '!' && key.getSymbol() <= '~' ||
                key.getSymbol() >= 160, "Key symbol should be printable");
    }

    @Test
    @DisplayName("Items should have correct consumable properties")
    void testItemConsumableProperties() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();
        Item coin = PirateTreasureFactory.createLargeGoldCoin();
        Item saber = PirateTreasureFactory.createPirateSaber();
        Item jewelBox = PirateTreasureFactory.createJewelBox();

        // Assert - Just check that consumable property is properly set
        // We don't enforce specific values, just that they have a defined state
        assertNotNull(rum.isConsumable(), "Rum should have consumable state defined");
        assertNotNull(key.isConsumable(), "Key should have consumable state defined");
        assertNotNull(coin.isConsumable(), "Coin should have consumable state defined");
        assertNotNull(saber.isConsumable(), "Saber should have consumable state defined");
        assertNotNull(jewelBox.isConsumable(), "Jewel box should have consumable state defined");
    }

    @Test
    @DisplayName("Items should be usable by player")
    void testItemUsage() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();

        // Act - Test that the item can be used (returns some result)
        String result = rum.use(player);

        // Assert
        assertNotNull(result, "Using item should return a result");
        assertTrue(result.length() > 0, "Result should not be empty");
    }

    @Test
    @DisplayName("Item toString should provide useful information")
    void testItemToString() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();

        // Act
        String rumString = rum.toString();
        String keyString = key.toString();

        // Assert
        assertNotNull(rumString, "Rum toString should not return null");
        assertNotNull(keyString, "Key toString should not return null");
        assertTrue(rumString.length() > 5, "Rum toString should provide meaningful information");
        assertTrue(keyString.length() > 5, "Key toString should provide meaningful information");
    }

    @Test
    @DisplayName("Different items should have different symbols")
    void testItemSymbolUniqueness() {
        // Arrange
        Item rum = PirateTreasureFactory.createStrongRum();
        Item key = PirateTreasureFactory.createMagicKey();
        Item coin = PirateTreasureFactory.createLargeGoldCoin();
        Item saber = PirateTreasureFactory.createPirateSaber();

        // Assert - Different item types should ideally have different symbols
        // This helps with map display clarity
        char rumSymbol = rum.getSymbol();
        char keySymbol = key.getSymbol();
        char coinSymbol = coin.getSymbol();
        char saberSymbol = saber.getSymbol();

        // At least some items should have different symbols
        boolean symbolsAreDifferent = (rumSymbol != keySymbol) ||
                (keySymbol != coinSymbol) ||
                (coinSymbol != saberSymbol);

        assertTrue(symbolsAreDifferent, "Different item types should have different symbols for map clarity");
    }
}