package org.example;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.example.di.A;
import org.example.di.SimpleDi;
import org.example.repository.EncryptedUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationService;
import org.example.service.UserRegistrationServiceEncrypted;
import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    // Part 1 (Manual)

    // Encrypted user login and registration using user repository interface and service

    UserRepository encryptedRepo = new EncryptedUserRepository();
    UserRegistrationService userService = new UserRegistrationServiceEncrypted(encryptedRepo);

    userService.registerUser(new User("Test", "12345"));
    encryptedRepo.login("Test", "12345");

    //Part 2 (Simple di)
    UserRegistrationServiceEncrypted registrationService =
            SimpleDi.runWithScope(UserRegistrationServiceEncrypted.class);


    // PArt 3 (Run with Weld)

//    try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
//      UserRegistrationServiceEncrypted service =
//              container.select(UserRegistrationServiceEncrypted.class).get();
//
//      service.registerUser(new User("Alice", "12345"));
//
//    }
  }
}