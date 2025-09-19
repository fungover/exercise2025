package utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Utils Package Tests - Focused on Actually Used Code")
class UtilsTest {

    @Nested
    @DisplayName("RandomGenerator Tests")
    class RandomGeneratorTest {

        @Test
        @DisplayName("rollPercent should work with 0% and 100% chances")
        void testRollPercentExtremes() {
            // Test med 0% - ska aldrig lyckas
            for (int i = 0; i < 20; i++) {
                assertFalse(RandomGenerator.rollPercent(0),
                        "0% chance should never succeed");
            }

            // Test med 100% - ska alltid lyckas
            for (int i = 0; i < 20; i++) {
                assertTrue(RandomGenerator.rollPercent(100),
                        "100% chance should always succeed");
            }
        }

        @RepeatedTest(5)
        @DisplayName("rollPercent with flee chance should be roughly correct")
        void testFleeChance() {
            int successes = 0;
            int trials = 100;

            for (int i = 0; i < trials; i++) {
                if (RandomGenerator.rollPercent(Constants.FLEE_SUCCESS_CHANCE)) {
                    successes++;
                }
            }

            // 70% chans borde ge ungefär 50-90% framgång över 100 försök
            assertTrue(successes >= 50 && successes <= 90,
                    "Flee chance (" + Constants.FLEE_SUCCESS_CHANCE + "%) over 100 trials should be roughly correct, got: " + successes);
        }

        @RepeatedTest(5)
        @DisplayName("rollPercent with special attack chance should be roughly correct")
        void testSpecialAttackChance() {
            int successes = 0;
            int trials = 100;

            for (int i = 0; i < trials; i++) {
                if (RandomGenerator.rollPercent(Constants.SPECIAL_ATTACK_CHANCE)) {
                    successes++;
                }
            }

            // 30% chans borde ge ungefär 15-45% framgång över 100 försök
            assertTrue(successes >= 15 && successes <= 45,
                    "Special attack chance (" + Constants.SPECIAL_ATTACK_CHANCE + "%) over 100 trials should be roughly correct, got: " + successes);
        }
    }

    @Nested
    @DisplayName("Constants Tests - Values Used in Game")
    class ConstantsTest {

        @Test
        @DisplayName("Player constants should have reasonable values")
        void testPlayerConstants() {
            assertTrue(Constants.PLAYER_STARTING_HEALTH > 0,
                    "Player starting health should be positive, got: " + Constants.PLAYER_STARTING_HEALTH);
            assertTrue(Constants.PLAYER_STARTING_DAMAGE > 0,
                    "Player starting damage should be positive, got: " + Constants.PLAYER_STARTING_DAMAGE);
            assertTrue(Constants.PLAYER_MAX_INVENTORY > 0,
                    "Max inventory should be positive, got: " + Constants.PLAYER_MAX_INVENTORY);
        }

        @Test
        @DisplayName("Combat constants should be valid percentages")
        void testCombatConstants() {
            assertTrue(Constants.FLEE_SUCCESS_CHANCE >= 0 && Constants.FLEE_SUCCESS_CHANCE <= 100,
                    "Flee chance should be 0-100%, got: " + Constants.FLEE_SUCCESS_CHANCE);
            assertTrue(Constants.SPECIAL_ATTACK_CHANCE >= 0 && Constants.SPECIAL_ATTACK_CHANCE <= 100,
                    "Special attack chance should be 0-100%, got: " + Constants.SPECIAL_ATTACK_CHANCE);
        }

        @Test
        @DisplayName("Map constants should be positive")
        void testMapConstants() {
            assertTrue(Constants.DEFAULT_MAP_WIDTH > 0,
                    "Default map width should be positive, got: " + Constants.DEFAULT_MAP_WIDTH);
            assertTrue(Constants.DEFAULT_MAP_HEIGHT > 0,
                    "Default map height should be positive, got: " + Constants.DEFAULT_MAP_HEIGHT);
        }

        @Test
        @DisplayName("Player symbol should be defined")
        void testPlayerSymbol() {
            assertEquals('@', Constants.PLAYER_SYMBOL,
                    "Player symbol should be '@'");
            assertNotEquals('\0', Constants.PLAYER_SYMBOL,
                    "Player symbol should not be null character");
        }

        @Test
        @DisplayName("Combat and flee percentages should be logical")
        void testLogicalPercentages() {
            assertTrue(Constants.FLEE_SUCCESS_CHANCE > Constants.SPECIAL_ATTACK_CHANCE,
                    "Flee chance should be higher than special attack chance for game balance");
        }
    }
}