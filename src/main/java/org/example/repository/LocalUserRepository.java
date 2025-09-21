package org.example.repository;

import org.example.users.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class LocalUserRepository implements UserRepository {
  Set<User> users = new HashSet<User>();

  @Override
  public void login(String username, String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashCode = md.digest(password.getBytes());
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b : hashCode) {
      stringBuilder.append(String.format("%02X", b));
    }

    String newPassword = stringBuilder.toString();
    for (User user : users) {
      if (user.username().equals(username) && user.password().equals(newPassword)) {
        System.out.println("Login successful");
        return;
      }
    }
    System.out.println("Login failed");
  }

  public void save(User user) {
    users.add(user);
  }
}
