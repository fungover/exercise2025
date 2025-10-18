package org.example.repository;

import org.example.entities.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Simulates a database-backed repository.
 * In a real application, this would connect to an actual database.
 */
public class DatabaseUserRepository implements UserRepository {
    private static final Logger logger = Logger.getLogger(DatabaseUserRepository.class.getName());
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private final String databaseName;

    public DatabaseUserRepository(String databaseName) {
        this.databaseName = databaseName;
        logger.info("DatabaseUserRepository initialized with database: " + databaseName);
    }

    @Override
    public void addUser(User user) {
        if (user == null || user.id() == null) {
            throw new IllegalArgumentException("User and user ID cannot be null");
        }
        logger.fine("Adding user with ID: " + user.id() + " to database [" + databaseName + "]");
        User previous = users.putIfAbsent(user.id(), user);
        if (previous != null) {
            throw new IllegalArgumentException("User with ID " + user.id() + " already exists");
        }
    }

    @Override
    public Optional<User> getUserById(String id) {
        logger.fine("Fetching user from database [" + databaseName + "] with ID: " + id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public List<User> getAllUsers() {
        logger.fine("Fetching all users from database [" + databaseName + "]");
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateUser(User user) {
        if (user == null || user.id() == null) {
            throw new IllegalArgumentException("User and user ID cannot be null");
        }
        logger.fine("Updating user with ID: " + user.id() + " in database [" + databaseName + "]");
        User previous = users.replace(user.id(), user);
        if (previous == null) {
            throw new IllegalStateException("User with ID " + user.id() + " does not exist");
        }
    }

    @Override
    public void deleteUser(String id) {
        logger.fine("Deleting user from database [" + databaseName + "] with ID: " + id);
        users.remove(id);
    }
}
