package org.example.users;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class UserStore {
  private final Set<User> users = Collections.synchronizedSet(new HashSet<>());

  public void addUser(User user) {
    users.add(user);
  }

  public Optional<User> findUserByUsername(String username) {
    return users.stream()
            .filter(u -> u.username().equals(username))
            .findFirst();
  }
}