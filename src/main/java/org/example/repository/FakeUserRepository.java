package org.example.repository;

import org.example.User;

import java.security.NoSuchAlgorithmException;

public class FakeUserRepository implements UserRepository {

  @Override
  public void login(String username, String password) throws NoSuchAlgorithmException {
    System.out.println("Fake user: '" + username + "' logged in");
  }

  @Override
  public void save(User user) {
    System.out.println("Fake user '" + user.username() + "' saved successfully");
  }
}
