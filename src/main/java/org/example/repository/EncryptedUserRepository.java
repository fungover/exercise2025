package org.example.repository;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import org.example.users.User;
import org.example.users.UserStore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Dependent
@Default
public class EncryptedUserRepository implements UserRepository {
  private final UserStore userStore;

  @Inject
  public EncryptedUserRepository(UserStore userStore) {
    this.userStore = userStore;
    System.out.println("*User store CREATED*");
  }

  @Override
  public void login(String username, String password) throws NoSuchAlgorithmException {

    String hashedPassword = hashPassword(password);

    Optional<User> foundUser = userStore.findUserByUsername(username);

    if(foundUser.isPresent() && foundUser.get().password().equals(hashedPassword)) {
      System.out.println("User " + username + " has been logged in");
    } else
      System.out.println("User " + username + " has NOT been logged in");
    }

  @Override
  public void save(User user) throws NoSuchAlgorithmException {
    User newUser = new User(user.username(), hashPassword(user.password()));
    userStore.addUser(newUser);
  }

  private String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashCode = md.digest(password.getBytes());
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b : hashCode) {
      stringBuilder.append(String.format("%02X", b));
    }
    return stringBuilder.toString();
  }
}