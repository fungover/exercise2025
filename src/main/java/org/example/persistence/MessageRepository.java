package org.example.persistence;

public interface MessageRepository {
    /**
     * Returns a String.format-compatible template (e.g., "Hej, %s!").
     * */
    String getMessage();
}
