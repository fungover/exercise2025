package org.example.service;

import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
  void registerUser(User user) throws NoSuchAlgorithmException;
}
