package clone.rs.dungeon.controls.movements;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.locations.Falador;
import clone.rs.dungeon.locations.Lumbridge;
import clone.rs.dungeon.locations.Varrock;
import clone.rs.dungeon.weapons.Weapon;
import org.junit.jupiter.api.Test;

import static clone.rs.dungeon.controls.movements.LocationManager.changePlayerLocation;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationManagerTest {

  @Test
  void testChangePlayerLocation() {
    Player player = new Player("Player", 10, 3, new Weapon("Hand"), new Lumbridge());

    changePlayerLocation(player, 2);
    assertEquals("Varrock", player.getLocation());

    changePlayerLocation(player, 3);
    assertEquals("Dwarven Mine", player.getLocation());

    changePlayerLocation(player, 1);
    assertEquals("Lumbridge", player.getLocation());
  }
}