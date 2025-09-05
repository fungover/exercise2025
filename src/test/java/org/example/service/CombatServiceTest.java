package org.example.service;

import org.example.entities.*;
import org.example.map.FarmageddonMap;
import org.example.map.Tile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class CombatServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    void setUpStreams() {
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    // Helper method to create map with enemy
    private FarmageddonMap createMapWithEnemy(Enemy enemy, int x, int y) {
        FarmageddonMap map = new FarmageddonMap(3, 3);
        Tile tile = map.getTile(x, y);
        tile.setType(Tile.Type.ENEMY);
        tile.setEnemy(enemy);
        return map;
    }

    // Helper method to invoke private calculatePlayerDamage method
    private int invokeCalculatePlayerDamage(CombatService service, Player player) throws Exception {
        Method method = CombatService.class.getDeclaredMethod("calculatePlayerDamage", Player.class);
        method.setAccessible(true);
        return (int) method.invoke(service, player);
    }

    // Test weapon class for creating weapons with different damage values
    static class TestWeapon extends Item implements Weapon {
        private final int damage;

        public TestWeapon(String name, int damage) {
            super(name, 0, 0);
            this.damage = damage;
        }

        @Override
        public int getDamage() { return damage; }

        @Override
        public void use(Player player) {

        }
    }

    //combat calculation tests

    @Test
    @DisplayName("Player with no weapon uses base damage (5)")
    void calculatePlayerDamageNoWeaponReturnsBaseDamage() throws Exception {
        Player player = new Player("Test", 100, 0, 0);
        CombatService service = new CombatService();

        int damage = invokeCalculatePlayerDamage(service, player);

        assertEquals(5, damage);
        String output = outContent.toString();
        assertTrue(output.contains("You attack with your bare hands!"));
    }

    @Test
    @DisplayName("Player with pitchfork uses weapon damage instead of base damage")
    void calculatePlayerDamageWithPitchforkReturnsWeaponDamage() throws Exception {
        Player player = new Player("Test", 100, 0, 0);
        Pitchfork pitchfork = new Pitchfork(0, 0); // 15 damage
        player.addItem(pitchfork);

        CombatService service = new CombatService();

        int damage = invokeCalculatePlayerDamage(service, player);

        assertEquals(15, damage);
        String output = outContent.toString();
        assertTrue(output.contains("You attack with your Rusty Pitchfork!"));
    }

    @Test
    @DisplayName("Player with multiple weapons uses the strongest one")
    void calculatePlayerDamageMultipleWeaponsUsesStrongestWeapon() throws Exception {
        Player player = new Player("Test", 100, 0, 0);

        TestWeapon weakWeapon = new TestWeapon("Rusty Sword", 8);
        TestWeapon strongWeapon = new TestWeapon("Battle Axe", 25);
        Pitchfork pitchfork = new Pitchfork(0, 0); // 15 damage

        player.addItem(weakWeapon);
        player.addItem(strongWeapon);
        player.addItem(pitchfork);

        CombatService service = new CombatService();

        int damage = invokeCalculatePlayerDamage(service, player);

        assertEquals(25, damage); // Should use strongest weapon
        String output = outContent.toString();
        assertTrue(output.contains("You attack with your Battle Axe!"));
    }

    @Test
    @DisplayName("Player inventory with non-weapon items ignores them for damage calculation")
    void calculatePlayerDamageMixedInventoryIgnoresNonWeapons() throws Exception {
        Player player = new Player("Test", 100, 0, 0);

        HealingMilk milk = new HealingMilk(0, 0); // Non-weapon item
        Pitchfork pitchfork = new Pitchfork(0, 0); // Weapon with 15 damage

        player.addItem(milk);
        player.addItem(pitchfork);

        CombatService service = new CombatService();

        int damage = invokeCalculatePlayerDamage(service, player);

        assertEquals(15, damage);
        String output = outContent.toString();
        assertTrue(output.contains("You attack with your Rusty Pitchfork!"));
    }

    @Test
    @DisplayName("Player defeats weak enemy in one hit")
    void attackPlayerDefeatsWeakEnemyEnemyRemovedFromMap() {
        Player player = new Player("Hero", 100, 0, 0);
        DroolingDog weakDog = new DroolingDog(0, 0); // 10 HP, 2 damage
        FarmageddonMap map = createMapWithEnemy(weakDog, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        // Enemy should be dead and removed (base damage 5 < 10 HP, so dog survives)
        // Actually, let's give player a pitchfork to ensure kill
        Pitchfork pitchfork = new Pitchfork(0, 0);
        player.addItem(pitchfork);

        // Reset for proper test
        DroolingDog freshDog = new DroolingDog(0, 0);
        FarmageddonMap freshMap = createMapWithEnemy(freshDog, 0, 0);

        service.attack(player, freshMap);

        assertFalse(freshDog.isAlive()); // 15 damage > 10 HP
        assertNull(freshMap.getTile(0, 0).getEnemy());
        assertEquals(Tile.Type.PATH, freshMap.getTile(0, 0).getType());

        String output = outContent.toString();
        assertTrue(output.contains("You defeated the Drooling Dog!"));
    }

    @Test
    @DisplayName("Player takes damage from enemy attack but survives")
    void attackPlayerSurvivesCombatHealthDecreases() {
        Player player = new Player("Hero", 100, 0, 0);
        GiantHeadlessChicken chicken = new GiantHeadlessChicken(0, 0); // 5 HP, 20 damage
        FarmageddonMap map = createMapWithEnemy(chicken, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        // Player should have taken damage from chicken (20 damage)
        assertEquals(80, player.getHealth());
        assertTrue(player.isAlive());

        String output = outContent.toString();
        assertTrue(output.contains("--- Enemy's turn ---"));
        assertTrue(output.contains("Your current health: 80 HP"));
    }

    @Test
    @DisplayName("Player dies from enemy attack - combat ends early")
    void attackPlayerDiesCombatEndsWithoutPlayerTurn() {
        Player player = new Player("Weakling", 15, 0, 0); // Low health
        GiantHeadlessChicken chicken = new GiantHeadlessChicken(0, 0); // 20 damage will kill player
        FarmageddonMap map = createMapWithEnemy(chicken, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        // Player should be dead
        assertFalse(player.isAlive());
        assertEquals(0, player.getHealth());

        // Chicken should still be alive since player never got to attack
        assertTrue(chicken.isAlive());

        String output = outContent.toString();
        assertTrue(output.contains("You have been defeated!"));
        assertFalse(output.contains("--- Your turn ---")); // Player never got a turn
    }

    @Test
    @DisplayName("Enemy survives player attack - shows remaining health")
    void attackEnemySurvivesShowsHealthStatus() {
        Player player = new Player("Hero", 100, 0, 0);

        // Use DroolingDog (10 HP) with base damage (5) - dog survives with 5 HP
        DroolingDog dog = new DroolingDog(0, 0); // 10 HP, 2 damage
        FarmageddonMap map = createMapWithEnemy(dog, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        // Enemy should be wounded but alive (10 - 5 = 5 HP left)
        assertTrue(dog.isAlive());
        assertEquals(5, dog.getHealth());

        String output = outContent.toString();
        assertTrue(output.contains("You dealt 5 damage!"));
        assertTrue(output.contains("has 5/10 HP left"));
    }

    @Test
    @DisplayName("Combat with pitchfork deals extra damage")
    void attackPlayerWithPitchforkDealsMoreDamage() {
        Player player = new Player("Warrior", 100, 0, 0);
        Pitchfork pitchfork = new Pitchfork(0, 0); // 15 damage
        player.addItem(pitchfork);

        DroolingDog enemy = new DroolingDog(0, 0); // 10 HP
        FarmageddonMap map = createMapWithEnemy(enemy, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        // Enemy should be destroyed by pitchfork (15 > 10 HP)
        assertFalse(enemy.isAlive());

        String output = outContent.toString();
        assertTrue(output.contains("You attack with your Rusty Pitchfork!"));
        assertTrue(output.contains("You defeated the Drooling Dog!"));
    }

    @Test
    @DisplayName("No enemy on tile shows appropriate message")
    void attackNoEnemyShowsNothingToAttackMessage() {
        Player player = new Player("Hero", 100, 0, 0);
        FarmageddonMap map = new FarmageddonMap(3, 3);
        map.getTile(0, 0).setType(Tile.Type.PATH); // Empty path

        CombatService service = new CombatService();

        service.attack(player, map);

        String output = outContent.toString();
        assertTrue(output.contains("There's nothing to attack here."));
    }

    @Test
    @DisplayName("Combat shows enemy stats at start")
    void attackCombatBeginsShowsEnemyStats() {
        Player player = new Player("Hero", 100, 0, 0);
        GiantHeadlessChicken chicken = new GiantHeadlessChicken(0, 0);
        FarmageddonMap map = createMapWithEnemy(chicken, 0, 0);

        CombatService service = new CombatService();

        service.attack(player, map);

        String output = outContent.toString();
        assertTrue(output.contains("A HeadlessChicken appears!"));
        assertTrue(output.contains("ATK: 20")); // chicken damage
        assertTrue(output.contains("HP: 5/5")); // chicken health
    }

    @Test
    @DisplayName("Player with weak test weapon uses base damage instead")
    void calculatePlayerDamageWeakTestWeaponUsesBaseDamage() throws Exception {
        Player player = new Player("Test", 100, 0, 0);

        TestWeapon weakWeapon = new TestWeapon("Broken Stick", 1); // weak weapon
        player.addItem(weakWeapon);

        CombatService service = new CombatService();

        int damage = invokeCalculatePlayerDamage(service, player);

        assertEquals(CombatService.getBaseDamage(), damage); // should use base damage instead
        String output = outContent.toString();
        assertTrue(output.contains("You attack with your bare hands!"));
    }

}