package org.example;

import org.example.entities.Player;
import org.example.map.Dungeon;
import org.example.service.MovementLogic;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


//Tests for
//
//Movement logic
//
//Combat calculations
//
//Item effects
//
//Map generation (basic validation)
class AppTest {

   @DisplayName("Checking if player walks in correct direction")
    @Test
void playerGoToRight() {

       Dungeon dungeon = new Dungeon(2, 2);
       Player player = new Player("TestPlayer");
       MovementLogic logic = new MovementLogic();

       logic.movePlayer(player, "right", dungeon);

       assertThat(player.getRow()).isEqualTo(0);
       assertThat(player.getCol()).isEqualTo(1);

}
}