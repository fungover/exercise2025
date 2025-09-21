package org.example.service;

import org.example.User;

import java.security.NoSuchAlgorithmException;

public interface UserService {
  void registerUser(User user) throws NoSuchAlgorithmException;
}
