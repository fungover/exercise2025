package org.example.repository;

import java.security.NoSuchAlgorithmException;

public class FakeUserRepository implements UserRepository {


  @Override
  public void registerUser(User user) throws NoSuchAlgorithmException {
    System.out.println("User registered");
  }

  @Override
  public void login(String username, String password) throws NoSuchAlgorithmException {
    System.out.println("User logged in");
  }
}
