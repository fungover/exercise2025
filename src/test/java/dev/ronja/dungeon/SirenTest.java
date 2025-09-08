package dev.ronja.dungeon;

import dev.ronja.dungeon.entities.Player;
import dev.ronja.dungeon.entities.Siren;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SirenTest {

    @Test
    void sirenDealsDamageWhenSheAttacks() {
        Player player = new Player("Ronja");
        Siren siren = new Siren();

        int before = player.getHp();

        siren.attack(player);

        assertEquals(before - siren.getDamage(), player.getHp(),
                "Siren attack should reduce player`s HP by siren`s attack value");
    }

    @Test
    void sirenCanUseSpecialSongAsAttack() {
        Player player = new Player("Ronja");
        Siren siren = new Siren();

        int before = player.getHp();

        siren.sing(player);

        assertTrue(player.getHp() < before,
                "Siren song attack should reduce player`s HP");
    }
}
