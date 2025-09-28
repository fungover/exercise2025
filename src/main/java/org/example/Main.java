package org.example;

import org.example.di.SimpleDi;
import org.example.repository.EncryptedUserRepository;
import org.example.repository.FakeDBUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationService;
import org.example.service.UserRegistrationServiceEncrypted;
import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    // Manual run

    // Fake db pool using user repository interface
    UserRepository fakeRepo = new FakeDBUserRepository();
    UserRegistrationService userService3 = new UserRegistrationServiceEncrypted(fakeRepo);

    userService3.registerUser(new User("Test", "12345"));
    fakeRepo.login("Test", "12345");

    // Encrypted user login and registration using user repository interface and service

    UserRepository encryptedRepo = new EncryptedUserRepository();
    UserRegistrationService userService = new UserRegistrationServiceEncrypted(encryptedRepo);

    userService.registerUser(new User("Test", "12345"));
    encryptedRepo.login("Test", "12345");

    // Run with Weld
    

  }
}