package clone.rs.dungeon.controls;

import clone.rs.dungeon.character.Enemy;
import clone.rs.dungeon.character.Player;
import clone.rs.dungeon.Items.Item;
import clone.rs.dungeon.weapons.Weapon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombatTest {

  Player player;
  Enemy enemy;

  @BeforeEach
  void setUp() {
    player = new Player("TestPlayer", 20, 10, new Weapon("Hand"), null);
    enemy = new Enemy("Goblin", 10, 1, 1);
  }

  @Test
  void testAttackReducesEnemyHealth() {
    double initialEnemyHp = enemy.getHealth();
    int initialExp = player.getExp();

    double damage = player.hit();
    enemy.setHealth(enemy.getHealth() - damage);
    player.addExperience(10);

    assertTrue(enemy.getHealth() < initialEnemyHp, "Enemy HP should be reduced");
  }

  @Test
  void testEscapeReducesPlayerHealthByOne() {
    double initialHp = player.getHealth();
    player.setHealth(player.getHealth() - 1);

    assertEquals(initialHp - 1, player.getHealth(), "Player HP should decrease by 1 when escaping");
  }

  @Test
  void testUseItemHealsPlayer() {
    player.setHealth(5);
    Item apple = new Item("Apple", 1, false, true, 10);
    player.addItem(apple);

    player.useItem("Apple");
    double expected = Math.min(5 + 10, player.getMaxHp());
    assertEquals(expected, player.getHealth(), "Using food item should increase player's health up to max HP");
  }

  @Test
  void testUseItemNotFood() {
    Item sword = new Item("Sword", 1, true, false, 5);
    player.addItem(sword);

    double initialHp = player.getHealth();
    player.useItem("Sword");

    assertEquals(initialHp, player.getHealth(), "Non-food item should not change health");
  }
}
