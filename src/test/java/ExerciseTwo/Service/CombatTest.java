package ExerciseTwo.Service;

import ExerciseTwo.Entities.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class CombatTest {

    @Test
    void CheckThatPlayersHealthChangesWhenAttacked(){
        Player player = new Player("Test Player");
        Combat combat = new Combat(player);
       combat.playerDamage(-10);
       assertEquals(90, player.getHealth());
    }
}