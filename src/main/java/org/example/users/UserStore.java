package org.example.users;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class UserStore {
  private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

  public void addUser(User user) {
    users.put(user.username(), user);
  }

  public Optional<User> findUserByUsername(String username) {
    return Optional.ofNullable(users.get(username));
  }
}