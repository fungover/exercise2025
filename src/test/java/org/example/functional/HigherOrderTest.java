package org.example.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HigherOrderTest {

    @DisplayName("😊 Filtering keeps only names starting with M")
    @Test
    void filterNamesOnlyKeepsNamesStartingWithM() {
        var unfilteredList = List.of("Mary", "Sting");

        var filteredNames = HigherOrder.keepNamesStartingWithM(unfilteredList);

        assertEquals(1, filteredNames.size());
        assertEquals("Mary", filteredNames.getFirst());
    }

    @Test
    void addReturnsSumOfTwoNumbers() {
        assertEquals(10, HigherOrder.add(5, 5));
    }


    @Test
    void greetAStrangerWithNullName() {
        String input = null;
        var greeting = new HigherOrder().greeting(input);
        assertEquals("Hello stranger", greeting);
    }

    @Test
    void greetAStrangerWithEmptyName() {
        var greeting = new HigherOrder().greeting("");
        assertEquals("Hello stranger", greeting);
    }


    @Test
    void greetAStrangerWithName() {
        var greeting = new HigherOrder().greeting("Martin");
        assertEquals("Hello Martin", greeting);
    }

    @Test
    void returnsGoodMorningIfTimeIsBefore12() {
        var greeting = new HigherOrder().greeting(LocalTime.of(11,00));
        assertEquals("Good morning", greeting);
    }


}