package org.example.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class LocalUserRepository implements UserRepository {
  Set<User> users = new HashSet<User>();

  @Override
  public void registerUser(User user) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashCode = md.digest(user.password().getBytes());
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b : hashCode) {
      stringBuilder.append(String.format("%02X", b));
    }

    User newUser = new User(user.username(), stringBuilder.toString());

    users.add(newUser);
  }

  @Override
  public void login(String username, String password) {

  }
}
