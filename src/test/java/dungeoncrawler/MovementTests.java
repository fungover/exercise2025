package dungeoncrawler;

import dungeoncrawler.enteties.Player;
import dungeoncrawler.service.Movement;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovementTests {
    @Test
    void testGoingNorth(){
        int[] position = {3, 2};
        Player testPlayer = new Player("Test", 10, position);
        Movement movement = new Movement();
        movement.goNorth(testPlayer);
        int[] newPosition = {3, 1};
        assertEquals(testPlayer.getPosition()[0], newPosition[0]);
        assertEquals(testPlayer.getPosition()[1], newPosition[1]);
    }
    @Test
    void testGoingSouth(){
        int[] position = {3, 2};
        Player testPlayer = new Player("Test", 10, position);
        Movement movement = new Movement();
        movement.goSouth(testPlayer);
        int[] newPosition = {3, 3};
        assertEquals(testPlayer.getPosition()[0], newPosition[0]);
        assertEquals(testPlayer.getPosition()[1], newPosition[1]);
    }
    @Test
    void testGoingWest(){
        int[] position = {3, 2};
        Player testPlayer = new Player("Test", 10, position);
        Movement movement = new Movement();
        movement.goWest(testPlayer);
        int[] newPosition = {2, 2};
        assertEquals(testPlayer.getPosition()[0], newPosition[0]);
        assertEquals(testPlayer.getPosition()[1], newPosition[1]);
    }
    @Test
    void testGoingEast(){
        int[] position = {3, 2};
        Player testPlayer = new Player("Test", 10, position);
        Movement movement = new Movement();
        movement.goEast(testPlayer);
        int[] newPosition = {4, 2};
        assertEquals(testPlayer.getPosition()[0], newPosition[0]);
        assertEquals(testPlayer.getPosition()[1], newPosition[1]);
    }

}
