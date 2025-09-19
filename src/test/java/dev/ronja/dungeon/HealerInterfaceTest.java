package dev.ronja.dungeon;

import dev.ronja.dungeon.entities.Healer;
import dev.ronja.dungeon.entities.Mother;
import dev.ronja.dungeon.entities.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealerInterfaceTest {
    void motherHealsPlayer() {
        Player p = new Player("Ronja");
        p.setHp(50);

        Healer mom = new Mother(20);
        mom.heal(p);

        assertEquals(70, p.getHp());
    }
}
