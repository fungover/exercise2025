package org.example.repository;

// This interface defines a contract for saving messages.
// It does not do anything by itself.

public interface MessageRepository {
    void saveMessage(String message);
}
