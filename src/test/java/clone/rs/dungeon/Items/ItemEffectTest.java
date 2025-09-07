package clone.rs.dungeon.Items;

import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.Items.Item;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemEffectTest {

  @Test
  void testFoodIncreasesHealth() {
    Player player = new Player();
    player.setHealth(5);

    Item apple = new Item("Apple", 1, false, true, 5);
    player.addItem(apple);

    player.useItem("Apple");
    assertEquals(10, player.getHealth());
  }
}