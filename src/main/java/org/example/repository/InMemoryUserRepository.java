package org.example.repository;

import org.example.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@ApplicationScoped
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void addUser(User user) {
        if (user == null || user.id() == null) {
            throw new IllegalArgumentException("User and user ID cannot be null");
        }
        User existing = users.putIfAbsent(user.id(), user);
        if (existing != null) {
            throw new IllegalStateException("User with ID " + user.id() + " already exists");
        }
    }

    @Override
    public Optional<User> getUserById(String id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateUser(User user) {
        users.put(user.id(), user);
    }

    @Override
    public void deleteUser(String id) {
        users.remove(id);
    }
}
