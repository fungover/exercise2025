package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("RandomGenerator Tests")
@ExtendWith(MockitoExtension.class)
class RandomGeneratorTest {

    @InjectMocks
    Chance chance;

    @Mock
    RandomGenerator generator;

    @Test
    @DisplayName("50% chance that its true")
    void Chance50ThatItsTrue() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(generator.nextDouble()).thenReturn(0.49);

        assertThat(chance.rollPercent(50)).isTrue();
    }
    @Test
    @DisplayName("10% chance that its false")
    void Chance10ThatItsFalse() throws NoSuchFieldException, IllegalAccessException {
        Mockito.when(generator.nextDouble()).thenReturn(0.9);

        assertThat(chance.rollPercent(90)).isFalse();
    }

//    @Test
//    @DisplayName("rollPercent should work with 0% and 100% chances")
//    void testRollPercentExtremes() {
//        // Test med 0% - ska aldrig lyckas
//        for (int i = 0; i < 20; i++) {
//            assertFalse(chance.rollPercent(0),
//                    "0% chance should never succeed");
//        }
//
//        // Test med 100% - ska alltid lyckas
//        for (int i = 0; i < 20; i++) {
//            assertTrue(chance.rollPercent(100),
//                    "100% chance should always succeed");
//        }
//    }
//
//    @RepeatedTest(5)
//    @DisplayName("rollPercent with flee chance should be roughly correct")
//    void testFleeChance() {
//        int successes = 0;
//        int trials = 100;
//
//        for (int i = 0; i < trials; i++) {
//            if (chance.rollPercent(70)) {
//                successes++;
//            }
//        }
//
//        // 70% chans borde ge ungefär 50-90% framgång över 100 försök
//        assertTrue(successes >= 50 && successes <= 90,
//                "Flee chance (" + 70 + "%) over 100 trials should be roughly correct, got: " + successes);
//    }
//
//    @RepeatedTest(5)
//    @DisplayName("rollPercent with special attack chance should be roughly correct")
//    void testSpecialAttackChance() {
//        int successes = 0;
//        int trials = 100;
//
//        for (int i = 0; i < trials; i++) {
//            if (chance.rollPercent(30)) {
//                successes++;
//            }
//        }
//
//        // 30% chans borde ge ungefär 15-45% framgång över 100 försök
//        assertTrue(successes >= 15 && successes <= 45,
//                "Special attack chance (" + 30 + "%) over 100 trials should be roughly correct, got: " + successes);
//    }
}