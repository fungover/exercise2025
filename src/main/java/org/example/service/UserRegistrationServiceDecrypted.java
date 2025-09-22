package org.example.service;

import org.example.repository.UserRepository;
import org.example.users.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserRegistrationServiceDecrypted implements  UserRegistrationService {
  private final UserRepository userRepository;

  public UserRegistrationServiceDecrypted(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void registerUser(User user) throws NoSuchAlgorithmException {

    User newUser = new User(user.username(), user.password());
    userRepository.save(newUser);
  }
}
