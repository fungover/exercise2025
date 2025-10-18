package org.example.service;

import org.example.entities.User;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for user management.
 * Demonstrates dependency injection of repository layer.
 */
public interface UserService {
    void registerUser(User user);
    Optional<User> findUserById(String id);
    List<User> getAllUsers();
    void updateUserEmail(String id, String newEmail);
    void removeUser(String id);
}
