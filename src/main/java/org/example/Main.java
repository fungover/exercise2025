package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.di.SimpleDi;
import org.example.repository.EncryptedUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationService;
import org.example.service.UserRegistrationServiceEncrypted;
import org.example.users.User;
import org.example.users.UserStore;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    // Part 1 (Manual)

    UserStore userStore = new UserStore();
    UserRepository encryptedRepo = new EncryptedUserRepository(userStore);
    UserRegistrationService userService = new UserRegistrationServiceEncrypted(encryptedRepo);

    System.out.println("--- Part 1: Manual Injection ---");
    userService.registerUser(new User("Part1", "12345"));
    encryptedRepo.login("Part1", "12345");

    //Part 2 (Simple di)
    SimpleDi.register(UserRepository.class, EncryptedUserRepository.class);

    System.out.println("\n--- Part 2: Simple DI ---");
    UserRegistrationServiceEncrypted registrationService =
            SimpleDi.runWithScope(UserRegistrationServiceEncrypted.class);

    registrationService.registerUser(new User("Part2", "12345"));


    // Part 3 (Run with Weld)

    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

      UserRegistrationServiceEncrypted service =
              container.select(UserRegistrationServiceEncrypted.class).get();

      UserRepository cdiRepo = container.select(UserRepository.class).get();

      System.out.println("\n--- Part 3: CDI/Weld ---");
      service.registerUser(new User("Part3", "12345"));

      cdiRepo.login("Part3", "12345");

    }
  }
}