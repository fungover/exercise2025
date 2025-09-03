package org.example.functional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

}