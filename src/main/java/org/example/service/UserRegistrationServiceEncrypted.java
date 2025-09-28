package org.example.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.users.User;
import org.example.repository.UserRepository;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class UserRegistrationServiceEncrypted implements UserRegistrationService {
  private final UserRepository userRepository;

  @Inject
  public UserRegistrationServiceEncrypted(UserRepository userRepository) {
    this.userRepository = userRepository;
    System.out.println("*User repository CREATED*");
  }

  @Override
  public void registerUser(User user) throws NoSuchAlgorithmException {
    userRepository.save(user);
  }
}
