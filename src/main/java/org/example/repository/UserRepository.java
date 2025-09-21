package org.example.repository;

import org.example.User;

import java.security.NoSuchAlgorithmException;

public interface UserRepository {
  void login(String username, String password) throws NoSuchAlgorithmException;
  void save(User user);
}
