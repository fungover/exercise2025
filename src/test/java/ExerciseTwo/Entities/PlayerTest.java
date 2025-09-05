package ExerciseTwo.Entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @DisplayName("Sets health when name is set")
    @Test
    void setsHealthWhenNameIsSet() {
        var name = "Hero";
        var player = new Player(name);

        assertEquals(100, player.getHealth());
    }

}