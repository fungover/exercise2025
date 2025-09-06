import org.example.entities.Position;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class MovementTest {
    @org.testng.annotations.Test
    public void testTranslate() {
        Position start = new Position(2, 3);
        Position movedEast = start.translate(1, 0);
        Position movedNorth = start.translate(0, -1);

        assertEquals(new Position(3,3), movedEast, "Moving east failed");
        assertEquals(new Position(2,2), movedNorth, "Moving north failed");
    }

    @Test
    public void testEqualityAndHash() {
        Position a = new Position(3, 2);
        Position b = new Position(3, 2);
        Position c = new Position(2, 3);

        assertEquals(a, b, "Positions a and b should be equal");
        assertNotEquals(a, c, "Positions a and c should NOT be equal");
        assertEquals(a.hashCode(), b.hashCode(), "Equal positions must have same hashCode");
    }
}
