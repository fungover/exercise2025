package org.example.repository;

import java.security.NoSuchAlgorithmException;

public interface UserRepository {
  void registerUser(User user) throws NoSuchAlgorithmException;
  void login(String username, String password);
}
