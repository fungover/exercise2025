package org.example;

import org.example.repository.DecryptedUserRepository;
import org.example.repository.EncryptedUserRepository;
import org.example.repository.FakeDBUserRepository;
import org.example.repository.UserRepository;
import org.example.service.UserRegistrationService;
import org.example.service.UserRegistrationServiceDecrypted;
import org.example.service.UserRegistrationServiceEncrypted;
import org.example.users.User;

import java.security.NoSuchAlgorithmException;

public class Main {
  public static void main(String[] args) throws NoSuchAlgorithmException {

    // Manual tests

    UserRepository encryptedRepo = new EncryptedUserRepository();
    UserRegistrationService userService = new UserRegistrationServiceEncrypted(encryptedRepo);

    // User registered encrypted
    userService.registerUser(new User("Test", "12345"));

    // Succeed & Non succeed login
    encryptedRepo.login("Test", "12345");

    UserRepository decryptedRepo = new DecryptedUserRepository();
    UserRegistrationService userService2 = new UserRegistrationServiceDecrypted(decryptedRepo);

    // User registered decrypted
    userService2.registerUser(new User("Test", "12345"));

    // Succeed & Non succeed login
    decryptedRepo.login("Test", "12345");

    // User registered decrypted & fakeRepo
    UserRepository fakeRepo = new FakeDBUserRepository();
    UserRegistrationService userService3 = new UserRegistrationServiceDecrypted(fakeRepo);

    userService3.registerUser(new User("Test", "12345"));
    fakeRepo.login("Test", "12345");
  }
}