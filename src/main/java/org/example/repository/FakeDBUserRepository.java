package org.example.repository;

import org.example.users.User;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class FakeDBUserRepository implements UserRepository {
  Set<User> users = new HashSet<User>();

  @Override
  public void login(String username, String password) throws NoSuchAlgorithmException {

    for (User user : users) {
      if (user.username().equals(username) && user.password().equals(password)) {
        System.out.println("Login successful DB");
        return;
      }
    }
    System.out.println("Login failed");
  }

  public void save(User user) {
    users.add(user);
  }
}
