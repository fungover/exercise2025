package clone.rs.dungeon.locations;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.Items.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {

  @Test
  void testLumbridgeHasEnemyAndItem() {
    Lumbridge lumbridge = new Lumbridge();

    Enemy enemy = lumbridge.enemy();
    Item item = lumbridge.item();

    assertNotNull(enemy, "Lumbridge should generate an enemy");
    assertNotNull(item, "Lumbridge should generate an item");
  }
}