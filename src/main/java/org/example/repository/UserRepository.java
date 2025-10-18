package org.example.repository;

import org.example.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void addUser(User user);
    Optional<User> getUserById(String id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(String id);
}
