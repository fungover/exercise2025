package org.example.repository;

import jakarta.enterprise.context.Dependent;
import org.example.users.User;

import java.util.HashSet;
import java.util.Set;

@Dependent
public class FakeDBUserRepository implements UserRepository {
  Set<User> users = new HashSet<User>();

  @Override
  public void login(String username, String password) {
    System.out.println(username + " connected to db pool");
  }

  public void save(User user) {
    users.add(user);
  }
}
