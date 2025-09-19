package dev.ronja.dungeon;


import dev.ronja.dungeon.entities.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EnemyInterfaceTest {
    static Stream<Supplier<Enemy>> enemySuppliers() {
        return Stream.of(
                Siren::new,
                MotherInLaw::new,
                DarkWitch::new
        );
    }

    @ParameterizedTest(name = "{index} -> {0}")
    @MethodSource("enemySuppliers")
    void attack_reducesPlayersHpByEnemyDamageLevel(Supplier<Enemy> factory) {
        Enemy e = factory.get();
        Player p = new Player("Ronja");
        int before = p.getHp();

        e.attack(p);

        assertEquals(before - e.getDamage(), p.getHp(),
                "Attack() should subtract exact getDamage() HP");
    }

    @ParameterizedTest(name = "{index} -> {0}")
    @MethodSource("enemySuppliers")
    void takeDamage_clampsAtZero_and_isAliveFalse(Supplier<Enemy> factory) {
        Enemy e = factory.get();

        e.takeDamage(e.getHp() + 999);

        assertEquals(0, e.getHp(), "HP cannot go below Zero");
        assertFalse(e.isAlive(), "isAlive() should be false with HP 0");
    }

    @ParameterizedTest
    @MethodSource("enemySuppliers")
    void motherInLaw_canTalkPlayerToDeath(Supplier<Enemy> factory) {
        Enemy e = factory.get();

        if (e instanceof PsychologicalAttack pAttack) {
            Player p = new Player ("Ronja");
            int before = p.getHp();

            pAttack.talkToDeath(p);
            assertEquals(before -(e.getDamage() * 2), p.getHp(), "Talk to death should double the damage");
        }
    }

}
