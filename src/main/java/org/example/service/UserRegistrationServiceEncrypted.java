package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.users.User;
import org.example.repository.UserRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class UserRegistrationServiceEncrypted implements UserRegistrationService {
  private final UserRepository userRepository;

  @Inject
  public UserRegistrationServiceEncrypted(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void registerUser(User user) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashCode = md.digest(user.password().getBytes());
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b : hashCode) {
      stringBuilder.append(String.format("%02X", b));
    }

    User newUser = new User(user.username(), stringBuilder.toString());

    userRepository.save(newUser);
  }
}
