import entities.Player;
import entities.Enemy;
import entities.Item;
import map.Room;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestDungeonGame {

    @Test
    void testRoomMovement() {
        Room start = new Room("Start");  //Att starta i ett rum och testa rörelse mellan rum fungerar korrekt.
        Room next = new Room("Next Room");
        start.addExit(1, next); //Att man från start kommer till nästa rum.

        assertEquals(next, start.getExit(1));
    }




    @Test
    void testMonsterHit() {
        Enemy enemy = new Enemy("Monster", 10, 2); //Testa skada, att HP sänks efter varje träff med monster.
        enemy.takeDamage(5);
        assertEquals(5, enemy.getHealth());
    }




}
