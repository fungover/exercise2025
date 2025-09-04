package org.example.entities;

import java.time.LocalDate;
import java.util.UUID;

public record Product(Long id, String name, Category category, int rating,
                      LocalDate createdDate, LocalDate modifiedDate) {

    private static long idCounter = 1;

    //constructor
    public Product(String name, Category category, int rating) {
        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("rating should be between 0 and 10");

        }
        this(idCounter++, name, category, rating, LocalDate.now(), LocalDate.now());

    }

    @Override public String toString() {
        return String.format(
          "id=%d, Name=%s, Category=%s, Rating=%d, Created=%s, Modified=%s", id,
          name, category, rating, createdDate, modifiedDate);
    }


}
