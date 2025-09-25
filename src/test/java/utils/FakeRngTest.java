package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FakeRngTest {

    @Test
    void returnsPreloadedInts() {
        FakeRng rng = new FakeRng().pushInts(5, 10);
        assertEquals(0, rng.nextInt(5));   // 5 % 5 = 0
        assertEquals(0, rng.nextInt(10));  // 10 % 10 = 0
    }

    @Test
    void returnsPreloadedBooleans() {
        FakeRng rng = new FakeRng().pushBools(true, false);
        assertTrue(rng.chance(50));
        assertFalse(rng.chance(50));
    }
}
