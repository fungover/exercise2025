package ExerciseTwo.Entities.Inventory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    Item item = new Item("Potion", 15) {
        @Override
        public void itemDescription() {
            System.out.println("Potion");
        }
    };

    @Test
    void effectIsSetWhenItemIsCreated() {
        assertEquals(15, item.getEffect());
    }

}